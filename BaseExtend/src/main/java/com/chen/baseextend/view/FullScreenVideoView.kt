package com.chen.baseextend.view

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView

class FullScreenVideoView(context: Context, attrs: AttributeSet? = null) : VideoView(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec))
    }
}