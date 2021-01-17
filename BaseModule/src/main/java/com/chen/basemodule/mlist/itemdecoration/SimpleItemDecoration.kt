package com.chen.basemodule.mlist.itemdecoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chen.basemodule.mlist.BaseMultiAdapter

class SimpleItemDecoration(val divider: Divider) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.adapter?.itemCount ?: 0 <= 0) return
        if (parent.adapter !is BaseMultiAdapter<*>) return

        when (parent.layoutManager) {
            is GridLayoutManager -> offsetGrid((parent.layoutManager as GridLayoutManager).spanCount, outRect, view, parent, state)
            is LinearLayoutManager -> offsetLinear(
                outRect,
                view,
                parent,
                state,
                (parent.layoutManager as LinearLayoutManager).orientation == LinearLayoutManager.VERTICAL,
                (parent.adapter as BaseMultiAdapter<*>)
            )
        }
    }

    private fun offsetLinear(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State, vertical: Boolean, adapter: BaseMultiAdapter<*>) {
        val position: Int = parent.getChildAdapterPosition(view)
        if (position < 0) return
        if (adapter.isItem(position) && position > adapter.headerViewDelegates.size)
            divider.run {
                if (vertical) outRect.top = if (dividerMarginRect == null) hDivider else (dividerMarginRect.top + dividerMarginRect.bottom + hDivider)
                else outRect.left = if (dividerMarginRect == null) hDivider else (dividerMarginRect.left + dividerMarginRect.right + hDivider)
            }
    }

    private fun offsetGrid(span: Int, outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        divider.run {
            val position = parent.getChildAdapterPosition(view)
            val offsetV = vDivider / 2
            val offsetH = hDivider / 2

            val itemCount = (parent.adapter?.itemCount ?: 0)
            if (position in 0 until itemCount) {
                //第一排，顶部不画
                if (position < span) {
                    if (position % span == 0) {
                        //最左边的，左边不画
                        outRect.set(recyclerViewPadding, 0, offsetV, offsetH)
                    } else if (position % span == span - 1) {
                        //最右边，右边不画
                        outRect.set(offsetV, 0, recyclerViewPadding, offsetH)
                    } else {
                        outRect.set(offsetV, 0, offsetV, offsetH)
                    }
                } else if ((position + span) / itemCount == 1) {
                    // 最后一排，底部不画
                    if (position % span == 0) {
                        //最左边的，左边不画
                        outRect.set(recyclerViewPadding, offsetH, offsetV, 0)
                    } else if (position % span == span - 1) {
                        //最右边，右边不画
                        outRect.set(offsetV, offsetH, recyclerViewPadding, 0)
                    } else {
                        outRect.set(offsetV, offsetH, offsetV, 0)
                    }
                } else {
                    //上下的分割线，就从第二排开始，每个区域的顶部直接添加设定大小，不用再均分了
                    if (position % span == 0) {
                        outRect.set(recyclerViewPadding, offsetH, offsetV, offsetH)
                    } else if (position % span == span - 1) {
                        outRect.set(offsetV, offsetH, recyclerViewPadding, offsetH)
                    } else {
                        outRect.set(offsetV, offsetH, offsetV, offsetH)
                    }
                }
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (parent.adapter?.itemCount ?: 0 <= 0) return
        if (parent.adapter !is BaseMultiAdapter<*>) return

        when (parent.layoutManager) {
            is GridLayoutManager -> drawGrid((parent.layoutManager as GridLayoutManager).spanCount, c, parent, state)
            is LinearLayoutManager -> drawLinear(c, parent, state, (parent.layoutManager as LinearLayoutManager).orientation == LinearLayoutManager.VERTICAL, parent.adapter as BaseMultiAdapter<*>)
        }
    }

    private fun drawLinear(c: Canvas, parent: RecyclerView, state: RecyclerView.State, vertical: Boolean, adapter: BaseMultiAdapter<*>) {
        for (i in 0 until parent.getChildCount()) {
            val view: View = parent.getChildAt(i)
            val position: Int = parent.getChildAdapterPosition(view)
            if (position < 0) return
            if (adapter.isItem(position) && position > adapter.headerViewDelegates.size)
                divider.run {
                    if (vertical)
                        c.drawRect(
                            view.left.toFloat() + (dividerMarginRect?.left ?: 0),
                            view.top.toFloat() - (dividerMarginRect?.bottom ?: 0) - hDivider,
                            view.right.toFloat() - (dividerMarginRect?.right ?: 0),
                            view.top.toFloat() - (dividerMarginRect?.bottom ?: 0),
                            paint
                        )
                    else
                        c.drawRect(
                            view.left.toFloat() + (dividerMarginRect?.right ?: 0) - hDivider,
                            view.top.toFloat() + (dividerMarginRect?.top ?: 0),
                            view.left.toFloat() + (dividerMarginRect?.right ?: 0),
                            view.bottom.toFloat() - (dividerMarginRect?.bottom ?: 0),
                            paint
                        )
                }

        }
    }

    private fun drawGrid(span: Int, c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        divider.run {
            val offsetV = vDivider / 2f
            val offsetH = hDivider.toFloat()

            for (position in 0 until parent.childCount) {
                val view = parent.getChildAt(position)
                val totalOutRect = Rect()
                totalOutRect.run {
                    var pos = 0
                    // 找到当前decoration的位置
                    for (index in 0 until parent.itemDecorationCount) {
                        val dec = parent.getItemDecorationAt(index)
                        if (dec == this@SimpleItemDecoration) {
                            pos = index
                            break
                        }
                    }
                    for (index in 0 until pos) {
                        val outRect = Rect()
                        parent.getItemDecorationAt(index).getItemOffsets(outRect, view, parent, state)
                        left += outRect.left
                        right += outRect.right
                        top += outRect.top
                        bottom += outRect.bottom
                    }
                }
                val myOutRect = Rect()
                getItemOffsets(myOutRect, view, parent, state)

                //第一排，顶部不画
                if (position < span) {
                    if (position % span == 0) {
                        //最左边的，左边不画
                        // 画右边
                        c.drawRect(
                            view.right.toFloat() + totalOutRect.right,
                            view.top.toFloat(),
                            view.right + offsetV + totalOutRect.right,
                            view.bottom.toFloat() + totalOutRect.bottom + myOutRect.bottom,
                            paint
                        )
                        // 画底边
                        c.drawRect(
                            view.left.toFloat(),
                            view.bottom.toFloat() + totalOutRect.bottom,
                            view.right.toFloat() + totalOutRect.right + myOutRect.right,
                            view.bottom + totalOutRect.bottom + offsetH,
                            paint
                        )
                    } else if (position % span == span - 1) {
                        // 最右边，右边不画
                        // 画左边
                        c.drawRect(
                            view.left - totalOutRect.left - offsetV,
                            view.top.toFloat(),
                            view.left - totalOutRect.left.toFloat(),
                            view.bottom.toFloat() + totalOutRect.bottom + myOutRect.bottom,
                            paint
                        )
                        // 画底边
                        c.drawRect(
                            view.left.toFloat() - totalOutRect.left - myOutRect.left,
                            view.bottom.toFloat() + totalOutRect.bottom,
                            view.right.toFloat(),
                            view.bottom + totalOutRect.bottom + offsetH,
                            paint
                        )
                    } else {
                        // 中间的
                        // 画左边
                        c.drawRect(
                            view.left - offsetV - totalOutRect.left,
                            view.top.toFloat(),
                            view.left.toFloat() - totalOutRect.left,
                            view.bottom.toFloat() + totalOutRect.bottom + myOutRect.bottom,
                            paint
                        )
                        // 画右边
                        c.drawRect(
                            view.right.toFloat() + totalOutRect.right,
                            view.top.toFloat(),
                            view.right + offsetV + totalOutRect.right,
                            view.bottom.toFloat() + totalOutRect.bottom + myOutRect.bottom,
                            paint
                        )
                        // 画底边
                        c.drawRect(
                            view.left.toFloat() - totalOutRect.left - myOutRect.left,
                            view.bottom.toFloat() + totalOutRect.bottom,
                            view.right.toFloat() + totalOutRect.right + myOutRect.right,
                            view.bottom + totalOutRect.bottom + offsetH,
                            paint
                        )
                    }
                } else if ((position + span) / (parent.adapter?.itemCount!!) == 1) {
                    // 最后一排，底部不画
                    if (position % span == 0) {
                        //最左边的，左边不画
                        // 画右边
                        c.drawRect(
                            view.right.toFloat() + totalOutRect.right,
                            view.top.toFloat() - totalOutRect.top - myOutRect.top,
                            view.right + offsetV + totalOutRect.right,
                            view.bottom.toFloat(),
                            paint
                        )
                        // 画顶边
                        c.drawRect(
                            view.left.toFloat(),
                            view.top.toFloat() - totalOutRect.top - offsetH,
                            view.right.toFloat() + totalOutRect.right + myOutRect.right,
                            view.top.toFloat() - totalOutRect.top,
                            paint
                        )
                    } else if (position % span == span - 1) {
                        // 最右边，右边不画
                        // 画左边
                        c.drawRect(
                            view.left - totalOutRect.left - offsetV,
                            view.top.toFloat() - totalOutRect.top - myOutRect.top,
                            view.left - totalOutRect.left.toFloat(),
                            view.bottom.toFloat(),
                            paint
                        )
                        // 画顶边
                        c.drawRect(
                            view.left.toFloat() - totalOutRect.left - myOutRect.left,
                            view.top.toFloat() - totalOutRect.top - offsetH,
                            view.right.toFloat(),
                            view.top.toFloat() - totalOutRect.top,
                            paint
                        )
                    } else {
                        // 中间的
                        // 画左边
                        c.drawRect(
                            view.left - offsetV - totalOutRect.left,
                            view.top.toFloat() - totalOutRect.top - myOutRect.top,
                            view.left.toFloat() - totalOutRect.left,
                            view.bottom.toFloat(),
                            paint
                        )
                        // 画右边
                        c.drawRect(
                            view.right.toFloat() + totalOutRect.right,
                            view.top.toFloat() - totalOutRect.top - myOutRect.top,
                            view.right + offsetV + totalOutRect.right,
                            view.bottom.toFloat(),
                            paint
                        )
                        // 画顶边
                        c.drawRect(
                            view.left.toFloat() - totalOutRect.left - myOutRect.left,
                            view.top.toFloat() - totalOutRect.top - offsetH,
                            view.right.toFloat() + totalOutRect.right + myOutRect.right,
                            view.top.toFloat() - totalOutRect.top,
                            paint
                        )
                    }
                } else {
                    //上下的分割线，就从第二排开始，每个区域的顶部直接添加设定大小，不用再均分了
                    if (position % span == 0) {
                        // 最左列
                        // 画右边
                        c.drawRect(
                            view.right.toFloat() + totalOutRect.right,
                            view.top.toFloat() - totalOutRect.top - myOutRect.top,
                            view.right + totalOutRect.right + offsetV,
                            view.bottom.toFloat() + totalOutRect.bottom + myOutRect.bottom,
                            paint
                        )
                        // 画顶边
                        c.drawRect(
                            view.left.toFloat(),
                            view.top - totalOutRect.top - offsetH,
                            view.right.toFloat() + totalOutRect.right + myOutRect.right,
                            view.top.toFloat() - totalOutRect.top,
                            paint
                        )
                        // 画底边
                        c.drawRect(
                            view.left.toFloat(),
                            view.bottom.toFloat() + totalOutRect.bottom,
                            view.right.toFloat() + totalOutRect.right + myOutRect.right,
                            view.bottom.toFloat() + totalOutRect.bottom + offsetH,
                            paint
                        )
                    } else if (position % span == span - 1) {
                        // 最右列
                        // 画左边
                        c.drawRect(
                            view.left.toFloat() - totalOutRect.left - offsetH,
                            view.top.toFloat() - totalOutRect.top - myOutRect.top,
                            view.left.toFloat() - totalOutRect.left,
                            view.bottom.toFloat() + totalOutRect.bottom + myOutRect.bottom,
                            paint
                        )
                        // 画顶边
                        c.drawRect(
                            view.left.toFloat() - totalOutRect.left - myOutRect.left,
                            view.top - totalOutRect.top - offsetH,
                            view.right.toFloat(),
                            view.top.toFloat() - totalOutRect.top,
                            paint
                        )
                        // 画底边
                        c.drawRect(
                            view.left.toFloat() - totalOutRect.left - myOutRect.left,
                            view.bottom.toFloat() + totalOutRect.bottom,
                            view.right.toFloat(),
                            view.bottom.toFloat() + totalOutRect.bottom + offsetH,
                            paint
                        )
                    } else {
                        //中间的，都画
                        // 画左边
                        c.drawRect(
                            view.left.toFloat() - totalOutRect.left - offsetV,
                            view.top.toFloat() - totalOutRect.top - myOutRect.top,
                            view.left.toFloat() - totalOutRect.left,
                            view.bottom.toFloat() + totalOutRect.bottom + myOutRect.bottom,
                            paint
                        )
                        // 画右边
                        c.drawRect(
                            view.right.toFloat() + totalOutRect.right,
                            view.top.toFloat() - totalOutRect.top - myOutRect.top,
                            view.right + totalOutRect.right + offsetV,
                            view.bottom.toFloat() + totalOutRect.bottom + myOutRect.bottom,
                            paint
                        )
                        // 画顶边
                        c.drawRect(
                            view.left.toFloat() - totalOutRect.left - myOutRect.left,
                            view.top - totalOutRect.top - offsetH,
                            view.right.toFloat() + totalOutRect.right + myOutRect.right,
                            view.top.toFloat() - totalOutRect.top,
                            paint
                        )
                        // 画底边
                        c.drawRect(
                            view.left.toFloat() - totalOutRect.left - myOutRect.left,
                            view.bottom.toFloat() + totalOutRect.bottom,
                            view.right.toFloat() + totalOutRect.right + myOutRect.right,
                            view.bottom.toFloat() + totalOutRect.bottom + offsetH,
                            paint
                        )
                    }
                }
            }
        }
    }
}



