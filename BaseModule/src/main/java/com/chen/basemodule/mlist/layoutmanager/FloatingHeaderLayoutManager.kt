package com.chen.basemodule.mlist.layoutmanager

import android.graphics.Rect
import android.util.Log
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.chen.basemodule.BuildConfig.DEBUG
import java.lang.reflect.InvocationTargetException
import java.util.*

class FloatingHeaderLayoutManager(private val floatingPositionList: MutableList<Int> = mutableListOf()) :
    RecyclerView.LayoutManager() {

    private var mTotalHeight = 0
    private var mExtentHeight = 0
    private var verticallyScrollOffset = 0
    private val viewInfoArray = SparseArray<ViewInfo?>()
    private val floatingViewInfoArray = SparseArray<ViewInfo?>()
    private val itemHeightArray = SparseIntArray()
    private var firstVisibleItemPosition = 0
    private var lastVisibleItemPosition = -1

    private val floatingView: MutableList<View?> = ArrayList()
    private var vhListInPoll: ArrayList<*>? = null
    private var mAttachedScrap: ArrayList<RecyclerView.ViewHolder>? = null

    internal inner class ViewInfo {
        var view: View? = null
        var position = 0
        var measureTop = 0
        var measureBottom = 0
        var rect = Rect()
        var alwaysStick = true
    }

    override fun isAutoMeasureEnabled(): Boolean = true

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        if (DEBUG) {
            Log.i("FloatingHeader", "onLayoutChildren  $state")
        }
        if (state.itemCount == 0) {
            removeAndRecycleAllViews(recycler)
            viewInfoArray.clear()
            itemHeightArray.clear()
            floatingViewInfoArray.clear()
            floatingView.clear()
            verticallyScrollOffset = 0
            return
        }
        detachAndScrapAttachedViews(recycler)
        floatingViewInfoArray.clear()
        floatingView.clear()
        relayout(recycler, state)
    }

    private fun relayout(recycler: Recycler, state: RecyclerView.State) {
        var itemFinalY = 0
        var position = 0
        //根据滚动距离计算第一个可见的Item
        while (itemHeightArray.size() != 0 && position < itemCount - 1) {
            val height = itemHeightArray[position]
            itemFinalY += height
            position++
            if (itemFinalY - verticallyScrollOffset >= 0) {
                itemFinalY -= height
                position--
                break
            }
        }
        firstVisibleItemPosition = position
        val visibleHeight = height - paddingTop - paddingBottom
        //在Relayout之前所有的View已经detach回收,这里只需要填充可见部分并获得最后一个可见Item
        while (true) {
            itemFinalY += fillFromTop(recycler, state, position)
            position++
            if (itemFinalY > visibleHeight + verticallyScrollOffset || position == itemCount) {
                position--
                break
            }
        }
        lastVisibleItemPosition = position
        //修正悬浮Item的层级，是其显示于最顶层
        fixFloatingOrder(recycler)
        mTotalHeight =
            if (viewInfoArray.size() >= itemCount) realContentHeight
            else computeVerticalScrollRange(state)
        mExtentHeight = computeVerticalScrollExtent(state)
    }

    private fun layoutItem(position: Int, recycler: Recycler): Int {
        val viewInfo = viewInfoArray[position]
        addView(viewInfo!!.view)
        measureChildWithMargins(viewInfo.view!!, 0, 0)
        layoutDecoratedWithMargins(
            viewInfo.view!!,
            viewInfo.rect.left,
            viewInfo.rect.top,
            viewInfo.rect.right,
            viewInfo.rect.bottom
        )
        //悬浮Item被添加后，此处缓存起来
        if (floatingPositionList.contains(position) && floatingViewInfoArray[position] == null) {
            floatingViewInfoArray.put(position, viewInfo)
            if (!floatingView.contains(viewInfo.view)) {
                floatingView.add(viewInfo.view)
            }
        }
        if ((viewInfo.rect.bottom < paddingTop || viewInfo.rect.top > height - paddingBottom)
            && !floatingPositionList.contains(position)) {
            //非悬浮Item，布局后如果此Item依然超出屏幕范围，则回收此Item
            removeAndRecycleView(viewInfo.view!!, recycler)
        }
        return viewInfo.rect.height()
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler, state: RecyclerView.State): Int {
        val lastHeight = mTotalHeight - mExtentHeight
        var consume = dy
        //边界锁定
        if (verticallyScrollOffset + dy < 0) {
            consume = -verticallyScrollOffset
        } else if (verticallyScrollOffset + dy > lastHeight && mTotalHeight >= mExtentHeight) {
            consume = lastHeight - verticallyScrollOffset
        }
        if (consume == 0) {
            return 0
        }
        verticallyScrollOffset += consume
        //偏移我们自己缓存的View的边框属性
        offsetViewInfoArray(consume)
        //偏移已经添加在试图上的View
        offsetChildrenVertical(-consume)

        if (dy > 0) {
            //向上滑动，遍历回收顶部不可见Item，添加底部空白部分，直到空白部分被填满
            while (true) {
                recycleTopItem(recycler)
                if (fillBottomItem(recycler, state) <= 0) break
            }
        } else {
            //向上滑动，遍历回收底部不可见Item，添加顶部空白部分，直到空白部分被填满
            while (true) {
                recycleBottomItem(recycler)
                if (fillTopItem(recycler, state) <= 0) break
            }
        }
        //修正悬浮Item的层级
        fixFloatingOrder(recycler)
        return consume
    }

    private fun fixFloatingOrder(recycler: Recycler) {
        var top = 0
        for (i in floatingPositionList.indices) {
            val position = floatingPositionList[i]
            val viewInfo = viewInfoArray[position]
            if (viewInfo?.view != null) {
                val measureTop = viewInfo.measureTop
                //该 rect 在offset以及fill时会偏移到当前显示区域，根据该区域的top决定该View的新的Top
                val curTop = viewInfo.rect.top

                var nViewInfo: ViewInfo? = null
                if (floatingPositionList.size > i + 1) {
                    nViewInfo = viewInfoArray[floatingPositionList[i + 1]]
                }
                if (nViewInfo != null && viewInfo.rect.bottom >= nViewInfo.rect.top) {
                    //当前顶部小于最小顶部
                    viewInfo.rect.offset(0, measureTop - verticallyScrollOffset - curTop)
                } else if (curTop < top) {
                    //当前顶部小于最小顶部
                    viewInfo.rect.offset(0, top - curTop)
                } else {
                    //当前顶部大于最小顶部
                    if (top >= measureTop - verticallyScrollOffset) {
                        //理论值还是比最小的小,还原滚动程度
                        viewInfo.rect.offset(0, top - curTop)
                    } else {
                        //理论是大于最小值了，必须偏移到理论值
                        viewInfo.rect.offset(0, measureTop - verticallyScrollOffset - curTop)
                    }
                }
                removeView(viewInfo.view)
                viewInfo.view = next(recycler, position)
                layoutItem(position, recycler)
                top += viewInfo.rect.height()
            }
        }
    }

    private fun next(recycler: Recycler, position: Int): View? {
        if (floatingPositionList.contains(position)) {
            val viewInfo = floatingViewInfoArray[position]
            if (viewInfo?.view != null) {
                return viewInfo.view
            }
        }
        return recycler.getViewForPosition(position)
    }

    private fun offsetViewInfoArray(dy: Int) {
        val size = viewInfoArray.size()
        for (i in 0 until size) {
            val key = viewInfoArray.keyAt(i)
            val viewInfo = viewInfoArray[key]
            viewInfo?.rect?.offset(0, -dy)
        }
    }

    private fun fillTopItem(recycler: Recycler, state: RecyclerView.State): Int {
        val firstViewInfo = viewInfoArray[firstVisibleItemPosition]
        val position = firstVisibleItemPosition - 1
        if (position == -1) {
            return 0
        }
        val itemFinalY = firstViewInfo!!.measureTop - verticallyScrollOffset
        if (itemFinalY <= paddingTop) {
            return 0
        }
        //底部的Item在回收，但是此处获取到的view可能是悬浮的View，因为悬浮的View此处不回收，所以没有使用到回收池的view holder，可能导致回收池溢出
        val view = next(recycler, position)
        measureChildWithMargins(view!!, 0, 0)
        val height = getDecoratedMeasuredHeight(view)
        val width = getDecoratedMeasuredWidth(view)
        val layoutParams = view.layoutParams as RecyclerView.LayoutParams
        val leftMargin = layoutParams.leftMargin
        val topMargin = layoutParams.topMargin
        val bottomMargin = layoutParams.bottomMargin
        itemHeightArray.put(position, height + topMargin + bottomMargin)
        var viewInfo = viewInfoArray[position]
        if (viewInfo == null) {
            viewInfo = ViewInfo()
        }
        val left = paddingLeft + leftMargin
        val top = itemFinalY - bottomMargin - height
        viewInfo!!.rect[left, top, left + width] = top + height
        viewInfo.position = position
        viewInfo.view = view
        viewInfo.measureTop = firstViewInfo.measureTop - bottomMargin - height - topMargin
        viewInfo.measureBottom = firstViewInfo.measureTop
        viewInfoArray.put(position, viewInfo)
        layoutItem(position, recycler)
        firstVisibleItemPosition = position
        return viewInfo.rect.height()
    }

    private fun recycleTopItem(recycler: Recycler): Int {
        if (lastVisibleItemPosition <= 0) {
            firstVisibleItemPosition = 0
            return 0
        }
        if (firstVisibleItemPosition > lastVisibleItemPosition) {
            firstVisibleItemPosition = lastVisibleItemPosition
            return 0
        }
        while (floatingPositionList.contains(firstVisibleItemPosition)) {
            firstVisibleItemPosition++
        }

        val viewInfo = viewInfoArray[firstVisibleItemPosition] ?: return 0

        if (viewInfo.rect.bottom <= paddingTop) {
            removeAndRecycleView(viewInfo.view!!, recycler)
            if (DEBUG) {
                reflectGetViewHolderAndLog(viewInfo.view)
            }
            firstVisibleItemPosition++
            return viewInfo.rect.height()
        }
        return 0
    }

    private fun recycleBottomItem(recycler: Recycler): Int {

        if (lastVisibleItemPosition < firstVisibleItemPosition) {
            lastVisibleItemPosition = firstVisibleItemPosition
            return 0
        }
        while (floatingPositionList.contains(lastVisibleItemPosition)) {
            lastVisibleItemPosition--
        }
        val viewInfo = viewInfoArray[lastVisibleItemPosition]
        if (viewInfo!!.rect.top >= height - paddingBottom) {
            removeAndRecycleView(viewInfo.view!!, recycler)
            reflectGetViewHolderAndLog(viewInfo.view)
            lastVisibleItemPosition--
            return viewInfo.rect.height()
        }
        return 0
    }

    private fun fillBottomItem(recycler: Recycler, state: RecyclerView.State): Int {
        val lastViewInfo = viewInfoArray[lastVisibleItemPosition]
        val position = lastVisibleItemPosition + 1
        if (position == state.itemCount) {
            return 0
        }
        val measureBottom = lastViewInfo?.measureBottom ?: paddingTop
        val itemFinalY = measureBottom - verticallyScrollOffset
        val visibleHeight = height - paddingBottom - paddingTop
        if (itemFinalY >= visibleHeight) {
            return 0
        }
        val view = next(recycler, position)
        measureChildWithMargins(view!!, 0, 0)
        val height = getDecoratedMeasuredHeight(view)
        val width = getDecoratedMeasuredWidth(view)
        val layoutParams = view.layoutParams as RecyclerView.LayoutParams
        val leftMargin = layoutParams.leftMargin
        val topMargin = layoutParams.topMargin
        val bottomMargin = layoutParams.bottomMargin
        itemHeightArray.put(position, height + topMargin + bottomMargin)
        var viewInfo = viewInfoArray[position]
        if (viewInfo == null) {
            viewInfo = ViewInfo()
        }
        val left = paddingLeft + leftMargin
        val top = itemFinalY + paddingTop + topMargin
        viewInfo!!.rect[left, top, left + width] = top + height
        viewInfo.position = position
        viewInfo.view = view
        viewInfo.measureTop = measureBottom
        viewInfo.measureBottom = measureBottom + height + topMargin + bottomMargin
        viewInfoArray.put(position, viewInfo)
        layoutItem(position, recycler)
        lastVisibleItemPosition = position
        return viewInfo.rect.height()
    }

    private fun fillFromTop(recycler: Recycler, state: RecyclerView.State, position: Int): Int {
        val firstViewInfo = viewInfoArray[position - 1]
        val lastItemBottom = firstViewInfo?.measureBottom ?: 0
        val view = next(recycler, position)
        measureChildWithMargins(view!!, 0, 0)
        val height = getDecoratedMeasuredHeight(view)
        val width = getDecoratedMeasuredWidth(view)
        val layoutParams = view.layoutParams as RecyclerView.LayoutParams
        val leftMargin = layoutParams.leftMargin
        val topMargin = layoutParams.topMargin
        val bottomMargin = layoutParams.bottomMargin
        itemHeightArray.put(position, height + topMargin + bottomMargin)
        var viewInfo = viewInfoArray[position]
        if (viewInfo == null) {
            viewInfo = ViewInfo()
        }
        val left = paddingLeft + leftMargin
        val top = lastItemBottom - verticallyScrollOffset + topMargin
        viewInfo!!.rect[left, top, left + width] = top + height
        viewInfo.position = position
        viewInfo.view = view
        viewInfo.measureTop = lastItemBottom
        viewInfo.measureBottom = lastItemBottom + topMargin + bottomMargin + height
        viewInfoArray.put(position, viewInfo)
        layoutItem(position, recycler)
        return viewInfo.rect.height()
    }

    private fun reflectGetViewHolderAndLog(view: View?) {
        val layoutParams = view!!.layoutParams as RecyclerView.LayoutParams
        if (layoutParams != null) {
            val aClass: Class<out RecyclerView.LayoutParams> = layoutParams.javaClass
            try {
                val mViewHolder = aClass.getDeclaredField("mViewHolder")
                mViewHolder.isAccessible = true
                val viewHolder = mViewHolder[layoutParams] as RecyclerView.ViewHolder
                val holderClass: Class<out RecyclerView.ViewHolder> =
                    RecyclerView.ViewHolder::class.java
                val mFlags = holderClass.getDeclaredField("mFlags")
                mFlags.isAccessible = true
                val flag = mFlags[viewHolder] as Int
                val isScrap = holderClass.getDeclaredMethod("isScrap")
                isScrap.isAccessible = true
                val isScrapR = isScrap.invoke(viewHolder) as Boolean
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
        }
    }

    override fun computeVerticalScrollOffset(state: RecyclerView.State): Int {
        if (childCount == 0) {
            return 0
        }
        if (floatingPositionList.contains(0) && firstVisibleItemPosition == 0) {
            return verticallyScrollOffset
        }
        val viewInfoStart = viewInfoArray[firstVisibleItemPosition]
        val viewInfoEnd = viewInfoArray[lastVisibleItemPosition]
        val laidOutArea = Math.abs(
            viewInfoEnd!!.measureBottom
                    - viewInfoStart!!.measureTop
        )
        val itemRange = lastVisibleItemPosition - firstVisibleItemPosition + 1
        val avgSizePerRow = laidOutArea.toFloat() / itemRange
        val params = viewInfoStart.view!!.layoutParams as RecyclerView.LayoutParams
        val decoratedStart = getDecoratedTop(viewInfoStart.view!!) - params.topMargin
        return Math.round(firstVisibleItemPosition * avgSizePerRow + (paddingTop - decoratedStart))
    }

    private val realContentHeight: Int
        private get() {
            var range = 0
            for (i in 0 until itemCount) {
                range += itemHeightArray[i]
            }
            return range
        }

    override fun computeVerticalScrollRange(state: RecyclerView.State): Int {
        if (childCount == 0) return 0
        val viewInfoStart = viewInfoArray[firstVisibleItemPosition]
        val viewInfoEnd = viewInfoArray[lastVisibleItemPosition]
        val heightStart = viewInfoStart!!.measureTop
        val heightEnd = viewInfoEnd!!.measureBottom
        val perHeight =
            (heightEnd - heightStart) / (lastVisibleItemPosition - firstVisibleItemPosition + 1)
        return perHeight * itemCount
    }

    override fun computeVerticalScrollExtent(state: RecyclerView.State): Int {
        if (childCount == 0) {
            return 0
        }
        val viewInfoStart = viewInfoArray[firstVisibleItemPosition]
        val viewInfoEnd = viewInfoArray[lastVisibleItemPosition]
        return (viewInfoEnd!!.measureBottom - viewInfoStart!!.measureTop).coerceAtMost(height - paddingTop - paddingBottom)
    }
}