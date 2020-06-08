package com.chen.app.ui.simple.event

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.ui.simple.SamplePageFragment
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.extend.startPage
import com.chen.basemodule.extend.listenClick
import kotlinx.android.synthetic.main.fragment_event.*
import java.util.*

@Launch
class EventFragment : BaseSimpleFragment() {

    //设置布局文件
    override val contentLayoutId = R.layout.fragment_event

    override fun initAndObserve() {

        //设置toolbar
        toolbar.run {
            //toolbar 标题
            center("通知")
            //toolbar 左侧返回图标
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        _title.text = ""

        //添加点击事件
        listenClick(_next, _notify) {
            when (it) {
                _notify -> {
                    postRefresh(SamplePageFragment::class, obj = "通知标题${Random().nextInt(100)}")
                }
                _next -> {
                    startPage(EventSecondFragment::class)
                }
                else -> {
                }
            }
        }
    }
}