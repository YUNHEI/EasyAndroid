package com.chen.baseextend.base.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.repos.viewmodel.MainViewModel
import com.chen.basemodule.basem.BaseDataFragment
import com.chen.basemodule.network.base.BaseResponse

/**
 *  Created by chen on 2019/6/11
 **/
abstract class BaseSimpleFragment : BaseDataFragment() {

    private var isLoaded = false //初始化过的页面，不在出现网络错误

    override val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java).apply { owner = activity!! }
    }

    override fun startLoadData(muteLoadData: Boolean?) {

        showLoadingCover(if (this.muteLoadData || muteLoadData == true) HIDE else LOADING)

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
                showLoadingCover(HIDE)
                isLoaded = true
            }
            s.status in 300..399 -> {
                showLoadingCover(HIDE)
            }
            s.status >= 400 -> showLoadingCover(ERROR)
        }
    }
}