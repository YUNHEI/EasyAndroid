package com.chen.baseextend.widget.qrcode

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MotionEvent
import com.journeyapps.barcodescanner.ViewfinderView
import com.chen.baseextend.R
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.dp2px

/**
 * @author alan
 * @date 2019/3/7
 */
internal class CustomViewfinderView(context: Context, attrs: AttributeSet) : ViewfinderView(context, attrs) {
/* ****************************************** 边角线相关属性 ************************************************/
    /**
     * "边角线长度/扫描边框长度"的占比 (比例越大，线越长)
     */
    private var mLineRate = 0.05f

    /**
     * 边角线厚度 (建议使用dp)
     */
    private var mLineDepth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics)

    /**
     * 边角线颜色
     */
    private var mLineColor = color(R.color.qr_code)
/* ******************************************* 扫描线相关属性 ************************************************/
    /**
     * 扫描线起始位置
     */
    private var mScanLinePosition = 0

    /**
     * 扫描线厚度
     */
    private var mScanLineDepth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics)

    /**
     * 扫描线每次重绘的移动距离
     */
    var mScanLineDy = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)

    /**
     * 线性梯度
     */
    lateinit var mLinearGradient: LinearGradient

    /**
     * 线性梯度位置
     */
    var mPositions = floatArrayOf(0f, 0.5f, 1f)

    /**
     * 线性梯度各个位置对应的颜色值
     */
    lateinit var nowScanRect: Rect
    var buttonRect: RectF? = null
    var laserColor_center = getContext().resources.getColor(R.color.main_theme)
    var laserColor_light = getContext().resources.getColor(R.color.trans00)
    var tip = "tip"
    var text_btn = "个性化需求"
    var screenWidth = 0
    var screenHeight = 0

    //扫描线渐变色
    var mScanLineColor = intArrayOf(laserColor_light, laserColor_center, laserColor_light)

    init {
        val manager = (getContext() as Activity).windowManager
        val outMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(outMetrics)
        screenWidth = outMetrics.widthPixels
        screenHeight = outMetrics.heightPixels
        val w = screenWidth * 3 / 5
        val marginL = screenWidth / 5
        val marginT = screenHeight / 2 - dp2px(125)
        nowScanRect = Rect(marginL, marginT, w + marginL, w + marginT)
    }

    fun drawText(canvas: Canvas, mScanRect: Rect) {
        val mTxetPaint = Paint()
        mTxetPaint.apply {
            color = context.resources.getColor(R.color.white)
            textSize = dp2px(14f)
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText(tip, (screenWidth / 2).toFloat(), (mScanRect.top - dp2px(22f)), mTxetPaint)
    }

    fun drawButton(canvas: Canvas, mScanRect: Rect) {
        val buttonPaint = Paint()
        buttonPaint.apply {
            isAntiAlias = true
            color = context.resources.getColor(R.color.white)
            strokeWidth = 1f
            style = Paint.Style.STROKE
        }
        var height = dp2px(40f)
        var left = mScanRect.left + (mScanRect.right - mScanRect.left) / 6
        var top = mScanRect.bottom + dp2px(48f)
        var right = mScanRect.right - (mScanRect.right - mScanRect.left) / 6
        var bottom = mScanRect.bottom + dp2px(48f) + height
        buttonRect = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
        buttonRect?.let {
            canvas.drawRoundRect(it, dp2px(20f).toFloat(), dp2px(20f).toFloat(), buttonPaint)
        }
        buttonPaint.apply {
            color = context.resources.getColor(R.color.white)
            textSize = dp2px(14f).toFloat()
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
        }
        var fontMetrics = buttonPaint.fontMetricsInt
        // var baseLine = buttonRect.centerY() + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
        buttonRect?.let {
            val baseLine = (it.top + it.bottom - fontMetrics.top - fontMetrics.bottom) / 2
            canvas.drawText(text_btn, it.centerX(), baseLine, buttonPaint)
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        refreshSizes()
        if (framingRect == null || previewFramingRect == null) {
            return
        }
//   framingRect = nowScanRect
        val frame = nowScanRect
        val previewFrame = previewFramingRect
        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.color = if (resultBitmap != null) resultColor else maskColor
        canvas.drawRect(0f, 0f, width.toFloat(), frame.top.toFloat(), paint)
        canvas.drawRect(0f, frame.top.toFloat(), frame.left.toFloat(), (frame.bottom + 1).toFloat(), paint)
        canvas.drawRect((frame.right + 1).toFloat(), frame.top.toFloat(), width.toFloat(), (frame.bottom + 1).toFloat(), paint)
        canvas.drawRect(0f, (frame.bottom + 1).toFloat(), width.toFloat(), height.toFloat(), paint)
//  drawText(canvas, frame)
//        drawButton(canvas, frame)
//绘制4个角
        paint.color = mLineColor
        // 定义画笔的颜色
//左上-横线
        canvas.drawRect(frame.left.toFloat() - mLineDepth,
                frame.top.toFloat() - mLineDepth,
                frame.left + frame.width() * mLineRate,
                frame.top.toFloat(), paint)
//左上-纵线
        canvas.drawRect(frame.left.toFloat() - mLineDepth, frame.top.toFloat(), frame.left.toFloat(), frame.top + frame.height() * mLineRate, paint)
//右上-横线
        canvas.drawRect(frame.right - frame.width() * mLineRate, frame.top.toFloat() - mLineDepth, frame.right.toFloat() + mLineDepth, frame.top.toFloat(), paint)
//右上-纵线
        canvas.drawRect(frame.right.toFloat(), frame.top.toFloat() - mLineDepth, frame.right.toFloat() + mLineDepth, frame.top + frame.height() * mLineRate, paint)
//左下-横线
        canvas.drawRect(frame.left.toFloat() - mLineDepth, frame.bottom.toFloat(), frame.left + frame.width() * mLineRate, frame.bottom.toFloat() + mLineDepth, paint)
//左下-纵线
        canvas.drawRect(frame.left.toFloat() - mLineDepth, frame.bottom - frame.height() * mLineRate, frame.left.toFloat(), frame.bottom.toFloat(), paint)
//右下-横线
        canvas.drawRect(frame.right - frame.width() * mLineRate, frame.bottom.toFloat(), frame.right.toFloat() + mLineDepth, frame.bottom.toFloat() + mLineDepth, paint)
//右下-纵线
        canvas.drawRect(frame.right.toFloat(), frame.bottom - frame.height() * mLineRate, frame.right.toFloat() + mLineDepth, frame.bottom.toFloat() + mLineDepth, paint)
        if (resultBitmap != null) {
// Draw the opaque result bitmap over the scanning rectangle
            paint.alpha = ViewfinderView.CURRENT_POINT_OPACITY
            canvas.drawBitmap(resultBitmap, null, frame, paint)
        } else {
//  drawLaserLine(canvas,frame)
// 绘制扫描线
            mScanLinePosition += mScanLineDy.toInt()
            if (mScanLinePosition > frame.height()) {
                mScanLinePosition = 0
            }
            mLinearGradient = LinearGradient(frame.left.toFloat(), (frame.top + mScanLinePosition).toFloat(), frame.right.toFloat(), (frame.top + mScanLinePosition).toFloat(), mScanLineColor, mPositions, Shader.TileMode.CLAMP)
            paint.shader = mLinearGradient
            canvas.drawRect(frame.left.toFloat(), (frame.top + mScanLinePosition).toFloat(), frame.right.toFloat(), frame.top.toFloat() + mScanLinePosition.toFloat() + mScanLineDepth, paint)
            paint.shader = null
            val scaleX = frame.width() / previewFrame.width().toFloat()
            val scaleY = frame.height() / previewFrame.height().toFloat()
            val currentPossible = possibleResultPoints
            val currentLast = lastPossibleResultPoints
            val frameLeft = frame.left
            val frameTop = frame.top
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null
            } else {
                possibleResultPoints = ArrayList(5)
                lastPossibleResultPoints = currentPossible
                paint.alpha = ViewfinderView.CURRENT_POINT_OPACITY
                paint.color = resultPointColor
                for (point in currentPossible) {
                    canvas.drawCircle((frameLeft + (point.x * scaleX).toInt()).toFloat(), (frameTop + (point.y * scaleY).toInt()).toFloat(), ViewfinderView.POINT_SIZE.toFloat(), paint)
                }
            }
            if (currentLast != null) {
                paint.alpha = ViewfinderView.CURRENT_POINT_OPACITY / 2
                paint.color = resultPointColor
                val radius = ViewfinderView.POINT_SIZE / 2.0f
                for (point in currentLast) {
                    canvas.drawCircle((frameLeft + (point.x * scaleX).toInt()).toFloat(), (frameTop + (point.y * scaleY).toInt()).toFloat(), radius, paint)
                }
            }
        }// Request another update at the animation interval, but only repaint the laser line, // not the entire viewfinde
// rmask.
        postInvalidateDelayed(CUSTOME_ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom)
    }

    interface InputView {
        fun inputClick()
    }

    var inputView: InputView? = null
    fun setInputView(doInput: () -> Unit) {
        this.inputView = object : InputView {
            override fun inputClick() {
                doInput()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event?.x
                val y = event?.y
                buttonRect?.let {
                    if (it.contains(x, y)) {
                        inputView?.inputClick()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    companion object {
        /**
         * 重绘时间间隔
         */
        val CUSTOME_ANIMATION_DELAY: Long = 16
    }
}