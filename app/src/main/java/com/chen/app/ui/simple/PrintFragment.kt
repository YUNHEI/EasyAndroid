package com.chen.app.ui.simple

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.databinding.FragmentPrintBinding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.listenClick

@Launch
class PrintFragment : BaseSimpleFragment() {

    override val binding by doBinding(FragmentPrintBinding::inflate)
    override fun initAndObserve() {

        toolbar.run{
            center("打印")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        listenClick(binding.Print) {
            when (it) {
                binding.Print -> {
                    println(binding.Edit.text)
                }
                else -> {
                }
            }
        }
    }

}