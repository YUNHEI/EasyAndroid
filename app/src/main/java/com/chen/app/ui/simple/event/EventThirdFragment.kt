package com.chen.app.ui.simple.event

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.databinding.FragmentEventClose2Binding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.listenClick

@Launch
class EventThirdFragment : BaseSimpleFragment() {

    //设置布局文件
    override val binding by doBinding(FragmentEventClose2Binding::inflate)

    override fun initAndObserve() {

        //设置toolbar
        toolbar.run {
            //toolbar 标题
            center("通知")
            //toolbar 左侧返回图标
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        binding.run {
            Title.text = ""

            //添加点击事件
            listenClick(Close) {
                when (it) {
                    Close -> {
                        postClose(EventFragment::class, EventSecondFragment::class)
                    }
                    else -> {
                    }
                }
            }
        }
    }
}