package com.chen.baseextend.base.fragment

import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.repos.viewmodel.MainViewModel
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.mlist.BaseMultiGroupListFragment

/**
 * 分组布局列表
 *
 *  Created by chen on 2019/6/6
 **/
abstract class MultiGroupListFragment<P : RootBean, C : RootBean> : BaseMultiGroupListFragment<P, C>() {

    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java).apply { owner = activity!! } }

}