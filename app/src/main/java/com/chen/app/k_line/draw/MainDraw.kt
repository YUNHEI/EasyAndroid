package com.chen.app.k_line.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.content.ContextCompat
import com.chen.app.R
import com.chen.app.k_line.BaseKChartView
import com.chen.app.k_line.ViewUtil
import com.chen.app.k_line.base.IChartDraw
import com.chen.app.k_line.base.IValueFormatter
import com.chen.app.k_line.entity.ICandle
import com.chen.app.k_line.entity.IKLine
import com.chen.app.k_line.formatter.ValueFormatter
import java.util.*

/**
 * 主图的实现类
 * Created by tifezh on 2016/6/14.
 */
class MainDraw(view: BaseKChartView) : IChartDraw<ICandle> {
    private var mCandleWidth = 0f
    private var mCandleLineWidth = 0f
    private val mRedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mGreenPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val ma5Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val ma10Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val ma20Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mSelectorTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mSelectorBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mContext: Context
    private var mCandleSolid = true
    override fun drawTranslated(
        lastPoint: ICandle?,
        curPoint: ICandle,
        lastX: Float,
        curX: Float,
        canvas: Canvas,
        view: BaseKChartView,
        position: Int
    ) {
        drawCandle(
            view,
            canvas,
            curX,
            curPoint.highPrice,
            curPoint.lowPrice,
            curPoint.openPrice,
            curPoint.closePrice
        )
        //画ma5
        if (lastPoint!!.mA5Price != 0f) {
            view.drawMainLine(canvas, ma5Paint, lastX, lastPoint.mA5Price, curX, curPoint.mA5Price)
        }
        //画ma10
        if (lastPoint.mA10Price != 0f) {
            view.drawMainLine(
                canvas,
                ma10Paint,
                lastX,
                lastPoint.mA10Price,
                curX,
                curPoint.mA10Price
            )
        }
        //画ma20
        if (lastPoint.mA20Price != 0f) {
            view.drawMainLine(
                canvas,
                ma20Paint,
                lastX,
                lastPoint.mA20Price,
                curX,
                curPoint.mA20Price
            )
        }
    }

    override fun drawText(canvas: Canvas, view: BaseKChartView, position: Int, x: Float, y: Float) {
        var x = x
        val point: ICandle = view.getItem(position) as IKLine
        var text = "MA5:" + view.formatValue(point.mA5Price) + " "
        canvas.drawText(text, x, y, ma5Paint)
        x += ma5Paint.measureText(text)
        text = "MA10:" + view.formatValue(point.mA10Price) + " "
        canvas.drawText(text, x, y, ma10Paint)
        x += ma10Paint.measureText(text)
        text = "MA20:" + view.formatValue(point.mA20Price) + " "
        canvas.drawText(text, x, y, ma20Paint)
        if (view.isLongPress) {
            drawSelector(view, canvas)
        }
    }

    override fun getMaxValue(point: ICandle): Float {
        return Math.max(point.highPrice, point.mA20Price)
    }

    override fun getMinValue(point: ICandle): Float {
        return Math.min(point.mA20Price, point.lowPrice)
    }

    override val valueFormatter: IValueFormatter
        get() = ValueFormatter()

    /**
     * 画Candle
     * @param canvas
     * @param x      x轴坐标
     * @param high   最高价
     * @param low    最低价
     * @param open   开盘价
     * @param close  收盘价
     */
    private fun drawCandle(
        view: BaseKChartView,
        canvas: Canvas,
        x: Float,
        high: Float,
        low: Float,
        open: Float,
        close: Float
    ) {
        var high = high
        var low = low
        var open = open
        var close = close
        high = view.getMainY(high)
        low = view.getMainY(low)
        open = view.getMainY(open)
        close = view.getMainY(close)
        val r = mCandleWidth / 2
        val lineR = mCandleLineWidth / 2
        if (open > close) {
            //实心
            if (mCandleSolid) {
                canvas.drawRect(x - r, close, x + r, open, mRedPaint)
                canvas.drawRect(x - lineR, high, x + lineR, low, mRedPaint)
            } else {
                mRedPaint.strokeWidth = mCandleLineWidth
                canvas.drawLine(x, high, x, close, mRedPaint)
                canvas.drawLine(x, open, x, low, mRedPaint)
                canvas.drawLine(x - r + lineR, open, x - r + lineR, close, mRedPaint)
                canvas.drawLine(x + r - lineR, open, x + r - lineR, close, mRedPaint)
                mRedPaint.strokeWidth = mCandleLineWidth * view.scaleX
                canvas.drawLine(x - r, open, x + r, open, mRedPaint)
                canvas.drawLine(x - r, close, x + r, close, mRedPaint)
            }
        } else if (open < close) {
            canvas.drawRect(x - r, open, x + r, close, mGreenPaint)
            canvas.drawRect(x - lineR, high, x + lineR, low, mGreenPaint)
        } else {
            canvas.drawRect(x - r, open, x + r, close + 1, mRedPaint)
            canvas.drawRect(x - lineR, high, x + lineR, low, mRedPaint)
        }
    }

