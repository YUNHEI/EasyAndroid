package com.chen.app.ui.simple

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.listenClick
import kotlinx.android.synthetic.main.fragment_print.*

@Launch
class PrintFragment : BaseSimpleFragment() {


    override val contentLayoutId = R.layout.fragment_print

    override suspend fun onReady() {
        super.onReady()

        listenClick(_print) {
            when (it) {
                _print -> {
                    println(_edit.text)
                }
                else -> {
                }
            }
        }
    }


}