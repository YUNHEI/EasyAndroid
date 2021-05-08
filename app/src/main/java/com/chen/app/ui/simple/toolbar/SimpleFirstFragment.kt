package com.chen.app.ui.simple.toolbar

import com.alibaba.android.arouter.facade.annotation.Launch
import com.alibaba.android.arouter.facade.enums.LaunchType
import com.alibaba.android.arouter.facade.enums.SwipeType
import com.chen.app.R
import com.chen.app.databinding.Fragment1Binding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.extend.startPage
import com.chen.baseextend.route.AppRoute.APP_SIMPLE_FIRST_FRAGMENT
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.listenClick

@Launch(path = APP_SIMPLE_FIRST_FRAGMENT, swipeType = SwipeType.FROM_TOP, launchType = LaunchType.COVER)
class SimpleFirstFragment : BaseSimpleFragment() {

    //设置布局文件
    override val binding by doBinding(Fragment1Binding::inflate)

    override fun initAndObserve() {

        //设置toolbar
        toolbar.run {
            //toolbar 标题
            center("标题")
            //toolbar 左侧返回图标
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        binding.run {
            title.text = "Hello World"

            //添加点击事件
            listenClick(next) {
                when (it) {
                    next -> {
                        startPage(SimpleToolbarFragment::class)
                    }
                    else -> {
                    }
                }
            }

        }
    }
}