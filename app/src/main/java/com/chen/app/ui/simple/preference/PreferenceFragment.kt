package com.chen.app.ui.simple.preference

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.databinding.FragmentPreferenceBinding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.constant.BasePreference
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.listenClick
import com.chen.basemodule.extend.toastSuc

/**
 *  Created by 86152 on 2020-06-07
 **/
@Launch
class PreferenceFragment : BaseSimpleFragment() {

    override val binding by doBinding(FragmentPreferenceBinding::inflate)

    override fun initAndObserve() {

        toolbar.run {
            center("本地存储")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        binding.run {
            Topic.text = "最快捷的本地存储"

            listenClick(Save, Get) {
                when (it) {
                    Save -> {
                        BasePreference.INFO = Input.text.toString()
                        "已保存".toastSuc()
                    }
                    Get -> {
                        Content.text = BasePreference.INFO
                    }
                    else -> {
                    }
                }
            }

        }
    }
}