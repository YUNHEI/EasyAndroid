package com.chen.baseextend.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.chen.baseextend.R
import com.chen.basemodule.extend.color


/**
 * 圆形进度条（可设置 线性渐变-背景色-进度条颜色-圆弧宽度）
 * Created by ZWQ
 */
class CircleProgressView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val mPaint by lazy { Paint() }

    var progressPercent = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var radius = 0f //圆弧宽度
    private var rectF: RectF? = null
    var bgColor = 0
    var progressColor = 0
    var startColor = 0
    var endColor = 0
    var gradient: LinearGradient? = null
    var isGradient = false

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView)
        bgColor = typedArray.getColor(R.styleable.CircleProgressView_circleProgressBgColor, color(R.color.gray_ef))
        progressColor = typedArray.getColor(R.styleable.CircleProgressView_circleProgressColor, color(R.color.main_theme))
        radius = typedArray.getFloat(R.styleable.CircleProgressView_circleProgressRadius, WIDTH_RADIUS_RATIO)
        isGradient = typedArray.getBoolean(R.styleable.CircleProgressView_circleProgressIsGradient, false)
        startColor = typedArray.getColor(R.styleable.CircleProgressView_circleProgressStartColor, color(R.color.gray_ef))
        endColor = typedArray.getColor(R.styleable.CircleProgressView_circleProgressEndColor, color(R.color.gray_ef))
        typedArray.recycle()

        //画笔样式
        mPaint.style = Paint.Style.STROKE
        //设置笔刷的样式:圆形
        mPaint.strokeCap = Paint.Cap.ROUND
        //设置抗锯齿
        mPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth) //自定义的View能够使用wrap_content或者是match_parent的属性
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        gradient = LinearGradient(width.toFloat(), 0f, width.toFloat(), height.toFloat(), startColor, endColor, Shader.TileMode.MIRROR)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 1、绘制背景灰色圆环
        val centerX = width / 2
        val strokeWidth = centerX / radius
        mPaint.shader = null //必须设置为null，否则背景也会加上渐变色
        mPaint.strokeWidth = strokeWidth //设置画笔的大小
        mPaint.color = bgColor
        canvas.drawCircle(centerX.toFloat(), centerX.toFloat(), centerX - strokeWidth / 2, mPaint)
        // 2、绘制比例弧
        if (rectF == null) { //外切正方形
            rectF = RectF((strokeWidth / 2), (strokeWidth / 2), (2 * centerX - strokeWidth / 2), (2 * centerX - strokeWidth / 2))
        }
        //3、是否绘制渐变色
        if (isGradient) {
            mPaint.shader = gradient //设置线性渐变
        } else {
            mPaint.color = progressColor
        }
        canvas.drawArc(rectF!!, (-90).toFloat(), 3.6f * progressPercent, false, mPaint) //画比例圆弧
    }

    companion object {
        const val WIDTH_RADIUS_RATIO = 8f // 弧线半径 : 弧线线宽 (比例)
        const val MAX = 100
    }
}