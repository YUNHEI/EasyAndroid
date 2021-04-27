package com.chen.baseextend.base.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.chen.baseextend.repos.viewmodel.MainViewModel
import com.chen.basemodule.allroot.RootFragmentActivity

/**
 * 特殊的如SplashActivity继承BaseFragmentActivity，不允许在根节点实现任何方法
 *  Created by chen on 2019/5/28
 **/
abstract class BaseFragmentActivity : RootFragmentActivity() {

    abstract val binding: ViewBinding

    val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
            .apply { owner = this@BaseFragmentActivity }
    }


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(binding.root)
    }
}