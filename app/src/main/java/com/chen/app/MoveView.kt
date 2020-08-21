package com.chen.app

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration


class MoveView(context: Context, attt: AttributeSet? = null) : View(context, attt) {

    private var mLastTouchX = 0
    private var mLastTouchY = 0
    private var mTouchSlop = 0
    private var mCanMove = false
    private var mScrollPointerId = 0
    private val mVelocityTracker by lazy { VelocityTracker.obtain() }

    init {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val actionIndex = event.actionIndex
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mScrollPointerId = event.getPointerId(0)
                mLastTouchX = (event.x + 0.5f).toInt()
                mLastTouchY = (event.y + 0.5f).toInt()
                mCanMove = false
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                mScrollPointerId = event.getPointerId(actionIndex)
                mLastTouchX = (event.getX(actionIndex) + 0.5f).toInt()
                mLastTouchY = (event.getY(actionIndex) + 0.5f).toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val index = event.findPointerIndex(mScrollPointerId)
                val x = (event.getX(index) + 0.5f).toInt()
                val y = (event.getY(index) + 0.5f).toInt()
                val dx = mLastTouchX - x
                var dy = mLastTouchY - y
                if (!mCanMove) {
                    if (Math.abs(dy) >= mTouchSlop) {
                        if (dy > 0) {
                            dy -= mTouchSlop
                        } else {
                            dy += mTouchSlop
                        }
                        mCanMove = true
                    }
                    if (Math.abs(dy) >= mTouchSlop) {
                        if (dy > 0) {
                            dy -= mTouchSlop
                        } else {
                            dy += mTouchSlop
                        }
                        mCanMove = true
                    }
                }
                if (mCanMove) {
                    offsetTopAndBottom(-dy)
                    offsetLeftAndRight(-dx)
                }
            }
            MotionEvent.ACTION_POINTER_UP -> onPointerUp(event)
            MotionEvent.ACTION_UP -> {
                val vtev = MotionEvent.obtain(event)
                mVelocityTracker.addMovement(vtev)
                mVelocityTracker.computeCurrentVelocity(1000, 10000f)
                val xvel: Float = -mVelocityTracker.getXVelocity(mScrollPointerId)
                val yvel: Float = -mVelocityTracker.getYVelocity(mScrollPointerId)

                if (!mCanMove) performClick()
            }
        }
        return true
    }

    private fun onPointerUp(e: MotionEvent) {
        val actionIndex = e.actionIndex
        if (e.getPointerId(actionIndex) == mScrollPointerId) {
            val newIndex = if (actionIndex == 0) 1 else 0
            mScrollPointerId = e.getPointerId(newIndex)
            mLastTouchX = (e.getX(newIndex) + 0.5f).toInt()
            mLastTouchY = (e.getY(newIndex) + 0.5f).toInt()
        }
    }

}