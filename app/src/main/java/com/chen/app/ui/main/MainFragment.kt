package com.chen.app.ui.main

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.chen.app.R
import com.chen.app.ui.coroutines.CoroutinesFragment
import com.chen.app.ui.list.ListSampleFragment
import com.chen.app.ui.simple.SamplePageFragment
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.extend.stickSide
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.listenClick
import com.chen.basemodule.extend.toastSuc
import com.chen.basemodule.util.ThemeColorUtil
import com.chen.basemodule.util.WindowsUtil
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_main

    private val tabs by lazy {
        mutableListOf(
                Tab(SamplePageFragment::class.java, R.drawable.ic_bottom_project, "简单"),
                Tab(ListSampleFragment::class.java, R.drawable.ic_bottom_home, "列表"),
                Tab(CoroutinesFragment::class.java, R.drawable.ic_bottom_message, "协程")
//                Tab(MineFragment::class.java, R.drawable.ic_bottom_mine, "我的")
        )
    }


    override fun initAndObserve() {

        _tab_host.run {
            setup(context, childFragmentManager, R.id.level_1_container)
            tabWidget.dividerDrawable = null
            clearAllTabs()

            tabs.forEach {

                val v = layoutInflater.inflate(R.layout.layout_tab_spec, null)

                val text = v.findViewById<TextView>(R.id._tab_text)
                val image = v.findViewById<ImageView>(R.id._tab_image)

                text.text = it.title
                text.setTextColor(ThemeColorUtil.createColorStateList(color(R.color.text_content), color(R.color.main_theme)))
                image.setImageResource(it.icon)

                addTab(newTabSpec(it.title).setIndicator(v), it.fragment, null)
            }

            tabWidget.children.forEachIndexed { index, view ->
                view.setOnClickListener {
                    if (currentTab == 0 && currentTab == index) postRefresh(ListSampleFragment::class)
//                    if (currentTab == 1 && currentTab == index) postRefresh(ProjectFragment::class)
                    _tab_host.currentTab = index

                    WindowsUtil.setDarkTheme(activity!!, index == 2)
                }
            }
        }

//        refreshUserInfo()
//
//        observeRefresh { refreshUserInfo() }
//
//        observeRefresh("project_change") { refreshWaitProcessCount() }

        _wait_process.stickSide()

        listenClick(_wait_process) {
            when (it) {
                _wait_process -> {
                    "点击悬浮窗".toastSuc()
                }
                else -> {
                }
            }

        }

    }

    internal class Tab(var fragment: Class<out Fragment>, var icon: Int = 0, var title: String)
}