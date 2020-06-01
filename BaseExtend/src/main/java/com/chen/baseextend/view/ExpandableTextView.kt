package com.chen.baseextend.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseBooleanArray
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import com.chen.baseextend.R
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.dp2px
import kotlinx.android.synthetic.main.item_expand_collapse.view.*

/**
 * Created by chen on 2019/7/15
 */
class ExpandableTextView(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs), View.OnClickListener {

    /*是否有重新绘制*/
    private var mRelayout = false

    /*默认收起*/
    private var mCollapsed = true

    private var mExpandable = false

    /*动画执行时间*/
    private var mAnimationDuration = 0

    /*是否正在执行动画*/
    private var mAnimating = false

    /* 展开收起状态回调 */
    private var mListener: OnExpandStateChangeListener? = null

    /* listview等列表情况下保存每个item的收起/展开状态 */
    private var mCollapsedStatus: SparseBooleanArray? = null

    /* 列表位置 */
    private var mPosition = 0

    /*设置内容最大行数，超过隐藏*/
    private var mMaxCollapsedLines = 0

    /*这个linerlayout容器的高度*/
    private var mCollapsedHeight = 0

    /*内容tv真实高度（含padding）*/
    private var mTextHeightWithMaxLines = 0

    /*内容tvMarginTopAmndBottom高度*/
    private var mMarginBetweenTxtAndBottom = 0

    /*内容颜色*/
    private var contentTextColor = 0

    /*收起展开颜色*/
    private var collapseExpandTextColor = 0

    /*内容字体大小*/
    private var contentTextSize = 0f

    /*收起展字体大小*/
    private var collapseExpandTextSize = 0f

    /*收起展开位置，默认左边*/
    private var grarity = 0

    /**
     * 初始化属性
     * @param attrs
     */
    init {
        mCollapsedStatus = SparseBooleanArray()

        context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView).run {
            mMaxCollapsedLines = getInt(R.styleable.ExpandableTextView_maxCollapsedLines, MAX_COLLAPSED_LINES)
            mAnimationDuration = getInt(R.styleable.ExpandableTextView_animDuration, DEFAULT_ANIM_DURATION)
            contentTextColor = getColor(R.styleable.ExpandableTextView_contentTextColor, context.color(R.color.gray_99))
            contentTextSize = getDimension(R.styleable.ExpandableTextView_contentTextSize, dp2px(14f))
            collapseExpandTextColor = getColor(R.styleable.ExpandableTextView_collapseExpandTextColor, context.color(R.color.blue_text))
            collapseExpandTextSize = getDimension(R.styleable.ExpandableTextView_collapseExpandTextSize, dp2px(14f))
            grarity = getInt(R.styleable.ExpandableTextView_collapseExpandGrarity, Gravity.LEFT)
            recycle()
        }
        // enforces vertical orientation
        orientation = VERTICAL
        // default visibility is gone
        visibility = View.GONE
    }

    /**
     * 渲染完成时初始化view
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        findViews()
    }

    /**
     * 初始化viwe
     */
    private fun findViews() {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.item_expand_collapse, this)
        _expandable_text.setOnClickListener(if (mExpandable) null else this)
        _expandable_text.setTextColor(contentTextColor)

        _expandable_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize)
    }

    /**
     * 点击事件
     * @param view
     */
    override fun onClick(view: View) {

        mCollapsed = !mCollapsed
        //保存位置状态
        if (mCollapsedStatus != null) {
            mCollapsedStatus!!.put(mPosition, mCollapsed)
        }

        if (!mExpandable) return

        // 执行展开/收起动画
        mAnimating = true
        val valueAnimator: ValueAnimator
        if (mCollapsed) { //            mTvContent.setMaxLines(mMaxCollapsedLines);
            valueAnimator = ValueAnimator.ofInt(height, mCollapsedHeight)
        } else {
            valueAnimator = ValueAnimator.ofInt(height, height +
                    getRealTextViewHeight(_expandable_text) - _expandable_text!!.height)
        }
        valueAnimator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            _expandable_text!!.maxHeight = animatedValue - mMarginBetweenTxtAndBottom
            layoutParams.height = animatedValue
            requestLayout()
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) { // 动画结束后发送结束的信号
/// clear the animation flag
                mAnimating = false
                // notify the listener
                if (mListener != null) {
                    mListener!!.onExpandStateChanged(_expandable_text, !mCollapsed)
                }
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        valueAnimator.duration = mAnimationDuration.toLong()
        valueAnimator.start()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean { // 当动画还在执行状态时，拦截事件，不让child处理
        return mAnimating
    }

    /**
     * 重新测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) { // If no change, measure and return

        //如果内容真实行数小于等于最大行数，不处理
        if (!mRelayout || visibility == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        val a = measuredWidth
        mRelayout = false
        _expandable_text!!.maxLines = Int.MAX_VALUE
        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 如果是收起状态，重新设置最大行数
        if (mCollapsed) {
            _expandable_text!!.maxLines = mMaxCollapsedLines
        }

        mExpandable = false
        if (_expandable_text!!.lineCount <= mMaxCollapsedLines) {
            return
        }
        mExpandable = true
        // 获取内容tv真实高度（含padding）
        mTextHeightWithMaxLines = getRealTextViewHeight(_expandable_text)
        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mCollapsed) { // Gets the margin between the TextView's bottom and the ViewGroup's bottom
            _expandable_text!!.post { mMarginBetweenTxtAndBottom = height - _expandable_text!!.height }
            // 保存这个容器的测量高度
            mCollapsedHeight = measuredHeight
        }
    }

    /*********暴露给外部调用方法 */
    /**
     * 设置收起/展开监听
     * @param listener
     */
    fun setOnExpandStateChangeListener(listener: OnExpandStateChangeListener?) {
        mListener = listener
    }

    /**
     * 设置内容，列表情况下，带有保存位置收起/展开状态
     * @param text
     * @param position
     */
    fun setText(text: CharSequence?, position: Int) {
        mPosition = position
        //获取状态，如无，默认是true:收起
        mCollapsed = mCollapsedStatus!![position, true]
        clearAnimation()
        //设置收起/展开图标和文字
        setText(text)
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        requestLayout()
    }

    /**
     * 半角转全角
     * @param input String.
     * @return 全角字符串.
     */

    fun toSBC(input: String): String {
        val c = input.toCharArray();
        for (i in c.indices) {
            if (c[i] == ' ') {
                c[i] = '\u3000'
            } else if (c[i] < '\u0177') {
                c[i] = (c[i] + 65248)

            }
        }
        return  String (c);
    }

    /**
     * 设置内容
     * @param text
     */
    fun setText(text: CharSequence?) {
        mRelayout = true
        _expandable_text.text = text
        visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
    }

    /**
     * 定义状态改变接口
     */
    interface OnExpandStateChangeListener {
        /**
         * @param textView   - TextView being expanded/collapsed
         * @param isExpanded - true if the TextView has been expanded
         */
        fun onExpandStateChanged(textView: TextView?, isExpanded: Boolean)
    }

    companion object {
        /* 默认最高行数 */
        private const val MAX_COLLAPSED_LINES = 5

        /* 默认动画执行时间 */
        private const val DEFAULT_ANIM_DURATION = 200

        /**
         * 获取内容tv真实高度（含padding）
         * @param textView
         * @return
         */
        private fun getRealTextViewHeight(textView: TextView?): Int {
            val textHeight = textView!!.layout.getLineTop(textView.lineCount)
            val padding = textView.compoundPaddingTop + textView.compoundPaddingBottom
            return textHeight + padding
        }
    }
}