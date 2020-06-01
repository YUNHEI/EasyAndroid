package com.chen.baseextend.base.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.extend.commonFailInterrupt
import com.chen.baseextend.repos.MainViewModel
import com.chen.basemodule.basem.BaseDataFragment
import com.chen.basemodule.extend.requestData
import com.chen.basemodule.network.base.BaseResponse
import io.reactivex.Observable

/**
 *  Created by chen on 2019/6/11
 **/
abstract class BaseSimpleFragment : BaseDataFragment() {

    private var isLoaded = false //初始化过的页面，不在出现网络错误

    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java).apply { owner = activity!! } }

    override fun <T> loadNetData(observer: Observable<BaseResponse<T>>,
                                 success: ((response: BaseResponse<T>) -> Unit)?,
                                 fail: ((response: BaseResponse<T>) -> Unit)?,
                                 preHandle: ((response: BaseResponse<T>) -> Unit)?): LiveData<BaseResponse<T>>? {

        return viewModel.requestData(activity!!, observer, success, fail, preHandle, failInterrupt = commonFailInterrupt)
    }

    override fun startLoadData(muteLoadData: Boolean?) {

        showShimmerCover(!(this.muteLoadData || (muteLoadData ?: false)), false, false)

        (loadData() as LiveData<BaseResponse<*>>?)?.run {
            observe(this@BaseSimpleFragment, Observer { handleResponse(it!!) })
        }
    }

    //不处理返回值
    open fun loadData(): LiveData<*>? {
        return null
    }

    open fun handleResponse(s: BaseResponse<*>) {
        when {
            s.suc() || isLoaded -> {
                showShimmerCover(false, false, false)
                isLoaded = true
            }
            s.status in 300..399 -> {
                showShimmerCover(false, false, false)
            }
            s.status >= 400 -> showShimmerCover(false, true, false)
        }
    }
}