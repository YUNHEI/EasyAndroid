package com.chen.app.ui.simple.preference

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.constant.BasePreference
import com.chen.basemodule.extend.listenClick
import com.chen.basemodule.extend.toastSuc
import kotlinx.android.synthetic.main.fragment_preference.*

/**
 *  Created by 86152 on 2020-06-07
 **/
@Launch
class PreferenceFragment :BaseSimpleFragment(){

    override val contentLayoutId = R.layout.fragment_preference

    override fun initAndObserve() {

        toolbar.run{
            center("本地存储")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        _topic.text = "最快捷的本地存储"

        listenClick(_save, _get) {
            when(it){
                _save ->{
                    BasePreference.INFO = _input.text.toString()
                    "已保存".toastSuc()
                }
                _get ->{
                    _content.text = BasePreference.INFO
                }
                else ->{
                }
            }
        }
    }
}