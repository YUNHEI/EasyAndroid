package com.chen.baseextend.base.fragment

import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.base.MultiSourceGroupBean
import com.chen.baseextend.repos.viewmodel.MainViewModel
import com.chen.basemodule.mlist.BaseMultiGroupMultiSourceListFragment

/**
 * 多数据源，分组布局列表
 *
 *  Created by chen on 2019/6/6
 **/
abstract class MultiGroupMultiSourceListFragment : BaseMultiGroupMultiSourceListFragment<MultiSourceGroupBean>() {

    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java).apply { owner = activity!! } }

}