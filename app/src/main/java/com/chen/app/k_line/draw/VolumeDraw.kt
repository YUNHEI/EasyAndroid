package com.chen.app.k_line.draw

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.chen.app.R
import com.chen.app.k_line.BaseKChartView
import com.chen.app.k_line.ViewUtil
import com.chen.app.k_line.base.IChartDraw
import com.chen.app.k_line.base.IValueFormatter
import com.chen.app.k_line.entity.IVolume
import com.chen.app.k_line.formatter.BigValueFormatter

//import com.chen.app.k_line.ViewUtil;
/**
 * Created by hjm on 2017/11/14 17:49.
 */
class VolumeDraw(view: BaseKChartView) : IChartDraw<IVolume> {
    private val mRedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mGreenPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val ma5Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val ma10Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var pillarWidth = 0
    override fun drawTranslated(
        lastPoint: IVolume?, curPoint: IVolume, lastX: Float, curX: Float,
        canvas: Canvas, view: BaseKChartView, position: Int
    ) {
        drawHistogram(canvas, curPoint, lastPoint, curX, view, position)
        view.drawChildLine(canvas, ma5Paint, lastX, lastPoint!!.mA5Volume, curX, curPoint.mA5Volume)
        view.drawChildLine(
            canvas,
            ma10Paint,
            lastX,
            lastPoint.mA10Volume,
            curX,
            curPoint.mA10Volume
        )
    }

    private fun drawHistogram(
        canvas: Canvas, curPoint: IVolume, lastPoint: IVolume?, curX: Float,
        view: BaseKChartView, position: Int
    ) {
        val r = pillarWidth / 2.toFloat()
        val top = view.getChildY(curPoint.volume)
        val bottom = view.childRect.bottom
        if (curPoint.closePrice >= curPoint.openPrice) { //涨
            canvas.drawRect(curX - r, top, curX + r, bottom.toFloat(), mRedPaint)
        } else {
            canvas.drawRect(curX - r, top, curX + r, bottom.toFloat(), mGreenPaint)
        }
    }

    override fun drawText(
        canvas: Canvas, view: BaseKChartView, position: Int, x: Float, y: Float
    ) {
        var x = x
        val point = view.getItem(position) as IVolume
        var text = "VOL:" + valueFormatter.format(point.volume) + " "
        canvas.drawText(text, x, y, view.textPaint)
        x += view.textPaint.measureText(text)
        text = "MA5:" + valueFormatter.format(point.mA5Volume) + " "
        canvas.drawText(text, x, y, ma5Paint)
        x += ma5Paint.measureText(text)
        text = "MA10:" + valueFormatter.format(point.mA10Volume) + " "
        canvas.drawText(text, x, y, ma10Paint)
    }

    override fun getMaxValue(point: IVolume): Float {
        return Math.max(point.volume, Math.max(point.mA5Volume, point.mA10Volume))
    }

    override fun getMinValue(point: IVolume): Float {
        return Math.min(point.volume, Math.min(point.mA5Volume, point.mA10Volume))
    }

    override val valueFormatter: IValueFormatter
        get() = BigValueFormatter()

    /**
     * 设置 MA5 线的颜色
     *
     * @param color
     */
    fun setMa5Color(color: Int) {
        ma5Paint.color = color
    }

    /**
     * 设置 MA10 线的颜色
     *
     * @param color
     */
    fun setMa10Color(color: Int) {
        ma10Paint.color = color
    }

    fun setLineWidth(width: Float) {
        ma5Paint.strokeWidth = width
        ma10Paint.strokeWidth = width
    }

    /**
     * 设置文字大小
     *
     * @param textSize
     */
    fun setTextSize(textSize: Float) {
        ma5Paint.textSize = textSize
        ma10Paint.textSize = textSize
    }

    init {
        val context = view.context
        mRedPaint.color = ContextCompat.getColor(context, R.color.chart_red)
        mGreenPaint.color = ContextCompat.getColor(context, R.color.chart_green)
        pillarWidth = ViewUtil.Dp2Px(context, 4f)
    }
}