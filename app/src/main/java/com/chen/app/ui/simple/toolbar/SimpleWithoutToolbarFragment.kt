package com.chen.app.ui.simple.toolbar

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.databinding.Fragment3Binding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.doBinding

@Launch
class SimpleWithoutToolbarFragment : BaseSimpleFragment() {

    override val binding by doBinding(Fragment3Binding::inflate)

    override fun initAndObserve() {

        //不调用toolbar.run{} 将不出现toolbar

//        toolbar.run {Ø
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

        binding.run {
            Topic.text = "不出现 Toolbar"

            Des.text = "不调用toolbar.run{} 将不出现toolbar"
        }

    }
}