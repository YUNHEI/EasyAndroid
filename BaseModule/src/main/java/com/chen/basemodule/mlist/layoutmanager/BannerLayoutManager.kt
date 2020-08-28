package com.chen.basemodule.mlist.layoutmanager

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import java.lang.ref.WeakReference

/**
 * Created by 钉某人
 * github: https://github.com/DingMouRen
 * email: naildingmouren@gmail.com
 */
class BannerLayoutManager(context: Context?, val recyclerView: RecyclerView, val realCount: Int, orientation: Int = RecyclerView.HORIZONTAL) : LinearLayoutManager(context) {

    private val mLinearSnapHelper by lazy { LinearSnapHelper() }

    private var mOnSelectedViewListener: OnSelectedViewListener? = null
    private var mCurrentPosition = 0
    private val mHandler by lazy { TaskHandler(this) }
    private var mTimeDelayed: Long = 1000
    private var mOrientation: Int
    private var mTimeSmooth = 150f

    init {
        setOrientation(orientation)
        this.mOrientation = orientation
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        mLinearSnapHelper.attachToRecyclerView(view)
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State, position: Int) {
        val smoothScroller: LinearSmoothScroller =
                object : LinearSmoothScroller(recyclerView.context) {
                    // 返回：滑过1px时经历的时间(ms)。
                    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                        return mTimeSmooth / displayMetrics.densityDpi
                    }
                }
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == RecyclerView.SCROLL_STATE_IDLE) { //滑动停止
            val view = mLinearSnapHelper.findSnapView(this)
            mCurrentPosition = getPosition(view!!)
            mOnSelectedViewListener?.onSelectedView(view, mCurrentPosition % realCount)
            mHandler.setSendMsg(true)
            val msg = Message.obtain()
            mCurrentPosition++
            msg.what = mCurrentPosition
            mHandler.sendMessageDelayed(msg, mTimeDelayed)
        } else if (state == RecyclerView.SCROLL_STATE_DRAGGING) { //拖动
            mHandler.setSendMsg(false)
        }
    }

    fun setTimeDelayed(timeDelayed: Long) {
        mTimeDelayed = timeDelayed
    }

    fun setTimeSmooth(timeSmooth: Float) {
        mTimeSmooth = timeSmooth
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        mHandler.setSendMsg(true)
        val msg = Message.obtain()
        msg.what = mCurrentPosition + 1
        mHandler.sendMessageDelayed(msg, mTimeDelayed)
    }

    fun setOnSelectedViewListener(listener: OnSelectedViewListener?) {
        mOnSelectedViewListener = listener
    }

    /**
     * 停止时，显示在中间的View的监听
     */
    interface OnSelectedViewListener {
        fun onSelectedView(view: View?, position: Int)
    }

    private class TaskHandler(bannerLayoutManager: BannerLayoutManager) : Handler() {
        private val mWeakBanner by lazy { WeakReference(bannerLayoutManager) }
        private var mSendMsg = false
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (mSendMsg) {
                val position = msg.what
                val bannerLayoutManager = mWeakBanner.get()
                bannerLayoutManager?.recyclerView?.smoothScrollToPosition(position)
            }
        }

        fun setSendMsg(sendMsg: Boolean) {
            mSendMsg = sendMsg
        }
    }
}