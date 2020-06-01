package com.chen.baseextend.base.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.extend.commonFailInterrupt
import com.chen.baseextend.repos.MainViewModel
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.extend.requestData
import com.chen.basemodule.mlist.BaseGListFragment
import com.chen.basemodule.network.base.BaseResponse
import io.reactivex.Observable

/**
 * 分组布局列表
 *
 *  Created by chen on 2019/6/6
 **/
abstract class GroupListFragment<P : RootBean, C : RootBean> : BaseGListFragment<P, C>() {

    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java).apply { owner = activity!! } }

    override fun <T> loadNetData(observer: Observable<BaseResponse<T>>,
                                 success: ((response: BaseResponse<T>) -> Unit)?,
                                 fail: ((response: BaseResponse<T>) -> Unit)?,
                                 preHandle: ((response: BaseResponse<T>) -> Unit)?): LiveData<BaseResponse<T>> {

        return viewModel.requestData(activity!!, observer, success, fail, preHandle, failInterrupt = commonFailInterrupt)
    }
}