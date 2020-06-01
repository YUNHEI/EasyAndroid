package com.chen.basemodule.extend

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.chen.basemodule.basem.BaseRoomBean
import com.chen.basemodule.basem.BaseViewModel
import com.chen.basemodule.network.base.BaseErrorResponse
import com.chen.basemodule.network.base.BaseObserver
import com.chen.basemodule.network.base.BaseResponse
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers

fun <T> BaseViewModel.requestData(activity: FragmentActivity, observer: Observable<BaseResponse<T>>,
                                  success: ((response: BaseResponse<T>) -> Unit)? = null,
                                  fail: ((response: BaseResponse<T>) -> Unit)? = { it.toast() },
                                  preHandle: ((response: BaseResponse<T>) -> Unit)? = null,
                                  successInterrupt: ((context: Context, response: BaseResponse<T>) -> Boolean)? = null,
                                  failInterrupt: ((context: Context, response: BaseResponse<T>) -> Boolean)? = null): LiveData<BaseResponse<T>> {

    return exe(observer).apply {
        observe(activity, object : BaseObserver<BaseResponse<T>>(activity) {
            override fun fail(res: BaseResponse<T>) {
                failInterrupt?.run {
                    if (!invoke(activity, res)) {
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
                    if (!invoke(activity, res)) {
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

fun <T> BaseViewModel.requestData(observer: Observable<BaseResponse<T>>) = exe(observer)

/**cacheType 缓存加载类型 0：初始化  1：刷新  2：加载更多,不缓存 */
//fun <T : BaseRoomBean> BaseViewModel.requestData(observer: Observable<BaseResponse<MutableList<T>>>, dao: BaseCacheDao<T>, cacheType: Int = 0, category: String? = null, prefix: String = "",
//                                                 netSuc: ((data: List<T>) -> Unit)? = null): LiveData<BaseResponse<MutableList<T>>> {
//    val observable = Observable.create(ObservableOnSubscribe<BaseResponse<MutableList<T>>> {
//
//        var hasCache = false
//
//        if (cacheType == 0) {
//            val data = mutableListOf<T>()
//            if (dao is BaseOfflineDao) {
//                data.addAll(dao.listOfflineToShow(category.orEmpty()))
//            }
//            data.addAll(dao.listCache(prefix + "_" + category.orEmpty()))
//            if (data.isNotEmpty()) {
//                hasCache = true
//                it.onNext(BaseResponse(data, 200, "成功", true))
//            }
//        }
//
//        observer.subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .onErrorReturn { BaseErrorResponse(it) }
//                .subscribe { res ->
//                    if (res.suc()) {
//                        if (cacheType in setOf(0, 1)) {
//                            dao.deleteAll("${prefix}_$category$SUFFIX_CACHE")
//                            dao.addAll(res.data.orEmpty().map {
//                                it.also {
//                                    it.category = "${prefix}_$category$SUFFIX_CACHE"
//                                }
//                            })
//                        }
//                        netSuc?.invoke(res.data.orEmpty())
//                        if (dao is BaseOfflineDao && cacheType in setOf(0, 1)) {
//                            res.data?.addAll(0, dao.listOfflineToShow(category.orEmpty()))
//                        }
//                        it.onNext(res)
//                    } else {
//                        if (cacheType == 0) {
//                            if (!hasCache) it.onNext(res)
//                        } else if (cacheType == 1) {
//                            val data = mutableListOf<T>()
//                            if (dao is BaseOfflineDao) {
//                                data.addAll(0, dao.listOfflineToShow(category.orEmpty()))
//                            }
//                            data.addAll(dao.listCache(prefix + "_" + category.orEmpty()))
//                            if (data.isNotEmpty()) {
//                                it.onNext(BaseResponse(data, 200, "成功", true))
//                            } else {
//                                it.onNext(res)
//                            }
//                        } else {
//                            it.onNext(res)
//                        }
//                    }
//                }
//    })
//
//    return exe(observable)
//}

/**cacheType 缓存加载类型 0：初始化  1：刷新  2：加载更多,不缓存 */
inline fun <reified T : BaseRoomBean> BaseViewModel.requestData(observer: Observable<BaseResponse<T>>, cacheType: Int = 0, category: String? = null,
                                                                noinline netSuc: ((data: T?) -> Unit)): LiveData<BaseResponse<T>> {

    val observable = Observable.create(ObservableOnSubscribe<BaseResponse<T>> {

        var hasCache = false

        if (cacheType == 0) {
            preferences.getString(category, null)?.run {
                hasCache = true
                it.onNext(BaseResponse(Gson().fromJson(this, T::class.java), 200, "成功", true))
            }
        }

        observer.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .onErrorReturn { BaseErrorResponse(it) }
                .subscribe { res ->
                    if (res.suc()) {
                        if (cacheType in setOf(0, 1)) {
                            preferences.edit().putString(category, Gson().toJson(res.data)).apply()
                        }
                        netSuc.invoke(res.data)
                        it.onNext(res)
                    } else {
                        if (cacheType == 0) {
                            if (!hasCache) it.onNext(res)
                        } else if (cacheType == 1) {
                            preferences.getString(category, null)?.run {
                                hasCache = true
                                it.onNext(BaseResponse(Gson().fromJson(this, T::class.java), 200, "成功", true))
                            } ?: run {
                                it.onNext(res)
                            }
                        } else {
                            it.onNext(res)
                        }
                    }
                }
    })

    return exe(observable)
}

fun <T, E : BaseResponse<T>> LiveData<E>.obs(activity: FragmentActivity,
                                             success: ((response: E) -> Unit)? = null,
                                             fail: ((response: E) -> Unit)? = null,
                                             preHandle: ((response: E) -> Unit)? = null,
                                             successInterrupt: ((context: Context, response: BaseResponse<T>) -> Boolean)? = null,
                                             failInterrupt: ((context: Context, response: BaseResponse<T>) -> Boolean)? = null) {

    observe(activity, object : BaseObserver<E>(activity) {
        override fun fail(res: E) {
            failInterrupt?.run {
                if (!invoke(activity, res)) {
                    preHandle?.invoke(res)
                    fail?.invoke(res)
                }
            } ?: run {
                preHandle?.invoke(res)
                fail?.invoke(res)
            }
        }

        override fun success(res: E) {
            successInterrupt?.run {
                if (!invoke(activity, res)) {
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
