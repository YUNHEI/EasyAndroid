package com.chen.basemodule.view

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.children
import com.chen.basemodule.R
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.dp2px
import com.chen.basemodule.extend.drawable
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Created by zhouweixian on 2016/1/23.
 */
class GuideView(context: Context) : RelativeLayout(context), OnGlobalLayoutListener {

    private val bitmap by lazy { Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)!! }

    /**
     * Canvas,绘制bitmap
     */
    private val mCanvas: Canvas by lazy { Canvas(bitmap) }

    // 背景画笔
    private val bgPaint by lazy { Paint().apply { color = color(R.color.trans_cc_22) } }

    private val targetPaint by lazy {
        Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
            isAntiAlias = true
        }
    }

    private var first = true

    /**
     * GuideView 偏移量
     */
    private var offsetX = 0
    private var offsetY = 0

    /**
     * targetView 的外切圆半径
     */
    var radius = 10

    val isShow get() = parent != null

    //步骤完成
    var completeClosure: ((guide: GuideView) -> Unit)? = null

    var closeClosure: ((guide: GuideView) -> Unit)? = null

    /**
     * 需要显示提示信息的View
     */
    var targetView: View? = null

    var rect: Rect? = null

    var targetHalfHeight = 0

    var targetHalfWidth = 0

    /**
     * 自定义View
     */
    var customGuideView: View? = null


    /**
     * targetView是否已测量
     */
    private var isMeasured = false

    /**
     * targetView圆心
     */
    private val center: IntArray = IntArray(2)


    /**
     * 相对于targetView的位置.在target的那个方向
     */
    var direction: Direction = Direction.BOTTOM

    /**
     * 形状
     */
    var myShape: MyShape = MyShape.RECTANGULAR

    private val _cancel by lazy {
        TextView(context).apply {
            text = "跳过"
            textSize = 16f
            setTextColor(color(R.color.white))
            setOnClickListener {
                closeClosure?.invoke(this@GuideView)
                dismiss()
            }
        }
    }

    init {
        background = drawable(R.color.trans00)

        addView(_cancel, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            addRule(ALIGN_PARENT_LEFT)
            addRule(ALIGN_PARENT_TOP)
            setMargins(dp2px(15), dp2px(30), 0, 0)
        })
    }

    fun dismiss() {
        targetView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
        removeAllViews()
        if (isShow) {
            ((context as Activity).window.decorView as FrameLayout).removeView(this)
        }
    }

    fun show() {
        if (!isShow) {
            targetView?.viewTreeObserver?.addOnGlobalLayoutListener(this)
            ((context as Activity).window.decorView as FrameLayout).addView(this)
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (abs(center[0] - event.x) < targetHalfWidth && abs(center[1] - event.y) < targetHalfHeight) {
            completeClosure?.invoke(this) ?: dismiss()
            return super.dispatchTouchEvent(event)
        }
        children.forEach {
            if (it.hasOnClickListeners() && it.x + it.width - event.x > 0 && it.y + it.height - event.y > 0) {
                return super.dispatchTouchEvent(event)
            }
        }
        return true
    }

    /**
     * 添加提示文字，位置在targetView的下边
     * 在屏幕窗口，添加蒙层，蒙层绘制总背景和透明圆形，圆形下边绘制说明文字
     */
    private fun createGuideView() {

        // Tips布局参数
        val guideViewParams: LayoutParams = customGuideView?.layoutParams as? LayoutParams ?: LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        guideViewParams.setMargins(0, center[1] + radius + 10, 0, 0)
        if (customGuideView != null) {

            val width = this.width
            val height = this.height
            val left = center[0] - radius
            val right = center[0] + radius
            val top = center[1] - radius
            val bottom = center[1] + radius
            when (direction) {
                Direction.CENTER -> {
                    guideViewParams.addRule(CENTER_IN_PARENT)
                }
                Direction.TOP -> {
                    guideViewParams.addRule(CENTER_HORIZONTAL)
                    guideViewParams.setMargins(0, dp2px(35), 0, 0)
                }
                Direction.LEFT -> {
                    this.gravity = Gravity.RIGHT
                    guideViewParams.setMargins(offsetX - width + left, top + offsetY, width - left - offsetX, -top - offsetY)
                }
                Direction.BOTTOM -> {
                    this.gravity = Gravity.CENTER_HORIZONTAL
                    guideViewParams.setMargins(offsetX, bottom + offsetY, -offsetX, -bottom - offsetY)
                }
                Direction.RIGHT -> guideViewParams.setMargins(right + offsetX, top + offsetY, -right - offsetX, -top - offsetY)
                Direction.LEFT_TOP -> {
                    this.gravity = Gravity.RIGHT or Gravity.BOTTOM
                    guideViewParams.setMargins(offsetX - width + left, offsetY - height + top, width - left - offsetX, height - top - offsetY)
                }
                Direction.LEFT_BOTTOM -> {
                    this.gravity = Gravity.RIGHT
                    guideViewParams.setMargins(offsetX - width + left, bottom + offsetY, width - left - offsetX, -bottom - offsetY)
                }
                Direction.RIGHT_TOP -> {
                    this.gravity = Gravity.BOTTOM
                    guideViewParams.setMargins(right + offsetX, offsetY - height + top, -right - offsetX, height - top - offsetY)
                }
                Direction.RIGHT_BOTTOM -> guideViewParams.setMargins(right + offsetX, bottom + offsetY, -right - offsetX, -top - offsetY)
                Direction.ABOVE -> guideViewParams.setMargins(center[0] - guideViewParams.width.shr(1) , rect!!.top -  guideViewParams.height - radius, 0, 0)
            }

            //            guideViewLayout.addView(customGuideView);
            this.addView(customGuideView, guideViewParams)
        }
    }

    /**
     * 获得targetView 的宽高，如果未测量，返回｛-1， -1｝
     *
     * @return
     */
    private val targetViewSize: IntArray
        private get() {
            val location = intArrayOf(-1, -1)
            if (isMeasured) {
                location[0] = targetView!!.width
                location[1] = targetView!!.height
            }
            return location
        }

    /**
     * 获得targetView 的半径
     *
     * @return
     */
    private val targetViewRadius: Int
        private get() {
            if (isMeasured) {
                val size = targetViewSize
                val x = size[0]
                val y = size[1]
                return (sqrt(x * x + y * y.toDouble()) / 2).toInt()
            }
            return -1
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!isMeasured) return
        if (targetView == null && rect == null) return

        // 绘制屏幕背景
        mCanvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        val oval = RectF()
        when (myShape) {
            MyShape.CIRCULAR -> mCanvas.drawCircle(center[0].toFloat(), center[1].toFloat(), radius.toFloat(), targetPaint) //绘制圆形
            MyShape.ELLIPSE -> {
                //RectF对象
                oval.left = center[0] - 150.toFloat() //左边
                oval.top = center[1] - 50.toFloat() //上边
                oval.right = center[0] + 150.toFloat() //右边
                oval.bottom = center[1] + 50.toFloat() //下边
                mCanvas.drawOval(oval, targetPaint) //绘制椭圆
            }
            MyShape.RECTANGULAR -> {
                //RectF对象
                oval.left = center[0] - targetHalfWidth - 10.toFloat() //左边
                oval.top = center[1] - targetHalfHeight - 10.toFloat() //上边
                oval.right = center[0] + targetHalfWidth + 10.toFloat() //右边
                oval.bottom = center[1] + targetHalfHeight + 10.toFloat() //下边
                mCanvas.drawRoundRect(oval, radius.toFloat(), radius.toFloat(), targetPaint) //绘制圆角矩形
            }
        }

        // 绘制到屏幕
        canvas.drawBitmap(bitmap, 0f, 0f, bgPaint)
    }

    override fun onGlobalLayout() {
        if (isMeasured) return
        if (rect != null || targetView!!.height > 0 && targetView!!.width > 0) {
            isMeasured = true
        }

        rect?.run {
            center[0] = (left + right).shr(1)
            center[1] = (bottom + top).shr(1)

            targetHalfWidth = (right - left).shr(1)
            targetHalfHeight = (bottom - top).shr(1)
        } ?: run {
            // 获取targetView的中心坐标
            // 获取右上角坐标
            val location = IntArray(2)
            targetView!!.getLocationInWindow(location)
            // 获取中心坐标
            center[0] = location[0] + targetView!!.width / 2
            center[1] = location[1] + targetView!!.height / 2

            targetHalfWidth = targetView!!.width / 2
            targetHalfHeight = targetView!!.height / 2

            rect = Rect(location[0], location[1], location[0] + targetView!!.width, location[1] + targetView!!.height)
        }

        // 获取targetView外切圆半径
        if (radius == 0) {
            radius = targetViewRadius
        }
        // 添加GuideView
        createGuideView()
    }

    /**
     * 定义GuideView相对于targetView的方位，共八种。不设置则默认在targetView下方
     */
    enum class Direction {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        LEFT_TOP,
        LEFT_BOTTOM,
        RIGHT_TOP,
        RIGHT_BOTTOM,
        CENTER,
        ABOVE
    }

    /**
     * 定义目标控件的形状，共3种。圆形，椭圆，带圆角的矩形（可以设置圆角大小），不设置则默认是圆形
     */
    enum class MyShape {
        CIRCULAR, ELLIPSE, RECTANGULAR
    }
}