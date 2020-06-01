package com.chen.basemodule.network.base

import androidx.lifecycle.Observer
import android.content.Context

/**
 * Created by chen on 2018/11/23
 */
abstract class BaseObserver<T : BaseResponse<*>>(private val context: Context) : Observer<T> {

    override fun onChanged(res: T?) {
        if (res != null && res.suc())
            success(res)
        else {
            fail(res?.apply {
                message = message ?: "系统繁忙，请稍后重试"
            } ?: BaseResponse<Any>(status = 300, message = "系统繁忙，请稍后重试") as T)
        }
    }

    abstract fun fail(res: T)

    abstract fun success(res: T)

}
