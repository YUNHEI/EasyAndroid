package com.chen.basemodule.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import kotlin.math.absoluteValue
import kotlin.math.sign

class NestedScrollableHost(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs)  {

    private val touchSlop by lazy { ViewConfiguration.get(context).scaledTouchSlop }

    private var initialX = 0f
    private var initialY = 0f
    private val parentViewPager: ViewPager2?
        get() {
            var cur = parent
            while (cur != null) {
                if (cur is ViewPager2) return cur
                cur = cur.parent
            }
            return null
        }

    private fun canChildScroll(orientation: Int, delta: Float): Boolean {

        val direction = -delta.sign.toInt()

        return if (orientation == 0) {
            getChildAt(0)?.canScrollHorizontally(direction)
        } else {
            getChildAt(0)?.canScrollVertically(direction)
        } ?: false
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        handleInterceptTouchEvent(e)
        return super.onInterceptTouchEvent(e)
    }

    private fun handleInterceptTouchEvent(e: MotionEvent) {
        parentViewPager?.orientation?.run {
            // Early return if child can't scroll in same direction as parent

            if (!canChildScroll(this, -1f) && !canChildScroll(this, 1f)) {
                return
            }

            if (e.action == MotionEvent.ACTION_DOWN) {
                initialX = e.x
                initialY = e.y
                parent.requestDisallowInterceptTouchEvent(true)
            } else if (e.action == MotionEvent.ACTION_MOVE) {
                val dx = e.x - initialX
                val dy = e.y - initialY
                val isVpHorizontal = this == ORIENTATION_HORIZONTAL

                // assuming ViewPager2 touch-slop is 2x touch-slop of child
                val scaledDx = dx.absoluteValue * if (isVpHorizontal) .5f else 1f
                val scaledDy = dy.absoluteValue * if (isVpHorizontal) 1f else .5f

                if (scaledDx > touchSlop || scaledDy > touchSlop) {
                    if (isVpHorizontal == (scaledDy > scaledDx)) {
                        // Gesture is perpendicular, allow all parents to intercept
                        parent.requestDisallowInterceptTouchEvent(false)
                    } else {
                        // Gesture is parallel, query child if movement in that direction is possible
                        if (canChildScroll(this, if (isVpHorizontal) dx else dy)) {
                            // Child can scroll, disallow all parents to intercept
                            parent.requestDisallowInterceptTouchEvent(true)
                        } else {
                            // Child cannot scroll, allow all parents to intercept
                            parent.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                }
            }
        }
    }
}