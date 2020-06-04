package com.chen.app.ui.simple

import android.graphics.Rect
import android.util.Log
import androidx.core.widget.NestedScrollView
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.toastSuc
import kotlinx.android.synthetic.main.fragment_1._des
import kotlinx.android.synthetic.main.fragment_2._topic
import kotlinx.android.synthetic.main.fragment_4.*
import kotlin.math.max
import kotlin.math.min

@Launch
class ToolbarFloatFragment : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_4

    override fun initAndObserve() {

        toolbar.run {

            // center toobar 居中的标题
            center("自定义Toolbar")

            // left toobar 居左的操作 可以添加图片，文字 自定义边距，位置
            left(R.mipmap.ic_back, rect = Rect(15, 15, 10, 15)) { activity?.finish() }

            left("关闭") { "点击了关闭".toastSuc() }

            right(R.mipmap.ic_more) { "点击了更多".toastSuc() }

            right("分享") { "点击了更多".toastSuc() }

            //禁用分割线
            divider(0)

            backgroundC = R.color.trans00


        }

        _topic.text = "Toolbar 悬浮"

        _des.text = "以 FrameLayout 为父容器 实现toolbar 悬浮"

        _scroller.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            val a = min(255, max(0, ((scrollY + 0f) / toolbar.height * 255).toInt())) shl 24
            toolbar.setBackgroundColor(a + (color(R.color.blue_lightest) and 0x00ffffff))
        }

    }
}