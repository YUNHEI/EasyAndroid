package com.chen.baseextend.base.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.chen.basemodule.basem.BaseSimActivity


/**
 * standard activity
 *  Created by chen on 2019/6/10
 **/
open class BaseInputActivity : BaseSimActivity() {

    private val mChildOfContent by lazy { fragment!!.view!! }
    private var usableHeightPrevious = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<View>(android.R.id.content).viewTreeObserver.addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) { // keyboard probably just became visible
                mChildOfContent.layoutParams.height = usableHeightSansKeyboard - heightDifference
            } else { // keyboard probably just became hidden
                mChildOfContent.layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT
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