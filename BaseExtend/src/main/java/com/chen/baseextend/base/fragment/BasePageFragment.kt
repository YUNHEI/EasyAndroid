package com.chen.baseextend.base.fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.chen.baseextend.R
import com.chen.baseextend.databinding.FragmentPageBinding
import com.chen.baseextend.view.TabCircleView
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.basem.BaseFragment
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.dp2px
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * @author chen  @[Link viewpage 嵌套 布局id不可冲突]
 * @date 2019/3/27
 */
//@Deprecated("viewpage is deprecated", ReplaceWith("BasePage2Fragment"))
abstract class BasePageFragment<T : RootBean> : BaseSimpleFragment() {

    companion object {
        //不初始化indicate 需要用户手动指定
        const val INDICATE_TYPE_NONE = -1

        //始化indicate ext 左侧
        const val INDICATE_TYPE_EXT_LEFT = 0

        //始化indicate center 左侧
        const val INDICATE_TYPE_CENTER_LEFT = 1

        //始化indicate ext 中部
        const val INDICATE_TYPE_EXT_CENTER = 2

        //始化indicate center 中部
        const val INDICATE_TYPE_CENTER_CENTER = 3

        const val INDICATE_TYPE_NONE_ADJUST = 4

        const val INDICATE_TYPE_EXT_ADJUST = 5

        const val INDICATE_TYPE_CENTER_ADJUST = 6
    }

    //    override val contentLayoutId = R.layout.fragment_page
    override val binding by doBinding(FragmentPageBinding::inflate)

    private val pagerAdapter: BasePageAdapter<T> by lazy { BasePageAdapter(this) }

    val navigator: CommonNavigator by lazy { CommonNavigator(context) }

    open val navAdapter: BaseItemNavAdapter<T> by lazy { BaseItemNavAdapter(this, showIndicator) }

    //title 字体大小
    open val titleTextSize = 17f

    open val titlePadding = Rect(dp2px(15), 0, dp2px(15), 0)

    open val showIndicator = true

    var items: MutableList<T> = mutableListOf()
        set(value) {
            items.clear()
            items.addAll(value)
            pagerAdapter.notifyDataSetChanged()
            navAdapter.notifyDataSetChanged()
        }

    open lateinit var mIndicator: MagicIndicator

    open val mViewPage by lazy { binding.ViewPager }

    @IntDef(
        INDICATE_TYPE_NONE,
        INDICATE_TYPE_EXT_LEFT,
        INDICATE_TYPE_CENTER_LEFT,
        INDICATE_TYPE_EXT_CENTER,
        INDICATE_TYPE_CENTER_CENTER,
        INDICATE_TYPE_NONE_ADJUST,
        INDICATE_TYPE_EXT_ADJUST,
        INDICATE_TYPE_CENTER_ADJUST
    )
    annotation class IndicateType

    /**0在extend left 1在center  left 2、extend center 3、center center*/
    @IndicateType
    abstract val indicateType: Int

    abstract fun getTitle(data: T): String

    abstract fun getFragmentPage(data: T, position: Int): BaseFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (indicateType > INDICATE_TYPE_NONE) {
            toolbar.run {
                /**0在extend left 1在center  left 2、extend center  3 center center*/
                when (indicateType) {
                    INDICATE_TYPE_EXT_LEFT -> {
                        mIndicator = extend(MagicIndicator(context), parentSize = -1 to 40)
                    }
                    INDICATE_TYPE_CENTER_LEFT -> {
                        mIndicator = center(MagicIndicator(context), true)
                    }
                    INDICATE_TYPE_EXT_CENTER, INDICATE_TYPE_EXT_ADJUST -> {
                        mIndicator =
                            extend(MagicIndicator(context), size = -2 to -1, parentSize = -1 to 40)
                        (mIndicator.layoutParams as FrameLayout.LayoutParams).gravity =
                            Gravity.CENTER
                    }
                    INDICATE_TYPE_CENTER_CENTER, INDICATE_TYPE_CENTER_ADJUST -> {
                        mIndicator = center(MagicIndicator(context))
                    }
                    else -> {
                    }
                }
            }
        }

        mViewPage.run {
            adapter = pagerAdapter
            offscreenPageLimit = 1

            mViewPage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    mIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    mIndicator.onPageSelected(position)
                    offscreenPageLimit = max(offscreenPageLimit, min(4, position.inc()))
                }

                override fun onPageScrollStateChanged(state: Int) {
                    mIndicator.onPageScrollStateChanged(state)
                }
            })
        }

        navigator.run {
            isSkimOver = true
            leftPadding = dp2px(5)
            rightPadding = dp2px(5)
            adapter = navAdapter
            isAdjustMode = indicateType >= INDICATE_TYPE_NONE_ADJUST

            navAdapter.onPageSelected = {
                mViewPage.setCurrentItem(it, abs(mViewPage.currentItem - it) < 2)
            }
            mIndicator.navigator = this
        }
        onIndicatorReady()
    }

    open fun onIndicatorReady() {

    }

    internal class BasePageAdapter<T : RootBean>(val fragment: BasePageFragment<T>) :
        FragmentStatePagerAdapter(
            fragment.childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {

        override fun getItem(position: Int): Fragment =
            fragment.getFragmentPage(fragment.items[position], position)

        override fun getCount(): Int = fragment.items.size
    }

    open class BaseItemNavAdapter<T : RootBean>(
        val fragment: BasePageFragment<T>,
        val showIndicator: Boolean = true
    ) :
        CommonNavigatorAdapter() {

        var onPageSelected: ((i: Int) -> Unit)? = null

        override fun getTitleView(context: Context, i: Int): IPagerTitleView {

            return CommonPagerTitleView(context).apply {
                setContentView(R.layout.view_tab_circle)
                findViewById<TabCircleView>(R.id.title).run {
                    onPagerTitleChangeListener =
                        object : CommonPagerTitleView.OnPagerTitleChangeListener {

                            override fun onDeselected(p0: Int, p1: Int) {
                                this@run.onDeselected(p0, p1)
                            }

                            override fun onSelected(p0: Int, p1: Int) {
                                this@run.onSelected(p0, p1)
                            }

                            override fun onLeave(p0: Int, p1: Int, p2: Float, p3: Boolean) {
                                this@run.onLeave(p0, p1, p2, p3)
                            }

                            override fun onEnter(p0: Int, p1: Int, p2: Float, p3: Boolean) {
                                this@run.onLeave(p0, p1, p2, p3)
                            }

                        }
                    setSelectBold(false)
                    fragment.run {
                        setTextSize(TypedValue.COMPLEX_UNIT_DIP, titleTextSize)
                        setPadding(
                            titlePadding.left,
                            titlePadding.top,
                            titlePadding.right,
                            titlePadding.bottom
                        )
                    }
                    normalColor = context.color(R.color.gray_33)
                    selectedColor = context.color(R.color.main_theme)
                    text = fragment.getTitle(fragment.items[i])
                    onPageSelected?.run {
                        setOnClickListener { invoke(i) }
                    }
                }
            }
        }

        override fun getCount(): Int = fragment.items.size

        override fun getIndicator(context: Context): IPagerIndicator? {
            return if (showIndicator) LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_EXACTLY
                lineHeight = dp2px(3f)
                lineWidth = dp2px(21f)
                roundRadius = dp2px(2f)
                startInterpolator = AccelerateInterpolator()
                endInterpolator = DecelerateInterpolator(2.0f)
                setColors(color(R.color.main_theme))
            } else null
        }
    }
}