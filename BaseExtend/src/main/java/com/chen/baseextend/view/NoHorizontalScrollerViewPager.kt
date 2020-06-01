package com.chen.baseextend.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

import androidx.viewpager.widget.ViewPager

/**
 * Created by zejian
 * Time  16/1/7 上午11:12
 * Email shinezejian@163.com
 * Description:不可横向滑动的ViewPager
 */
class NoHorizontalScrollerViewPager : ViewPager {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    /**
     * 重写拦截事件，返回值设置为false，这时便不会横向滑动了。
     *
     * @param ev
     * @return
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

    /**
     * 重写拦截事件，返回值设置为false，这时便不会横向滑动了。
     *
     * @param ev
     * @return
     */
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return false
    }
}
