package com.chen.baseextend.base.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.extend.commonFailInterrupt
import com.chen.baseextend.repos.MainViewModel
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.extend.requestData
import com.chen.basemodule.mlist.BaseMListFragment
import com.chen.basemodule.network.base.BaseResponse
import io.reactivex.Observable

/**
 * 多种布局列表
 *
 *  Created by chen on 2019/6/6
 **/
abstract class MulListFragment<V: RootBean> : BaseMListFragment<V>() {

    /*E viewModel 必须是第一个泛型变量*/
    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java).apply { owner = activity!! } }

    override fun <T> loadNetData(observer: Observable<BaseResponse<T>>,
                                 success: ((response: BaseResponse<T>) -> Unit)?,
                                 fail: ((response: BaseResponse<T>) -> Unit)?,
                                 preHandle: ((response: BaseResponse<T>) -> Unit)?) : LiveData<BaseResponse<T>> {

        return viewModel.requestData(activity!!, observer, success, fail, preHandle, failInterrupt = commonFailInterrupt)
    }
}