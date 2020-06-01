package com.chen.baseextend.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.chen.basemodule.extend.color
import java.util.*
import kotlin.math.abs

/**
 * Created by chen on 2019/9/4
 */
class ADTextView(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {

    private val speed = 3 //文字滚动速度
    var mInterval: Int = 700 //文字停留在中间的时长切换的间隔
    private val mTexts: MutableList<ADEntity> = mutableListOf() //显示文字的数据源
    private var mY = 0 //文字的Y坐标
    private var mIndex = 0 //当前的数据下标

    var maxFontLength = 10

    // 绘制中间的外框
    private val indexBound = Rect()

    // 绘制前缀的外框
    private val prefixBound = Rect()

    //绘制内容的外框
    private val contentBound = Rect()

    private val mPaintBack: Paint by lazy {
        //绘制内容的画笔
        Paint().apply {
            isAntiAlias = true
            isDither = true
            textSize = paint.textSize
        }
    }

    private val mPaintPrefix: Paint by lazy {
        //绘制内容的画笔
        Paint().apply {
            isAntiAlias = true
            isDither = true
            textSize = paint.textSize
        }
    }

    private val mPaintFront: Paint by lazy {
        //绘制前缀的画笔
        Paint().apply {
            isAntiAlias = true
            isDither = true
            textSize = paint.textSize
        }
    }

    private var isMove = true //文字是否移动
    private var hasInit = false//是否初始化刚进入时候文字的纵坐标

    val currentId: String?
        get() = mTexts.getOrNull(mIndex)?.mUrl

    //设置数据源
    fun setData(mTexts: List<ADEntity>) {
        this.mTexts.clear()
        this.mTexts.addAll(mTexts)
        mIndex = 0
    }

    //设置中间的文字颜色
    fun setFrontColor(mFrontColor: Int) {
        mPaintFront.color = context.color(mFrontColor)
    }

    //设置前缀的文字颜色
    fun setPrefixColor(mPrefixColor: Int) {
        mPaintPrefix.color = context.color(mPrefixColor)
    }

    //设置正文内容的颜色
    fun setBackColor(mBackColor: Int) {
        mPaintBack.color = context.color(mBackColor)
    }

    override fun onDraw(canvas: Canvas) {
        if (mTexts.isNotEmpty()) {
            //获取当前的数据
            val model = mTexts[mIndex]

            val prefix = model.mPrefix?.run {
                if (length <= 5) {
                    this
                } else {
                    "${substring(0, 4)}..."
                }
            } ?: ""

            val font = model.mFront?.run {
                if (length <= maxFontLength) {
                    this
                } else {
                    "${substring(0, maxFontLength - 1)}..."
                }
            } ?: ""

            val back = model.mBack

            mPaintPrefix.getTextBounds(prefix, 0, prefix.length, prefixBound)

            mPaintFront.getTextBounds(font, 0, font.length, indexBound)

            mPaintBack.getTextBounds(back, 0, back!!.length, contentBound)

            //刚开始进入的时候文字应该是位于组件的底部的 ,但是这个值是需要获取组件的高度和当前显示文字的情况下来判断的,
            // 所以应该放在onDraw内来初始化这个值,所以需要前面的是否初始化的属性,判断当mY==0并且未初始化的时候给mY赋值.
            if (mY <= 0 && !hasInit) {
                mY = measuredHeight - indexBound.top
                hasInit = true
            }
            //移动到最上面
            if (mY <= 0) {
                mY = measuredHeight - indexBound.top//返回底部
                mIndex++//换下一组数据
            }
            canvas.drawText(prefix, 0, prefix.length, 10f, mY.toFloat(), mPaintPrefix)
            canvas.drawText(font, 0, font.length, prefixBound.right + paddingEnd + 20f, mY.toFloat(), mPaintFront)
            canvas.drawText(back, 0, back.length, (width - contentBound.right - paddingEnd).toFloat(), mY.toFloat(), mPaintBack)
            //移动到中间
            val centerY = measuredHeight / 2 - (indexBound.top + indexBound.bottom) / 2

            if (abs(mY - centerY) < speed) {
                mY = centerY
                invalidate()
                isMove = false//停止移动
                val timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        postInvalidate()//通知重绘
                        isMove = true//设置移动为true
                    }
                }, mInterval.toLong())//停顿多少毫秒之后再次移动
            }

            //移动的处理与数据源的处理
            mY -= speed

            //循环使用数据
            if (mIndex == mTexts.size) {
                mIndex = 0
            }

            //如果是处于移动状态时的,则延迟绘制
            //计算公式为一个比例,一个时间间隔移动组件高度,则多少毫秒来移动1像素
            if (isMove) {
                invalidate()
            }
        }
    }

    inner class ADEntity(var mPrefix: String?, /*前面的文字*/
                         var mFront: String?, /*中间的文字*/
                         var mBack: String?, //后面的文字
                         var mUrl: String?/*包含的链接*/)
}
