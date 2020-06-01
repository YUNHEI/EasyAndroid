package com.chen.baseextend.widget.dialog

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.annotation.DrawableRes

/**
 *  Created by chen on 2019/7/29
 **/
class GuidePopWindow(val context: Context, @DrawableRes drawable: Int) : PopupWindow() {

    init {

        contentView = ImageView(context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            adjustViewBounds = true
            setImageResource(drawable)
        }

        width = LinearLayout.LayoutParams.WRAP_CONTENT
        height = LinearLayout.LayoutParams.WRAP_CONTENT

    }
}