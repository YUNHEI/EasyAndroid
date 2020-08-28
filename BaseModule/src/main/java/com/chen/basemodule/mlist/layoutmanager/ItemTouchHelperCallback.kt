package com.chen.basemodule.mlist.layoutmanager

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.mlist.BaseMAdapter

/**
 * Created by 钉某人
 * github: https://github.com/DingMouRen
 * email: naildingmouren@gmail.com
 */
class ItemTouchHelperCallback<T: RootBean>(val adapter: BaseMAdapter<T>, var mListener: OnSlideListener<T>? = null) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = 0
        var slideFlags = 0
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is SlideLayoutManager) {
            slideFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }
        return makeMovementFlags(dragFlags, slideFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewHolder.itemView.setOnTouchListener(null)
        val layoutPosition = viewHolder.layoutPosition
        val remove: T = adapter.removeItem(layoutPosition)
        adapter.notifyDataSetChanged()
        mListener?.onSlided(viewHolder, remove, if (direction == ItemTouchHelper.LEFT) ItemConfig.SLIDED_LEFT else ItemConfig.SLIDED_RIGHT)
        if (adapter.itemCount == 0) {
            mListener?.onClear()
        }
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            var ratio = dX / getThreshold(recyclerView, viewHolder)
            if (ratio > 1) {
                ratio = 1f
            } else if (ratio < -1) {
                ratio = -1f
            }
            itemView.rotation = ratio * ItemConfig.DEFAULT_ROTATE_DEGREE
            val childCount = recyclerView.childCount
            if (childCount > ItemConfig.DEFAULT_SHOW_ITEM) {
                for (position in 1 until childCount - 1) {
                    val index = childCount - position - 1
                    val view = recyclerView.getChildAt(position)
                    view.scaleX = 1 - index * ItemConfig.DEFAULT_SCALE + Math.abs(ratio) * ItemConfig.DEFAULT_SCALE
                    view.scaleY = 1 - index * ItemConfig.DEFAULT_SCALE + Math.abs(ratio) * ItemConfig.DEFAULT_SCALE
                    view.translationY = (index - Math.abs(ratio)) * itemView.measuredHeight / ItemConfig.DEFAULT_TRANSLATE_Y
                }
            } else {
                for (position in 0 until childCount - 1) {
                    val index = childCount - position - 1
                    val view = recyclerView.getChildAt(position)
                    view.scaleX = 1 - index * ItemConfig.DEFAULT_SCALE + Math.abs(ratio) * ItemConfig.DEFAULT_SCALE
                    view.scaleY = 1 - index * ItemConfig.DEFAULT_SCALE + Math.abs(ratio) * ItemConfig.DEFAULT_SCALE
                    view.translationY = (index - Math.abs(ratio)) * itemView.measuredHeight / ItemConfig.DEFAULT_TRANSLATE_Y
                }
            }
            if (mListener != null) {
                if (ratio != 0f) {
                    mListener?.onSliding(viewHolder, ratio, if (ratio < 0) ItemConfig.SLIDING_LEFT else ItemConfig.SLIDING_RIGHT)
                } else {
                    mListener?.onSliding(viewHolder, ratio, ItemConfig.SLIDING_NONE)
                }
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.rotation = 0f
    }

    private fun getThreshold(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Float {
        return recyclerView.width * getSwipeThreshold(viewHolder)
    }
}