package com.chen.app.k_line.draw

import android.graphics.Canvas
import android.graphics.Paint
import com.chen.app.k_line.BaseKChartView
import com.chen.app.k_line.base.IChartDraw
import com.chen.app.k_line.base.IValueFormatter
import com.chen.app.k_line.entity.IKDJ
import com.chen.app.k_line.formatter.ValueFormatter

/**
 * KDJ实现类
 * Created by tifezh on 2016/6/19.
 */
class KDJDraw(view: BaseKChartView?) : IChartDraw<IKDJ> {
    private val mKPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mJPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun drawTranslated(
        lastPoint: IKDJ?,
        curPoint: IKDJ,
        lastX: Float,
        curX: Float,
        canvas: Canvas,
        view: BaseKChartView,
        position: Int
    ) {
        view.drawChildLine(canvas, mKPaint, lastX, lastPoint!!.k, curX, curPoint.k)
        view.drawChildLine(canvas, mDPaint, lastX, lastPoint.d, curX, curPoint.d)
        view.drawChildLine(canvas, mJPaint, lastX, lastPoint.j, curX, curPoint.j)
    }

    override fun drawText(canvas: Canvas, view: BaseKChartView, position: Int, x: Float, y: Float) {
        var x = x
        var text = ""
        val point = view.getItem(position) as IKDJ
        text = "K:" + view.formatValue(point.k) + " "
        canvas.drawText(text, x, y, mKPaint)
        x += mKPaint.measureText(text)
        text = "D:" + view.formatValue(point.d) + " "
        canvas.drawText(text, x, y, mDPaint)
        x += mDPaint.measureText(text)
        text = "J:" + view.formatValue(point.j) + " "
        canvas.drawText(text, x, y, mJPaint)
    }

    override fun getMaxValue(point: IKDJ): Float {
        return Math.max(point.k, Math.max(point.d, point.j))
    }

    override fun getMinValue(point: IKDJ): Float {
        return Math.min(point.k, Math.min(point.d, point.j))
    }

    override val valueFormatter: IValueFormatter
        get() = ValueFormatter()

    /**
     * 设置K颜色
     */
    fun setKColor(color: Int) {
        mKPaint.color = color
    }

    /**
     * 设置D颜色
     */
    fun setDColor(color: Int) {
        mDPaint.color = color
    }

    /**
     * 设置J颜色
     */
    fun setJColor(color: Int) {
        mJPaint.color = color
    }

    /**
     * 设置曲线宽度
     */
    fun setLineWidth(width: Float) {
        mKPaint.strokeWidth = width
        mDPaint.strokeWidth = width
        mJPaint.strokeWidth = width
    }

    /**
     * 设置文字大小
     */
    fun setTextSize(textSize: Float) {
        mKPaint.textSize = textSize
        mDPaint.textSize = textSize
        mJPaint.textSize = textSize
    }
}