package com.chen.basemodule.basem

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chen.basemodule.BaseModuleLoad
import com.chen.basemodule.allroot.RootViewModel
import com.chen.basemodule.extend.toast
import com.chen.basemodule.network.base.BaseErrorResponse
import com.chen.basemodule.network.base.BaseNetException
import com.chen.basemodule.network.base.BaseObserver
import com.chen.basemodule.network.base.BaseResponse
import com.chen.basemodule.room.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*


open class BaseViewModel : RootViewModel() {

    lateinit var owner: FragmentActivity

    val compositeDisposable by lazy { CompositeDisposable() }

    val preferences by lazy {
        BaseModuleLoad.context.run {
            getSharedPreferences(
                packageName + "cache_preferences",
                Context.MODE_PRIVATE
            )
        }
    }

    open fun <T>  requestData(
        block: suspend CoroutineScope.() -> BaseResponse<T>,
        success: ((response: BaseResponse<T>) -> Unit)? = null,
        fail: ((response: BaseResponse<T>) -> Unit)? = { it.toast() },
        preHandle: ((response: BaseResponse<T>) -> Unit)? = null,
        successInterrupt: ((context: Context, response: BaseResponse<T>) -> Boolean)? = null,
        failInterrupt: ((context: Context, response: BaseResponse<T>) -> Boolean)? = null
    ): LiveData<BaseResponse<T>> {

        val liveData = MutableLiveData<BaseResponse<T>>()

        val exceptionHandler = CoroutineExceptionHandler { _, e ->
            liveData.postValue(BaseErrorResponse(e))
        }

        viewModelScope.launch(exceptionHandler) {
            async(Dispatchers.IO, block = block).run {
                liveData.postValue(await())
            }
        }

        return liveData.apply {
            observe(owner, object : BaseObserver<BaseResponse<T>>(owner) {
                override fun fail(res: BaseResponse<T>) {
                    failInterrupt?.run {
                        if (!invoke(owner, res)) {
                            preHandle?.invoke(res)
                            fail?.invoke(res) ?: res.toast()
                        }
                    } ?: run {
                        preHandle?.invoke(res)
                        fail?.invoke(res) ?: res.toast()
                    }
                }

                override fun success(res: BaseResponse<T>) {
                    successInterrupt?.run {
                        if (!invoke(owner, res)) {
                            preHandle?.invoke(res)
                            success?.invoke(res)
                        }
                    } ?: run {
                        preHandle?.invoke(res)
                        success?.invoke(res)
                    }
                }
            })
        }
    }


    /**cacheType 缓存加载类型 0：初始化  1：刷新  2：加载更多,不缓存 */
    fun <T : BaseRoomBean> requestData(
        block: suspend CoroutineScope.() -> BaseResponse<MutableList<T>>,
        dao: BaseDao<T>,
        cacheType: Int = 0,
        category: String? = null,
        prefix: String = "",
        netSuc: ((data: List<T>) -> Unit)? = null
    ): LiveData<BaseResponse<MutableList<T>>> {

        val liveData = MutableLiveData<BaseResponse<MutableList<T>>>()

        var hasCache = false

        val exceptionHandler = CoroutineExceptionHandler { _, e ->
            if (cacheType == 0 && !hasCache) {
                liveData.postValue(BaseErrorResponse<MutableList<T>>(e).apply { status = 410 })
            }
        }

        viewModelScope.launch(exceptionHandler) {

            supervisorScope {
                if (cacheType == 0) {
                    val data = mutableListOf<T>()
                    if (dao is BaseFixedDao) {
                        async(Dispatchers.Default) { dao.listFixedData() }.run {
                            data.addAll(await())
                        }
                    }
                    if (dao is BaseCacheDao) {
                        if (dao is BaseOfflineDao) {
                            async(Dispatchers.Default) { dao.listOfflineToShow(category.orEmpty()) }.run {
                                data.addAll(await())
                            }
                        }
                        async(Dispatchers.Default) { dao.listCache(prefix + "_" + category.orEmpty()) }.run {
                            data.addAll(await())
                        }
                    }
                    if (data.isNotEmpty()) {
                        hasCache = true
                        liveData.value = BaseResponse(data, 200, "成功", true)
                    }
                }

                async(block = block).run {
                    try {
                        await()
                    } catch (e: Exception) {
                        BaseErrorResponse<MutableList<T>>(e)
                    }
                }.run {
                    if (suc()) {
                        if (cacheType in setOf(0, 1)) {
                            if (dao is BaseCacheDao) {
                                async(Dispatchers.Default) {
                                    dao.deleteAll("${prefix}_$category${DataBaseCategory.SUFFIX_CACHE}")
                                    dao.addAll(data.orEmpty().map {
                                        it.also {
                                            it.category =
                                                "${prefix}_$category${DataBaseCategory.SUFFIX_CACHE}"
                                        }
                                    })
                                }.run {
                                    await()
                                }
                            }
                        }
                        netSuc?.invoke(data.orEmpty())

                        if (dao is BaseFixedDao && cacheType in setOf(0, 1)) {
                            async(Dispatchers.Default) { dao.listFixedData() }.run {
                                data?.addAll(await())
                            }
                        }

                        if (dao is BaseOfflineDao && cacheType in setOf(0, 1)) {
                            async(Dispatchers.Default) { dao.listOfflineToShow(category.orEmpty()) }.run {
                                data?.addAll(0, await())
                            }
                        }
                        liveData.value = this
                    } else {
                        if (cacheType == 0) {
                            if (!hasCache) {
                                liveData.value = this
                            }
                        } else if (cacheType == 1) {
                            val data = mutableListOf<T>()

                            if (dao is BaseFixedDao) {
                                async(Dispatchers.Default) { dao.listFixedData() }.run {
                                    data.addAll(await())
                                }
                            }
                            if (dao is BaseCacheDao) {
                                if (dao is BaseOfflineDao) {
                                    async(Dispatchers.Default) { dao.listOfflineToShow(category.orEmpty()) }.run {
                                        data.addAll(await())
                                    }
                                }
                                async(Dispatchers.Default) { dao.listCache(prefix + "_" + category.orEmpty()) }.run {
                                    data.addAll(await())
                                }
                            }
                            liveData.value =
                                if (data.isEmpty()) this else BaseResponse(data, 290, "缓存更新")
                        } else {
                            liveData.value = this
                        }
                        if (cacheType == 0 && hasCache) {
                            netSuc?.invoke(data.orEmpty())
                        }
                    }
                }
            }
        }

        return liveData
    }

    override fun onCleared() {

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }

        super.onCleared()
    }
}
