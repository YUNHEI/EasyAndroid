package com.chen.app.ui.main

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.chen.app.R
import com.chen.app.databinding.FragmentMainBinding
import com.chen.app.ui.coroutines.CoroutinesFragment
import com.chen.app.ui.list.ListSampleFragment
import com.chen.app.ui.simple.SamplePageFragment
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.extend.stickSide
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.listenClick
import com.chen.basemodule.extend.toastSuc
import com.chen.basemodule.util.ThemeColorUtil
import com.chen.basemodule.util.WindowsUtil


class MainFragment : BaseSimpleFragment() {

    override val binding by doBinding(FragmentMainBinding::inflate)

    private val tabs by lazy {
        mutableListOf(
            Tab(ListSampleFragment::class.java, R.drawable.ic_bottom_home, "列表"),
            Tab(CoroutinesFragment::class.java, R.drawable.ic_bottom_message, "协程"),
            Tab(SamplePageFragment::class.java, R.drawable.ic_bottom_project, "简单")
//                Tab(MineFragment::class.java, R.drawable.ic_bottom_mine, "我的")
        )
    }


    override fun initAndObserve() {

        binding.TabHost.run {
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

                    currentTab = index

                    WindowsUtil.setDarkTheme(requireActivity(), index == 2)
                }
            }
        }

//        refreshUserInfo()
//
//        observeRefresh { refreshUserInfo() }
//
//        observeRefresh("project_change") { refreshWaitProcessCount() }

        binding.WaitProcess.stickSide()

        listenClick(binding.WaitProcess) {
            when (it) {
                binding.WaitProcess -> {
                    "点击悬浮窗".toastSuc()
                }
                else -> {
                }
            }

        }

    }

    internal class Tab(var fragment: Class<out Fragment>, var icon: Int = 0, var title: String)
}