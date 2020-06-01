package com.chen.baseextend.repos.offline

import androidx.sqlite.db.SimpleSQLiteQuery
import com.chen.baseextend.bean.project.ProjectAddRequest
import com.chen.baseextend.bean.project.ProjectBean
import com.chen.baseextend.repos.ProjectRepos
import com.chen.baseextend.room.DataBaseCategory.MANAGER_PROJECT
import com.chen.baseextend.stepby.entity.InterruptStatus
import com.chen.baseextend.stepby.entity.Step
import com.chen.baseextend.stepby.entity.StepInfo
import com.chen.baseextend.util.FileUploadUtil
import com.chen.basemodule.extend.fromJson
import com.chen.basemodule.network.base.BaseResponse
import com.chen.basemodule.room.DataBaseCategory
import com.chen.basemodule.util.NetworkUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *  Created by chen on 2019/10/28
 **/
object ProjectOfflineRepos : BaseOfflineRepos<ProjectAddRequest, ProjectBean>() {

    override val dao = database.projectBeanDao()

    override val roomCategory = MANAGER_PROJECT

    override fun reqTransToBean(req: ProjectAddRequest): ProjectBean {
        return ProjectBean(title = req.title,
                typeId = req.typeId,
                unitprice = req.unitprice,
                completeNum = 0,
                remainNum = req.taskNum,
                totalNum = req.taskNum,
                underwayNum = 0,
                waitVerifyNum = 0,
                status = 10).apply {

            if (!req.id.isNullOrEmpty()) taskId = req.id
            if (!req.offlineId.isNullOrEmpty()) taskId = req.offlineId
        }
    }

    suspend fun getOfflineById(id: String): BaseResponse<ProjectBean> = suspendCoroutine {

        val cate = "%$roomCategory${DataBaseCategory.SUFFIX_OFFLINE}"

        val bean = dao.getOne(SimpleSQLiteQuery("SELECT * FROM ProjectBean WHERE taskId = '$id' AND category LIKE '$cate'"))

        if (bean != null) {
            OfflineService.start()
            it.resume(BaseResponse(bean, 200, "成功"))
        } else {
            it.resumeWithException(Throwable("获取失败"))
        }
    }

    override val refreshPage = "project_manager"

    override fun jsonToReq(json: String?) = fromJson<ProjectAddRequest>(json)

    override val steps: MutableList<Step<ProjectAddRequest, ProjectBean>> = mutableListOf(
            Step({

                if (!NetworkUtil.isConnected) {
                    it.second?.errorInfo = "网络异常，请检查后重试"
                    return@Step StepInfo(it, InterruptStatus.ERROR_TO_FINISH, handlerName =  "fail")
                }

                it.first?.step?.run {
                    filter {
                        it.stepType in setOf(2, 4, 5) && it.imageUrl.isNullOrEmpty() && !it.imageLocalUrl.isNullOrEmpty()
                    }.forEach { step ->

                        val path = FileUploadUtil.uploadSyncFile(step.imageLocalUrl!!)
                        if (!path.isNullOrEmpty()) {
                            step.imageUrl = path
                        } else {
                            return@Step StepInfo(it, InterruptStatus.FAIL_TO_NEXT, handlerName = "update")
                        }
                    }
                }
                StepInfo(it, handlerName = "update")
            }),
            Step(handler = {
                GlobalScope.launch {
                    dao.update(it?.dataBean!!.apply { status = 11 })
                }
            }, stepName = "fail")
    )

    override fun post(req: ProjectAddRequest) = ProjectRepos.service.publishProject(req)

}