package com.chen.baseextend.base.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.chen.baseextend.R
import com.chen.basemodule.basem.BaseSimActivity


/**
 * standard activity
 *  Created by chen on 2019/6/10
 **/
open class BaseInputActivity : BaseSimActivity() {

    private lateinit var mChildOfContent: View
    private var usableHeightPrevious = 0
    private var frameLayoutParams: FrameLayout.LayoutParams? = null

    override val contentLayoutId = R.layout.base_input_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val content = findViewById<View>(android.R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0).apply {
            viewTreeObserver.addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
            frameLayoutParams = layoutParams as FrameLayout.LayoutParams
        }
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) { // keyboard probably just became visible
                frameLayoutParams!!.height = usableHeightSansKeyboard - heightDifference
            } else { // keyboard probably just became hidden
                frameLayoutParams!!.height = FrameLayout.LayoutParams.MATCH_PARENT
            }
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return r.bottom // 全屏模式下： return r.bottom
    }
}