package com.chen.app.ui.simple.pager

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.basem.argument.ArgString
import kotlinx.android.synthetic.main.fragment_1.*
import kotlinx.android.synthetic.main.fragment_2.*

@Launch
class SimplePageFragment : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_3

    val title by ArgString()

    override fun initAndObserve() {

        _topic.text = "标题 $title"

        _des.text = "toolbar 高级扩展"

    }
}