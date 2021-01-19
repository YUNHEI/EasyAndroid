package com.chen.baseextend.extend

import android.graphics.Paint
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chen.basemodule.constant.BasePreference
import com.chen.basemodule.extend.dp2px
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 *  Created by 86152 on 2020-04-29
 **/

fun View.stickSide() {
    if (parent is ConstraintLayout) {
        val lp = layoutParams as ConstraintLayout.LayoutParams
        lp.setMargins(0, 0, BasePreference._WAIT_PROCESS_X, BasePreference._WAIT_PROCESS_Y)
        val pointF = PointF(0f, 0f)
        var drag = false
        setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    drag = false
                    pointF.set(event.x, event.y)
                }
                MotionEvent.ACTION_MOVE -> {
                    if (abs(pointF.x - event.x) > 10 || abs(pointF.y - event.y) > 10) {
                        drag = true
                        val marginEnd = max(
                            (parent as ConstraintLayout).width - event.rawX.toInt() - width.shr(1),
                            dp2px(15)
                        )
                        val marginBottom = min(
                            max(
                                (parent as ConstraintLayout).height - event.rawY.toInt() - height.shr(
                                    1
                                ), dp2px(15)
                            ), (parent as ConstraintLayout).height - 200
                        )
                        lp.setMargins(0, 0, marginEnd, marginBottom)
                        v.requestLayout()
                        return@setOnTouchListener true
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (drag) {
                        drag = false
                        if (lp.rightMargin > dp2px(15)) {
                            startValueAnimate(lp.rightMargin, dp2px(15), 100, {}, {
                                lp.setMargins(0, 0, it.animatedValue as Int, lp.bottomMargin)
                                requestLayout()
                            })
                        }
                        BasePreference._WAIT_PROCESS_Y = lp.bottomMargin
                    } else {
                        performClick()
                    }
                    return@setOnTouchListener true
                }
            }
            false
        }
    }
}

fun TextView.bold(rate: Float = 0.8f) {
    paint.style = Paint.Style.FILL_AND_STROKE
    paint.strokeWidth = rate
}