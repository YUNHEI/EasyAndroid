package com.chen.app.ui.simple.toolbar.pager

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.databinding.Fragment3Binding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.basem.argument.ArgString
import com.chen.basemodule.extend.doBinding

@Launch
class SimplePageFragment : BaseSimpleFragment() {

    override val binding by doBinding(Fragment3Binding::inflate)

    val title by ArgString()

    override fun initAndObserve() {

        binding.run {
            Topic.text = "标题 $title"
            Des.text = "toolbar 高级扩展"
        }

    }
}