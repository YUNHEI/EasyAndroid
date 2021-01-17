package com.chen.basemodule.mlist.itemdecoration

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.annotation.ColorInt

data class Divider(
    // 横分割线粗细
    val hDivider: Int = 0,
    // 竖分割线粗细
    val vDivider: Int = 0,
    @ColorInt val color: Int = Color.TRANSPARENT,
    // 左右边距
    val recyclerViewPadding: Int = 0,
    // linearLayoutManager的情况下，支持设置divider的margin
    val dividerMarginRect: Rect? = null
) {
    val paint: Paint

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color
        paint.style = Paint.Style.FILL
    }
}