package com.chen.baseextend.base.fragment

import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.repos.viewmodel.MainViewModel
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.mlist.BaseMultiListFragment

/**
 * 多种布局列表
 *
 *  Created by chen on 2019/6/6
 **/
abstract class MultiListFragment<V: RootBean> : BaseMultiListFragment<V>() {

    /*E viewModel 必须是第一个泛型变量*/
    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java).apply { owner = activity!! } }

}