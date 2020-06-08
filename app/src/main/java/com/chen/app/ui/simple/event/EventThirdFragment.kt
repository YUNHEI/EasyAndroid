package com.chen.app.ui.simple.event

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.listenClick
import kotlinx.android.synthetic.main.fragment_event_close.*

@Launch
class EventThirdFragment : BaseSimpleFragment() {

    //设置布局文件
    override val contentLayoutId = R.layout.fragment_event_close_2

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
        listenClick(_close) {
            when (it) {
                _close -> {
                    postClose(EventFragment::class, EventSecondFragment::class)
                }
                else -> {
                }
            }
        }
    }
}