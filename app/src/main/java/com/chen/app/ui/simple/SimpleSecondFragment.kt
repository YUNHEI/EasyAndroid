package com.chen.app.ui.simple

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.listenClick
import kotlinx.android.synthetic.main.fragment_1.*

@Launch
class SimpleSecondFragment :BaseSimpleFragment(){

    override val contentLayoutId = R.layout.fragment_2

    override fun initAndObserve() {

        toolbar.run{
            center("简单页面2")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        _title.text = "简单页面2"

        listenClick(_next) {
            when(it){
                _next ->{

                }
                else ->{
                }
            }
        }
    }
}