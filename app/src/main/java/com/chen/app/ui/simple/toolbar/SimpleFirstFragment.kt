package com.chen.app.ui.simple.toolbar

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.extend.startPage
import com.chen.baseextend.route.AppRoute.APP_SIMPLE_FIRST_FRAGMENT
import com.chen.basemodule.extend.listenClick
import kotlinx.android.synthetic.main.fragment_1.*

@Launch(path = APP_SIMPLE_FIRST_FRAGMENT)
class SimpleFirstFragment : BaseSimpleFragment() {

    //设置布局文件
    override val contentLayoutId = R.layout.fragment_1

    override fun initAndObserve() {

        //设置toolbar
        toolbar.run {
            //toolbar 标题
            center("简单页面")
            //toolbar 左侧返回图标
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        _title.text = "简单页面1"

        //添加点击事件
        listenClick(_next) {
            when (it) {
                _next -> {
                    startPage(SimpleToolbarFragment::class)
                }
                else -> {
                }
            }
        }
    }
}