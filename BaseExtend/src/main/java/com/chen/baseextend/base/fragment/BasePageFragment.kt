package com.chen.baseextend.base.fragment

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.chen.baseextend.R
import com.chen.baseextend.view.TabCircleView
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.basem.BaseFragment
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.dp2px
import com.chen.basemodule.widget.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.fragment_page.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import kotlin.math.abs

/**
 * @author chen
 * @date 2019/3/27
 */
abstract class BasePageFragment<T : RootBean> : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_page

    private val pagerAdapter: BasePageAdapter<T> by lazy { BasePageAdapter(this) }

    private val navigator: CommonNavigator by lazy { CommonNavigator(context) }

    open val navAdapter: BaseItemNavAdapter<T> by lazy { BaseItemNavAdapter(this) }

    var items: MutableList<T> = mutableListOf()
        set(value) {
            items.clear()
            items.addAll(value)
            pagerAdapter.notifyDataSetChanged()
            navAdapter.notifyDataSetChanged()
        }

    lateinit var mIndicator: MagicIndicator

    /**0在extend left 1在center  left 2、extend center 3、center center*/
    abstract val indicateType: Int

    abstract fun getTitle(data: T): String

    abstract fun getFragmentPage(data: T, position: Int): BaseFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        toolbar.run {
            /**0在extend left 1在center  left 2、extend center  3 center center*/
            when (indicateType) {
                0 -> {
                    mIndicator = extend(MagicIndicator(context), parentSize = -1 to 40)
                }
                1 -> {
                    mIndicator = center(MagicIndicator(context), true)
                }
                2 -> {
                    mIndicator = extend(MagicIndicator(context), size = -2 to -1, parentSize = -1 to 40)
                    (mIndicator.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.CENTER
                }
                3 -> {
                    mIndicator = center(MagicIndicator(context))
                }
                else -> {

                }
            }
        }

        _view_pager.run {
            adapter = pagerAdapter
            offscreenPageLimit = 2

            _view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    mIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    mIndicator.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    mIndicator.onPageScrollStateChanged(state)
                }
            })
        }

        navigator.run {
            isSkimOver = true
            leftPadding = DensityUtil.dp2px(5f)
            rightPadding = DensityUtil.dp2px(5f)
            adapter = navAdapter

            navAdapter.onPageSelected = {
                _view_pager.setCurrentItem(it, abs(_view_pager.currentItem - it) < 2)
            }
            mIndicator.navigator = this
        }

        super.onViewCreated(view, savedInstanceState)
    }

    internal class BasePageAdapter<T : RootBean>(val fragment: BasePageFragment<T>) : FragmentStatePagerAdapter(fragment.childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = fragment.getFragmentPage(fragment.items[position], position)

        override fun getCount(): Int = fragment.items.size
    }

    open class BaseItemNavAdapter<T : RootBean>(val fragment: BasePageFragment<T>) : CommonNavigatorAdapter() {

        var onPageSelected: ((i: Int) -> Unit)? = null

        override fun getTitleView(context: Context, i: Int): IPagerTitleView {

            return CommonPagerTitleView(context).apply {
                setContentView(R.layout.view_tab_circle)
                findViewById<TabCircleView>(R.id.title).run {
                    onPagerTitleChangeListener = object : CommonPagerTitleView.OnPagerTitleChangeListener {

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
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
                    setPadding(dp2px(15), 0, dp2px(15), 0)
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

        override fun getIndicator(context: Context): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_EXACTLY
                lineHeight = dp2px(3f)
                lineWidth = dp2px(21f)
                roundRadius = dp2px(2f)
                startInterpolator = AccelerateInterpolator()
                endInterpolator = DecelerateInterpolator(2.0f)
                setColors(color(R.color.main_theme))
            }
        }
    }
}