package com.chen.app.k_line.draw

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.chen.app.R
import com.chen.app.k_line.BaseKChartView
import com.chen.app.k_line.base.IChartDraw
import com.chen.app.k_line.base.IValueFormatter
import com.chen.app.k_line.entity.IMACD
import com.chen.app.k_line.formatter.ValueFormatter

/**
 * macd实现类
 * Created by tifezh on 2016/6/19.
 */
class MACDDraw(view: BaseKChartView) : IChartDraw<IMACD> {
    private val mRedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mGreenPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDIFPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDEAPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mMACDPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**macd 中柱子的宽度 */
    private var mMACDWidth = 0f
    override fun drawTranslated(
        lastPoint: IMACD?,
        curPoint: IMACD,
        lastX: Float,
        curX: Float,
        canvas: Canvas,
        view: BaseKChartView,
        position: Int
    ) {
        drawMACD(canvas, view, curX, curPoint.macd)
        view.drawChildLine(canvas, mDIFPaint, lastX, lastPoint!!.dea, curX, curPoint.dea)
        view.drawChildLine(canvas, mDEAPaint, lastX, lastPoint.dif, curX, curPoint.dif)
    }

    override fun drawText(canvas: Canvas, view: BaseKChartView, position: Int, x: Float, y: Float) {
        var x = x
        var text = ""
        val point = view.getItem(position) as IMACD
        text = "DIF:" + view.formatValue(point.dif) + " "
        canvas.drawText(text, x, y, mDEAPaint)
        x += mDIFPaint.measureText(text)
        text = "DEA:" + view.formatValue(point.dea) + " "
        canvas.drawText(text, x, y, mDIFPaint)
        x += mDEAPaint.measureText(text)
        text = "MACD:" + view.formatValue(point.macd) + " "
        canvas.drawText(text, x, y, mMACDPaint)
    }

    override fun getMaxValue(point: IMACD): Float {
        return Math.max(point.macd, Math.max(point.dea, point.dif))
    }

    override fun getMinValue(point: IMACD): Float {
        return Math.min(point.macd, Math.min(point.dea, point.dif))
    }

    override val valueFormatter: IValueFormatter
        get() = ValueFormatter()

    /**
     * 画macd
     * @param canvas
     * @param x
     * @param macd
     */
    private fun drawMACD(canvas: Canvas, view: BaseKChartView, x: Float, macd: Float) {
        val macdy = view.getChildY(macd)
        val r = mMACDWidth / 2
        val zeroy = view.getChildY(0f)
        if (macd > 0) {
            //               left   top   right  bottom
            canvas.drawRect(x - r, macdy, x + r, zeroy, mRedPaint)
        } else {
            canvas.drawRect(x - r, zeroy, x + r, macdy, mGreenPaint)
        }
    }

    /**
     * 设置DIF颜色
     */
    fun setDIFColor(color: Int) {
        mDIFPaint.color = color
    }

    /**
     * 设置DEA颜色
     */
    fun setDEAColor(color: Int) {
        mDEAPaint.color = color
    }

    /**
     * 设置MACD颜色
     */
    fun setMACDColor(color: Int) {
        mMACDPaint.color = color
    }

    /**
     * 设置MACD的宽度
     * @param MACDWidth
     */
    fun setMACDWidth(MACDWidth: Float) {
        mMACDWidth = MACDWidth
    }

    /**
     * 设置曲线宽度
     */
    fun setLineWidth(width: Float) {
        mDEAPaint.strokeWidth = width
        mDIFPaint.strokeWidth = width
        mMACDPaint.strokeWidth = width
    }

    /**
     * 设置文字大小
     */
    fun setTextSize(textSize: Float) {
        mDEAPaint.textSize = textSize
        mDIFPaint.textSize = textSize
        mMACDPaint.textSize = textSize
    }

    init {
        val context = view.context
        mRedPaint.color = ContextCompat.getColor(context, R.color.chart_red)
        mGreenPaint.color = ContextCompat.getColor(context, R.color.chart_green)
    }
}