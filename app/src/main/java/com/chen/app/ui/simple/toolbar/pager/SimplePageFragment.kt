package com.chen.app.ui.simple.toolbar.pager

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.databinding.Fragment3Binding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.basem.argument.ArgString
import com.chen.basemodule.extend.doBinding
import kotlinx.android.synthetic.main.fragment_1.*
import kotlinx.android.synthetic.main.fragment_2.*

@Launch
class SimplePageFragment : BaseSimpleFragment() {

    //    override val contentLayoutId = R.layout.fragment_3
    override val binding by doBinding(Fragment3Binding::inflate)
    val title by ArgString()

    override fun initAndObserve() {

        _topic.text = "标题 $title"

        _des.text = "toolbar 高级扩展"

    }
}