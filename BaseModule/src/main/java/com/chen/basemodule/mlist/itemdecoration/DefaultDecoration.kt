package com.chen.basemodule.mlist.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chen.basemodule.mlist.BaseMultiAdapter

class DefaultDecoration(var columns: Int = 1, var mPadding: Int = 0) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        (parent.adapter as? BaseMultiAdapter<*>)?.run {

            val position = parent.getChildAdapterPosition(view)
            if (isItem(position)) {
                val realP = position - headerViewDelegates.size
                val row = realP.rem(columns)
                outRect.run {
                    left = mPadding - row * mPadding / columns
                    right = row.inc() * mPadding / columns
                    bottom = mPadding
                    if (realP < columns) {
                        top = mPadding
                    }
                }
            }
        }
    }
}