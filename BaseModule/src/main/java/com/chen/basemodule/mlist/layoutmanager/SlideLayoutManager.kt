package com.chen.basemodule.mlist.layoutmanager

import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

/**
 * Created by 钉某人
 * github: https://github.com/DingMouRen
 * email: naildingmouren@gmail.com
 */
class SlideLayoutManager(val mRecyclerView: RecyclerView, val mItemTouchHelper: ItemTouchHelper) : RecyclerView.LayoutManager() {

    private fun <T> checkIsNull(t: T?): T {
        if (t == null) {
            throw NullPointerException()
        }
        return t
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val itemCount = itemCount
        if (itemCount > ItemConfig.DEFAULT_SHOW_ITEM) {
            for (position in ItemConfig.DEFAULT_SHOW_ITEM downTo 0) {
                val view = recycler.getViewForPosition(position)
                addView(view)
                measureChildWithMargins(view, 0, 0)
                val widthSpace = width - getDecoratedMeasuredWidth(view)
                val heightSpace = height - getDecoratedMeasuredHeight(view)
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 5,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 5 + getDecoratedMeasuredHeight(view))
                if (position == ItemConfig.DEFAULT_SHOW_ITEM) {
                    view.scaleX = 1 - (position - 1) * ItemConfig.DEFAULT_SCALE
                    view.scaleY = 1 - (position - 1) * ItemConfig.DEFAULT_SCALE
                    view.translationY = (position - 1) * view.measuredHeight / ItemConfig.DEFAULT_TRANSLATE_Y.toFloat()
                } else if (position > 0) {
                    view.scaleX = 1 - position * ItemConfig.DEFAULT_SCALE
                    view.scaleY = 1 - position * ItemConfig.DEFAULT_SCALE
                    view.translationY = position * view.measuredHeight / ItemConfig.DEFAULT_TRANSLATE_Y.toFloat()
                } else {
                    view.setOnTouchListener(mOnTouchListener)
                }
            }
        } else {
            for (position in itemCount - 1 downTo 0) {
                val view = recycler.getViewForPosition(position)
                addView(view)
                measureChildWithMargins(view, 0, 0)
                val widthSpace = width - getDecoratedMeasuredWidth(view)
                val heightSpace = height - getDecoratedMeasuredHeight(view)
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 5,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 5 + getDecoratedMeasuredHeight(view))
                if (position > 0) {
                    view.scaleX = 1 - position * ItemConfig.DEFAULT_SCALE
                    view.scaleY = 1 - position * ItemConfig.DEFAULT_SCALE
                    view.translationY = position * view.measuredHeight / ItemConfig.DEFAULT_TRANSLATE_Y.toFloat()
                } else {
                    view.setOnTouchListener(mOnTouchListener)
                }
            }
        }
    }

    private val mOnTouchListener = OnTouchListener { v, event ->
        val childViewHolder = mRecyclerView.getChildViewHolder(v)
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
            mItemTouchHelper.startSwipe(childViewHolder)
        }
        false
    }
}