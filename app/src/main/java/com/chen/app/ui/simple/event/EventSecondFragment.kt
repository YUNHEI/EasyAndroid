package com.chen.app.ui.simple.event

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.databinding.Fragment1Binding
import com.chen.app.databinding.FragmentEventCloseBinding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.extend.startPage
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.listenClick

@Launch
class EventSecondFragment : BaseSimpleFragment() {

    //设置布局文件
//    override val contentLayoutId = R.layout.fragment_event_close
    override val binding by doBinding(FragmentEventCloseBinding::inflate)
    override fun initAndObserve() {

        //设置toolbar
        toolbar.run {
            //toolbar 标题
            center("通知")
            //toolbar 左侧返回图标
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        binding.Title.text = ""

        //添加点击事件
        listenClick(binding.Next, binding.Close) {
            when (it) {
                binding.Close -> {
                    postClose(EventFragment::class)
                }
                binding.Next -> {
                    startPage(EventThirdFragment::class)
                }
                else -> {
                }
            }
        }
    }
}