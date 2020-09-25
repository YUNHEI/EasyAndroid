package com.chen.app.k_line.draw

import android.graphics.Canvas
import android.graphics.Paint
import com.chen.app.k_line.BaseKChartView
import com.chen.app.k_line.base.IChartDraw
import com.chen.app.k_line.base.IValueFormatter
import com.chen.app.k_line.entity.IRSI
import com.chen.app.k_line.formatter.ValueFormatter

/**
 * RSI实现类
 * Created by tifezh on 2016/6/19.
 */
class RSIDraw(view: BaseKChartView?) : IChartDraw<IRSI> {
    private val mRSI1Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRSI2Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRSI3Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun drawTranslated(
        lastPoint: IRSI?,
        curPoint: IRSI,
        lastX: Float,
        curX: Float,
        canvas: Canvas,
        view: BaseKChartView,
        position: Int
    ) {
        view.drawChildLine(canvas, mRSI1Paint, lastX, lastPoint!!.rsi1, curX, curPoint.rsi1)
        view.drawChildLine(canvas, mRSI2Paint, lastX, lastPoint.rsi2, curX, curPoint.rsi2)
        view.drawChildLine(canvas, mRSI3Paint, lastX, lastPoint.rsi3, curX, curPoint.rsi3)
    }

    override fun drawText(canvas: Canvas, view: BaseKChartView, position: Int, x: Float, y: Float) {
        var x = x
        var text = ""
        val point = view.getItem(position) as IRSI
        text = "RSI1:" + view.formatValue(point.rsi1) + " "
        canvas.drawText(text, x, y, mRSI1Paint)
        x += mRSI1Paint.measureText(text)
        text = "RSI2:" + view.formatValue(point.rsi2) + " "
        canvas.drawText(text, x, y, mRSI2Paint)
        x += mRSI2Paint.measureText(text)
        text = "RSI3:" + view.formatValue(point.rsi3) + " "
        canvas.drawText(text, x, y, mRSI3Paint)
    }

    override fun getMaxValue(point: IRSI): Float {
        return Math.max(point.rsi1, Math.max(point.rsi2, point.rsi3))
    }

    override fun getMinValue(point: IRSI): Float {
        return Math.min(point.rsi1, Math.min(point.rsi2, point.rsi3))
    }

    override val valueFormatter: IValueFormatter
        get() = ValueFormatter()

    fun setRSI1Color(color: Int) {
        mRSI1Paint.color = color
    }

    fun setRSI2Color(color: Int) {
        mRSI2Paint.color = color
    }

    fun setRSI3Color(color: Int) {
        mRSI3Paint.color = color
    }

    /**
     * 设置曲线宽度
     */
    fun setLineWidth(width: Float) {
        mRSI1Paint.strokeWidth = width
        mRSI2Paint.strokeWidth = width
        mRSI3Paint.strokeWidth = width
    }

    /**
     * 设置文字大小
     */
    fun setTextSize(textSize: Float) {
        mRSI2Paint.textSize = textSize
        mRSI3Paint.textSize = textSize
        mRSI1Paint.textSize = textSize
    }
}