package com.chen.baseextend.base.fragment

import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.repos.viewmodel.MainViewModel
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.mlist.BaseSingleListFragment

/**
 * 单一布局列表
 *
 *  Created by chen on 2019/6/6
 **/
abstract class SingleListFragment<V : RootBean> : BaseSingleListFragment<V>() {

    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java).apply { owner = activity!! } }
}