    /**
     * draw选择器
     * @param view
     * @param canvas
     */
    private fun drawSelector(view: BaseKChartView, canvas: Canvas) {
        val metrics = mSelectorTextPaint.fontMetrics
        val textHeight = metrics.descent - metrics.ascent
        val index = view.selectedIndex
        val padding = ViewUtil.Dp2Px(mContext, 5f).toFloat()
        val margin = ViewUtil.Dp2Px(mContext, 5f).toFloat()
        var width = 0f
        val left: Float
        val top = margin + view.topPadding
        val height = padding * 8 + textHeight * 5
        val point = view.getItem(index) as ICandle
        val strings: MutableList<String> = ArrayList()
        strings.add(view.formatDateTime(view.adapter.getDate(index)))
        strings.add("高:" + point.highPrice)
        strings.add("低:" + point.lowPrice)
        strings.add("开:" + point.openPrice)
        strings.add("收:" + point.closePrice)
        for (s in strings) {
            width = Math.max(width, mSelectorTextPaint.measureText(s))
        }
        width += padding * 2
        val x = view.translateXtoX(view.getX(index))
        left = if (x > view.chartWidth / 2) {
            margin
        } else {
            view.chartWidth - width - margin
        }
        val r = RectF(left, top, left + width, top + height)
        canvas.drawRoundRect(r, padding, padding, mSelectorBackgroundPaint)
        var y = top + padding * 2 + (textHeight - metrics.bottom - metrics.top) / 2
        for (s in strings) {
            canvas.drawText(s, left + padding, y, mSelectorTextPaint)
            y += textHeight + padding
        }
    }

    /**
     * 设置蜡烛宽度
     * @param candleWidth
     */
    fun setCandleWidth(candleWidth: Float) {
        mCandleWidth = candleWidth
    }

    /**
     * 设置蜡烛线宽度
     * @param candleLineWidth
     */
    fun setCandleLineWidth(candleLineWidth: Float) {
        mCandleLineWidth = candleLineWidth
    }

    /**
     * 设置ma5颜色
     * @param color
     */
    fun setMa5Color(color: Int) {
        ma5Paint.color = color
    }

    /**
     * 设置ma10颜色
     * @param color
     */
    fun setMa10Color(color: Int) {
        ma10Paint.color = color
    }

    /**
     * 设置ma20颜色
     * @param color
     */
    fun setMa20Color(color: Int) {
        ma20Paint.color = color
    }

    /**
     * 设置选择器文字颜色
     * @param color
     */
    fun setSelectorTextColor(color: Int) {
        mSelectorTextPaint.color = color
    }

    /**
     * 设置选择器文字大小
     * @param textSize
     */
    fun setSelectorTextSize(textSize: Float) {
        mSelectorTextPaint.textSize = textSize
    }

    /**
     * 设置选择器背景
     * @param color
     */
    fun setSelectorBackgroundColor(color: Int) {
        mSelectorBackgroundPaint.color = color
    }

    /**
     * 设置曲线宽度
     */
    fun setLineWidth(width: Float) {
        ma20Paint.strokeWidth = width
        ma10Paint.strokeWidth = width
        ma5Paint.strokeWidth = width
    }

    /**
     * 设置文字大小
     */
    fun setTextSize(textSize: Float) {
        ma20Paint.textSize = textSize
        ma10Paint.textSize = textSize
        ma5Paint.textSize = textSize
    }

    /**
     * 蜡烛是否实心
     */
    fun setCandleSolid(candleSolid: Boolean) {
        mCandleSolid = candleSolid
    }

    init {
        val context = view.context
        mContext = context
        mRedPaint.color = ContextCompat.getColor(context, R.color.chart_red)
        mGreenPaint.color = ContextCompat.getColor(context, R.color.chart_green)
    }
}