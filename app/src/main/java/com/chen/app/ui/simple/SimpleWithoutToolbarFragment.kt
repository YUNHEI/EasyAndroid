package com.chen.app.ui.simple

import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.listenClick
import kotlinx.android.synthetic.main.fragment_2.*

@Launch
class SimpleWithoutToolbarFragment : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_2

    private lateinit var _title: TextView

    private lateinit var _back: ImageView

    private lateinit var _close: TextView

    private lateinit var _more: ImageView

    private lateinit var _share: TextView

    override fun initAndObserve() {

        //不调用toolbar.run{} 将不出现toolbar

//        toolbar.run {
//
//            // center toobar 居中的标题
//            _title = center("自定义Toolbar")
//
//            // left toobar 居左的操作 可以添加图片，文字 自定义边距，位置
//            _back = left(R.mipmap.ic_back, rect = Rect(15, 15, 10, 15)) { activity?.finish() }
//
//            _close = left("关闭") { "点击了关闭".toastSuc() }
//
//            _more = right(R.mipmap.ic_more) { "点击了更多".toastSuc() }
//
//            _share = right("分享") { "点击了更多".toastSuc() }
//
//
//        }

        _topic.text = "不出现 Toolbar"

        listenClick(_next) {
            when (it) {
                _next -> {

                }
                else -> {
                }
            }
        }
    }
}