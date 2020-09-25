package com.chen.app.k_line.draw

import android.graphics.Canvas
import android.graphics.Paint
import com.chen.app.k_line.BaseKChartView
import com.chen.app.k_line.base.IChartDraw
import com.chen.app.k_line.base.IValueFormatter
import com.chen.app.k_line.entity.IBOLL
import com.chen.app.k_line.formatter.ValueFormatter

/**
 * BOLL实现类
 * Created by tifezh on 2016/6/19.
 */
class BOLLDraw(view: BaseKChartView?) : IChartDraw<IBOLL> {
    private val mUpPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mMbPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDnPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun drawTranslated(
        lastPoint: IBOLL?,
        curPoint: IBOLL,
        lastX: Float,
        curX: Float,
        canvas: Canvas,
        view: BaseKChartView,
        position: Int
    ) {
        view.drawChildLine(canvas, mUpPaint, lastX, lastPoint!!.up, curX, curPoint.up)
        view.drawChildLine(canvas, mMbPaint, lastX, lastPoint!!.mb, curX, curPoint.mb)
        view.drawChildLine(canvas, mDnPaint, lastX, lastPoint!!.dn, curX, curPoint.dn)
    }

    override fun drawText(canvas: Canvas, view: BaseKChartView, position: Int, x: Float, y: Float) {
        var x = x
        var text = ""
        val point = view.getItem(position) as IBOLL
        text = "UP:" + view.formatValue(point.up) + " "
        canvas.drawText(text, x, y, mUpPaint)
        x += mUpPaint.measureText(text)
        text = "MB:" + view.formatValue(point.mb) + " "
        canvas.drawText(text, x, y, mMbPaint)
        x += mMbPaint.measureText(text)
        text = "DN:" + view.formatValue(point.dn) + " "
        canvas.drawText(text, x, y, mDnPaint)
    }

    override fun getMaxValue(point: IBOLL): Float {
        return if (java.lang.Float.isNaN(point.up)) {
            point.mb
        } else point.up
    }

    override fun getMinValue(point: IBOLL): Float {
        return if (java.lang.Float.isNaN(point.dn)) {
            point.mb
        } else point.dn
    }

    override val valueFormatter: IValueFormatter
        get() = ValueFormatter()

    /**
     * 设置up颜色
     */
    fun setUpColor(color: Int) {
        mUpPaint.color = color
    }

    /**
     * 设置mb颜色
     * @param color
     */
    fun setMbColor(color: Int) {
        mMbPaint.color = color
    }

    /**
     * 设置dn颜色
     */
    fun setDnColor(color: Int) {
        mDnPaint.color = color
    }

    /**
     * 设置曲线宽度
     */
    fun setLineWidth(width: Float) {
        mUpPaint.strokeWidth = width
        mMbPaint.strokeWidth = width
        mDnPaint.strokeWidth = width
    }

    /**
     * 设置文字大小
     */
    fun setTextSize(textSize: Float) {
        mUpPaint.textSize = textSize
        mMbPaint.textSize = textSize
        mDnPaint.textSize = textSize
    }
}