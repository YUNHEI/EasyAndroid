//package com.chen.basemodule.mlist.layoutmanager
//
//import android.animation.ValueAnimator
//import android.animation.ValueAnimator.AnimatorUpdateListener
//import android.view.View
//import android.view.animation.LinearInterpolator
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView.Recycler
//
//
//class StackLayoutManager : RecyclerView.LayoutManager() {
//    /**
//     * 一次完整的聚焦滑动所需要的移动距离
//     */
//    private var onceCompleteScrollLength = -1f
//
//    /**
//     * 第一个子view的偏移量
//     */
//    private var firstChildCompleteScrollLength = -1f
//
//    /**
//     * 屏幕可见第一个view的position
//     */
//    private var mFirstVisiPos = 0
//
//    /**
//     * 屏幕可见的最后一个view的position
//     */
//    private var mLastVisiPos = 0
//
//    /**
//     * 水平方向累计偏移量
//     */
//    private var mHorizontalOffset: Long = 0
//
//    /**
//     * view之间的margin
//     */
//    private val normalViewGap = 30f
//
//    private var childWidth = 0
//
//    /**
//     * 是否自动选中
//     */
//    private val isAutoSelect = true
//
//    // 选中动画
//    private var selectAnimator: ValueAnimator? = null
//
//    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
//        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
//    }
//
//    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: RecyclerView.State): Int {
//        // 手指从右向左滑动，dx > 0; 手指从左向右滑动，dx < 0;
//        // 位移0、没有子View 当然不移动
//        var dx = dx
//        if (dx == 0 || childCount == 0) {
//            return 0
//        }
//
//        // 误差处理
//        val realDx = dx / 1.0f
//        if (Math.abs(realDx) < 0.00000001f) {
//            return 0
//        }
//        mHorizontalOffset += dx.toLong()
//        dx = fill(recycler, state, dx)
//        return dx
//    }
//
//    private fun fill(recycler: Recycler, state: RecyclerView.State, dx: Int): Int {
//        var resultDelta = dx
//        resultDelta = fillHorizontalLeft(recycler, state, dx)
//        recycleChildren(recycler)
//        return resultDelta
//    }
//
//    private fun fillHorizontalLeft(recycler: Recycler, state: RecyclerView.State, dx: Int): Int {
//        //----------------1、边界检测-----------------
//        var dx = dx
//        if (dx < 0) {
//            // 已到达左边界
//            if (mHorizontalOffset < 0) {
//                dx = 0
//                mHorizontalOffset = dx.toLong()
//            }
//        }
//        if (dx > 0) {
//            if (mHorizontalOffset >= getMaxOffset()) {
//                // 根据最大偏移量来计算滑动到最右侧边缘
//                mHorizontalOffset = getMaxOffset() as Long
//                dx = 0
//            }
//        }
//
//        // 分离全部的view，加入到临时缓存
//        detachAndScrapAttachedViews(recycler)
//        var startX = 0f
//        var fraction = 0f
//        val isChildLayoutLeft = true
//        var tempView: View? = null
//        var tempPosition = -1
//        if (onceCompleteScrollLength == -1f) {
//            // 因为mFirstVisiPos在下面可能被改变，所以用tempPosition暂存一下
//            tempPosition = mFirstVisiPos
//            tempView = recycler.getViewForPosition(tempPosition)
//            measureChildWithMargins(tempView, 0, 0)
//            childWidth = getDecoratedMeasurementHorizontal(tempView)
//        }
//
//        // 修正第一个可见view mFirstVisiPos 已经滑动了多少个完整的onceCompleteScrollLength就代表滑动了多少个item
//        firstChildCompleteScrollLength = width / 2 + childWidth / 2.toFloat()
//        if (mHorizontalOffset >= firstChildCompleteScrollLength) {
//            startX = normalViewGap
//            onceCompleteScrollLength = childWidth + normalViewGap
//            mFirstVisiPos = Math.floor(Math.abs(mHorizontalOffset - firstChildCompleteScrollLength) / onceCompleteScrollLength.toDouble()).toInt() + 1
//            fraction = Math.abs(mHorizontalOffset - firstChildCompleteScrollLength) % onceCompleteScrollLength / (onceCompleteScrollLength * 1.0f)
//        } else {
//            mFirstVisiPos = 0
//            startX = getMinOffset()
//            onceCompleteScrollLength = firstChildCompleteScrollLength
//            fraction = Math.abs(mHorizontalOffset) % onceCompleteScrollLength / (onceCompleteScrollLength * 1.0f)
//        }
//
//        // 临时将mLastVisiPos赋值为getItemCount() - 1，放心，下面遍历时会判断view是否已溢出屏幕，并及时修正该值并结束布局
//        mLastVisiPos = itemCount - 1
//        val normalViewOffset = onceCompleteScrollLength * fraction
//        var isNormalViewOffsetSetted = false
//
//        //----------------3、开始布局-----------------
//        for (i in mFirstVisiPos..mLastVisiPos) {
//            var item: View
//            item = if (i == tempPosition && tempView != null) {
//                // 如果初始化数据时已经取了一个临时view
//                tempView
//            } else {
//                recycler.getViewForPosition(i)
//            }
//            addView(item)
//            measureChildWithMargins(item, 0, 0)
//            if (!isNormalViewOffsetSetted) {
//                startX -= normalViewOffset
//                isNormalViewOffsetSetted = true
//            }
//            var l: Int
//            var t: Int
//            var r: Int
//            var b: Int
//            l = startX.toInt()
//            t = paddingTop
//            r = l + getDecoratedMeasurementHorizontal(item)
//            b = t + getDecoratedMeasurementVertical(item)
//            layoutDecoratedWithMargins(item, l, t, r, b)
//            startX += childWidth + normalViewGap
//            if (startX > width - paddingRight) {
//                mLastVisiPos = i
//                break
//            }
//        }
//        return dx
//    }
//
//    /**
//     * 最大偏移量
//     *
//     * @return
//     */
//    private fun getMaxOffset(): Float {
//        return if (childWidth == 0 || itemCount == 0) 0 else (childWidth + normalViewGap) * (itemCount - 1)
//    }
//
//    /**
//     * 获取某个childView在水平方向所占的空间，将margin考虑进去
//     *
//     * @param view
//     * @return
//     */
//    fun getDecoratedMeasurementHorizontal(view: View): Int {
//        val params = view.layoutParams as RecyclerView.LayoutParams
//        return (getDecoratedMeasuredWidth(view) + params.leftMargin
//                + params.rightMargin)
//    }
//
//    /**
//     * 获取某个childView在竖直方向所占的空间,将margin考虑进去
//     *
//     * @param view
//     * @return
//     */
//    fun getDecoratedMeasurementVertical(view: View): Int {
//        val params = view.layoutParams as RecyclerView.LayoutParams
//        return (getDecoratedMeasuredHeight(view) + params.topMargin
//                + params.bottomMargin)
//    }
//
//    /**
//     * 回收需回收的Item。
//     */
//    private fun recycleChildren(recycler: Recycler) {
//        val scrapList = recycler.scrapList
//        for (i in scrapList.indices) {
//            val holder = scrapList[i]
//            removeAndRecycleView(holder.itemView, recycler)
//        }
//    }
//
//    override fun onScrollStateChanged(state: Int) {
//        super.onScrollStateChanged(state)
//        when (state) {
//            RecyclerView.SCROLL_STATE_DRAGGING ->                 //当手指按下时，停止当前正在播放的动画
//                cancelAnimator()
//            RecyclerView.SCROLL_STATE_IDLE ->                 //当列表滚动停止后，判断一下自动选中是否打开
//                if (isAutoSelect) {
//                    //找到离目标落点最近的item索引
//                    smoothScrollToPosition(findShouldSelectPosition())
//                }
//            else -> {
//            }
//        }
//    }
//
//    /**
//     * 平滑滚动到某个位置
//     *
//     * @param position 目标Item索引
//     */
//    fun smoothScrollToPosition(position: Int) {
//        if (position > -1 && position < itemCount) {
//            startValueAnimator(position)
//        }
//    }
//
//    private fun findShouldSelectPosition(): Int {
//        if (onceCompleteScrollLength == -1f || mFirstVisiPos == -1) {
//            return -1
//        }
//        val position = (Math.abs(mHorizontalOffset) / (childWidth + normalViewGap)).toInt()
//        val remainder = (Math.abs(mHorizontalOffset) % (childWidth + normalViewGap)).toInt()
//        // 超过一半，应当选中下一项
//        if (remainder >= (childWidth + normalViewGap) / 2.0f) {
//            if (position + 1 <= itemCount - 1) {
//                return position + 1
//            }
//        }
//        return position
//    }
//
//    private fun startValueAnimator(position: Int) {
//        cancelAnimator()
//        val distance: Float = getScrollToPositionOffset(position)
//        val minDuration: Long = 100
//        val maxDuration: Long = 300
//        val duration: Long
//        val distanceFraction = Math.abs(distance) / (childWidth + normalViewGap)
//        duration = if (distance <= childWidth + normalViewGap) {
//            (minDuration + (maxDuration - minDuration) * distanceFraction).toLong()
//        } else {
//            (maxDuration * distanceFraction).toLong()
//        }
//        selectAnimator = ValueAnimator.ofFloat(0.0f, distance)
//        selectAnimator.setDuration(duration)
//        selectAnimator.setInterpolator(LinearInterpolator())
//        val startedOffset = mHorizontalOffset.toFloat()
//        selectAnimator.addUpdateListener(AnimatorUpdateListener { animation ->
//            val value = animation.animatedValue as Float
//            mHorizontalOffset = (startedOffset + value).toLong()
//            requestLayout()
//        })
//        selectAnimator.start()
//    }
//}