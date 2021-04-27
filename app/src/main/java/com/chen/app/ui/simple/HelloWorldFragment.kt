package com.chen.app.ui.simple

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.databinding.Fragment1Binding
import com.chen.app.databinding.FragmentHelloWorldBinding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.doBinding
import kotlinx.android.synthetic.main.fragment_hello_world.*

/**
 *  Created by 86152 on 2020-06-12
 **/
@Launch
class HelloWorldFragment :BaseSimpleFragment(){
//    override val contentLayoutId = R.layout.fragment_hello_world
    override val binding by doBinding(FragmentHelloWorldBinding::inflate)
    override fun initAndObserve() {

        toolbar.run{
            center("标题")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        _topic.text = "Hello World"
    }
}