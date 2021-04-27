package com.chen.app.ui.simple.toolbar

import android.graphics.Rect
import android.view.inputmethod.EditorInfo
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.databinding.Fragment1Binding
import com.chen.app.databinding.Fragment5Binding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.view.CleanableEditText
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.toastSuc
import kotlinx.android.synthetic.main.fragment_5.*

@Launch
class ToolbarExtend2Fragment : BaseSimpleFragment() {

    //    override val contentLayoutId = R.layout.fragment_5
    override val binding by doBinding(Fragment5Binding::inflate)
    private var key = ""

    private lateinit var mSearchEdit: CleanableEditText

    override fun initAndObserve() {

        toolbar.run {

            center("扩展搜索")

            //toolbar底部扩展
            mSearchEdit = extend<CleanableEditText>(
                R.layout.layout_search_edit,
                margRect = Rect(15, 0, 15, 10)
            ).apply {

            }

            mSearchEdit.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    "点击了搜索 ${mSearchEdit.text.toString().trim { it <= ' ' }}".toastSuc()
                    return@setOnEditorActionListener true
                }
                false
            }

            left(R.mipmap.ic_back) { activity?.finish() }

            divider(0)

            right("搜索") { "点击了搜索 ${mSearchEdit.text.toString().trim { it <= ' ' }}".toastSuc() }
        }

        _topic.text = "Toolbar 高级扩展"
    }
}