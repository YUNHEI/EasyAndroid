package com.chen.baseextend.repos.offline

import com.alibaba.fastjson.JSON
import com.jeremyliao.liveeventbus.LiveEventBus
import com.chen.baseextend.BaseExtendApplication
import com.chen.baseextend.stepby.StepBy
import com.chen.baseextend.stepby.entity.InterruptStatus
import com.chen.baseextend.stepby.entity.Step
import com.chen.baseextend.stepby.entity.StepInfo
import com.chen.baseextend.stepby.entity.StepName
import com.chen.basemodule.basem.BaseOfflineRoomBean
import com.chen.basemodule.constant.LiveBusKey
import com.chen.basemodule.event_bus.BaseRefreshEvent
import com.chen.basemodule.network.base.BaseErrorResponse
import com.chen.basemodule.network.base.BaseRequest
import com.chen.basemodule.network.base.BaseResponse
import com.chen.basemodule.room.BaseOfflineDao
import com.chen.basemodule.room.DataBaseCategory.NOT_SHOW
import com.chen.basemodule.room.DataBaseCategory.SUFFIX_OFFLINE
import com.chen.basemodule.util.NetworkUtil
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 *  Created by chen on 2019/10/12
 **/
abstract class BaseOfflineRepos<R : BaseRequest, B : BaseOfflineRoomBean> {

    companion object {
        const val MAX_TRY_TIMES = 1
    }

    val database by lazy { BaseExtendApplication.database }

    private val offlinePools: HashMap<String, List<*>?> = hashMapOf()

    abstract val dao: BaseOfflineDao<B>

    /**不分类默认传 空字符串 "" */
    abstract val roomCategory: String

    abstract fun reqTransToBean(req: R): B

    abstract fun jsonToReq(json: String?): R?

    abstract fun post(req: R): Observable<BaseResponse<Any>>

    abstract val refreshPage: String

    abstract val steps: MutableList<Step<R, B>>

    suspend fun addOffline(request: R, notShow: Boolean = false): BaseResponse<Boolean> {
        val offlineBean = reqTransToBean(request).apply {
            requestJson = JSON.toJSONString(request)
            if (category.isEmpty()) {
                category = roomCategory + SUFFIX_OFFLINE
            } else if (!category.endsWith(SUFFIX_OFFLINE)) {
                category += SUFFIX_OFFLINE
            }
            if (notShow) {
                if (!category.startsWith(NOT_SHOW)) {
                    category = NOT_SHOW + category
                }
            }
        }

        val l = dao.add(offlineBean)

        return if (l > 0) {
            OfflineService.start()
            BaseResponse(true, 200, "成功")
        } else {
            BaseResponse(false, 300, "发送失败")
        }
    }

    suspend fun retryOffline(bean: B): BaseResponse<Boolean> {

        val l = dao.update(bean.apply { retryTimes = 0 })

        return if (l > 0) {
            OfflineService.start()
            BaseResponse(true, 200, "成功")
        } else {
            BaseResponse(false, 300, "更新失败")
        }

    }

    suspend fun deleteOffline(bean: B): BaseResponse<Boolean> {
        return if (dao.delete(bean) > 0) {
            BaseResponse(true, 200, "成功")
        } else {
            BaseResponse(false, 300, "删除失败")
        }

    }

    fun postOffline() {

        val queueName = hashCode().toString()

        synchronized(this) {
            if (offlinePools.keys.contains(queueName)) {
                return
            } else {
                offlinePools[queueName] = null
            }
        }

        dao.listOfflineToPost(roomCategory, MAX_TRY_TIMES).let {
            if (it.isNotEmpty()) {

                if (offlinePools[queueName] != null) return@let

                offlinePools[queueName] = it

                var isEnable = false

                offlinePools[queueName]?.run {
                    val itera = iterator()
                    while (itera.hasNext()) {
                        (itera.next() as B).let { scene ->

                            val request = jsonToReq(scene.requestJson)

                            isEnable = StepBy.arrangeStep(*steps.toTypedArray(),
                                    Step(
                                            {
                                                if (!NetworkUtil.isConnected) {
                                                    it.second?.errorInfo = "网络异常，请检查后重试"
                                                    return@Step StepInfo(it, interrupt = InterruptStatus.ERROR_TO_FINISH, handlerName = "fail")
                                                } else if (it.first == null) {
                                                    it.second?.errorInfo = "服务繁忙，请稍后再试"
                                                    return@Step StepInfo(it, interrupt = InterruptStatus.ERROR_TO_FINISH, handlerName = "fail")
                                                } else {
                                                    val res = post(it.first!!)
                                                            .subscribeOn(Schedulers.io())
                                                            .observeOn(Schedulers.io())
                                                            .onErrorReturn { BaseErrorResponse(it) }
                                                            .blockingFirst()
                                                    if (res.suc()) {
                                                        GlobalScope.launch {
                                                            it.second?.run { dao.delete(this) }
                                                        }
                                                    } else {
                                                        it.second?.errorInfo = res.message
                                                        return@Step StepInfo(it, interrupt = InterruptStatus.ERROR_TO_NEXT, handlerName = "fail")
                                                    }
                                                }

                                                StepInfo(it, nextStepName = StepName.END)
                                            }),
                                    Step(stepName = "update", handler = {
                                        GlobalScope.launch {
                                            it?.dataBean?.run {
                                                dao.update(this.apply {
                                                    requestJson = JSON.toJSONString(it.reqBean)
                                                    stepName = it.nextStepName
                                                })
                                            }
                                        }
                                    }),
                                    request = request,
                                    daoBean = scene,
                                    interruptCallback = { info, _, msg, suc ->
                                        GlobalScope.launch {
                                            info.dataBean?.run {
                                                if (suc in setOf(InterruptStatus.FAIL_TO_FINISH, InterruptStatus.ERROR_TO_FINISH)) {
                                                    dao.update(this.apply { retryTimes = MAX_TRY_TIMES })
                                                }
                                                if (suc in setOf(InterruptStatus.FAIL_TO_NEXT, InterruptStatus.ERROR_TO_NEXT)) {
                                                    dao.update(this.apply { ++retryTimes })
                                                }
                                            }
                                        }
                                        LiveEventBus.get(LiveBusKey.EVENT_REFRESH)
                                                .broadcast(BaseRefreshEvent(this@BaseOfflineRepos::class, refreshPage, obj = info))
                                    }
                            )

                            if (!isEnable) return@run
                        }
                    }
                }

                offlinePools.remove(queueName)
                if (isEnable) postOffline()
            } else {
                offlinePools.remove(queueName)
            }
        }
    }
}