package com.chen.baseextend.base.activity

import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.repos.MainViewModel
import com.chen.basemodule.allroot.RootFragmentActivity

/**
 * 特殊的如SplashActivity继承BaseFragmentActivity，不允许在根节点实现任何方法
 *  Created by chen on 2019/5/28
 **/
abstract class BaseFragmentActivity : RootFragmentActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java).apply { owner = this@BaseFragmentActivity } }

}