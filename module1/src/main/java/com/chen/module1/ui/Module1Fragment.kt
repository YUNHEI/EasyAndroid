package com.chen.module1.ui

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.extend.startPage
import com.chen.baseextend.route.AppRoute.APP_SIMPLE_FIRST_FRAGMENT
import com.chen.baseextend.route.Module1Route.MODULE1_FRAGMENT
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.listenClick
import com.chen.module1.R
import com.chen.module1.databinding.FragmentModule1Binding

/**
 *  Created by 86152 on 2020-06-07
 **/
@Launch(path = MODULE1_FRAGMENT)
class Module1Fragment : BaseSimpleFragment() {

    override val binding by doBinding(FragmentModule1Binding::inflate)

    override fun initAndObserve() {

        toolbar.run{
            center("模块间跳转")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        binding.run {

            Topic.text = "module1的页面"

            listenClick(Goto) {
                when(it){
                    Goto ->{
                        startPage(route = APP_SIMPLE_FIRST_FRAGMENT)
                    }
                    else ->{
                    }
                }
            }
        }
    }

}