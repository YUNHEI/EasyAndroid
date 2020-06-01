package com.chen.baseextend.base.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.extend.commonFailInterrupt
import com.chen.baseextend.repos.MainViewModel
import com.chen.basemodule.allroot.RootFragmentActivity
import com.chen.basemodule.extend.requestData
import com.chen.basemodule.network.base.BaseResponse
import io.reactivex.Observable

/**
 * 特殊的如SplashActivity继承BaseFragmentActivity，不允许在根节点实现任何方法
 *  Created by chen on 2019/5/28
 **/
abstract class BaseFragmentActivity : RootFragmentActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java).apply { owner = this@BaseFragmentActivity } }

    protected open fun <T> loadNetData(observer: Observable<BaseResponse<T>>,
                                       success: ((response: BaseResponse<T>) -> Unit)? = null,
                                       fail: ((response: BaseResponse<T>) -> Unit)? = null,
                                       preHandle: ((response: BaseResponse<T>) -> Unit)? = null) : LiveData<BaseResponse<T>> {

        return viewModel.requestData(this, observer, success, fail, preHandle, failInterrupt = commonFailInterrupt)

    }
}