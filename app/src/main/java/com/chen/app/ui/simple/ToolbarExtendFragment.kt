package com.chen.app.ui.simple

import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.view.CleanableEditText
import com.chen.basemodule.extend.dp2px
import com.chen.basemodule.extend.toastSuc
import kotlinx.android.synthetic.main.fragment_5.*

@Launch
class ToolbarExtendFragment : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_5

    private var key = ""

    private lateinit var mSearchEdit: CleanableEditText

    override fun initAndObserve() {

        toolbar.run {
            //toolbar中部布局扩展
            mSearchEdit = center<CleanableEditText>(R.layout.layout_search_edit, true).apply {
                (layoutParams as FrameLayout.LayoutParams).run {
                    setMargins(0, dp2px(6), 0, dp2px(8))
                }
            }

            mSearchEdit.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    "点击了搜索 ${mSearchEdit.text.toString().trim { it <= ' ' }}".toastSuc()
                    return@setOnEditorActionListener true
                }
                false
            }

            left(R.mipmap.ic_back) { activity?.finish() }
            right("搜索") { "点击了搜索 ${mSearchEdit.text.toString().trim { it <= ' ' }}".toastSuc() }
        }

        _topic.text = "Toolbar 高级扩展"
    }
}