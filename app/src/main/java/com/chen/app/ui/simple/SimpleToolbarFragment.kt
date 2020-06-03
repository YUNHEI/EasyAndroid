package com.chen.app.ui.simple

import android.graphics.Rect
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.listenClick
import com.chen.basemodule.extend.toastSuc
import kotlinx.android.synthetic.main.fragment_2.*

@Launch
class SimpleToolbarFragment : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_2

    private lateinit var _title: TextView

    private lateinit var _back: ImageView

    private lateinit var _close: TextView

    private lateinit var _more: ImageView

    private lateinit var _share: TextView

    override fun initAndObserve() {

        toolbar.run {

            // center toobar 居中的标题
            _title = center("自定义Toolbar")

            // left toobar 居左的操作 可以添加图片，文字 自定义边距，位置
            _back = left(R.mipmap.ic_back, rect = Rect(15, 15, 10, 15)) { activity?.finish() }

            _close = left("关闭") { "点击了关闭".toastSuc() }

            _more = right(R.mipmap.ic_more) { "点击了更多".toastSuc() }

            _share = right("分享") { "点击了更多".toastSuc() }

            //设置toolbar背景图片
            backgroundR = R.drawable.bg_item_white_e3_line

            //设置toolbar背景色
            backgroundC = R.color.white

            //是否开启沉浸式, 默认跟随主题
            isImmerse = true

            //是否开启亮色主题, 默认亮色
            isLight = true

            //toobar分割线
            divider(R.dimen.dimen_05, R.color.gray_f5)
        }

        _topic.text = "Toolbar 灵活设置"

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