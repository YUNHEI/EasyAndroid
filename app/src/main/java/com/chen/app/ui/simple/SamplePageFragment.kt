package com.chen.app.ui.simple

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.navigationtest.MainActivity
import com.chen.app.ui.simple.download.DownloadFragment
import com.chen.app.ui.simple.event.EventFragment
import com.chen.app.ui.simple.net.WeatherDetailFragment
import com.chen.app.ui.simple.net.WeatherDetailLoadingFragment
import com.chen.app.ui.simple.params.ParamsFragment
import com.chen.app.ui.simple.preference.PreferenceFragment
import com.chen.app.ui.simple.toolbar.*
import com.chen.app.ui.simple.toolbar.pager.ToolbarExtend3Fragment
import com.chen.app.ui.simple.toolbar.pager.ToolbarExtend4Fragment
import com.chen.app.ui.simple.toolbar.pager.ToolbarExtend5Fragment
import com.chen.app.ui.simple.toolbar.pager.ToolbarExtend6Fragment
import com.chen.baseextend.base.fragment.GroupSSListFragment
import com.chen.baseextend.extend.startPage
import com.chen.baseextend.route.Module1Route.MODULE1_FRAGMENT
import com.chen.basemodule.extend.toastDebug
import com.chen.basemodule.extend.toastSuc
import java.util.*

@Launch
class SamplePageFragment : GroupSSListFragment() {

    lateinit var _title: TextView

    override fun initAndObserve() {

        toolbar.run {
            _title = center("简单页面")
        }

        //是否可展开 默认false
        expandable = false
        //是否可展开 默认false
        defaultHide = true

        //item 列数
        columns = 1

        observeRefresh {
            "通知来自${it.fromClassName}".toastSuc()
            it.obj?.run {
                if (this is String) _title.text = this
            }
        }
    }

    override val titleStyle = TitleStyle(40, 14f, R.color.red, backgroundResource = R.color.gray_f5, padding = Rect(10, 10, 20, 10))

    override val wrapData by lazy {

        DATA {
            Group("简单页面", TitleStyle(40, 14f, R.color.white, backgroundResource = R.color.blue_lightest, padding = Rect(10, 10, 20, 10))) {
                Item("新建一个简单页面") {
//                    "点击 单一样式列表: $t".toastSuc()
                    startPage(SimpleFirstFragment::class)
                }
                Item("Hello World") {
                    startPage(HelloWorldFragment::class)
                }
            }
            Group("toolbar的使用") {
                Item("自定义toobar样式") {
                    startPage(SimpleToolbarFragment::class)
                }
                Item("没有toolbar") {
                    startPage(SimpleWithoutToolbarFragment::class)
                }
                Item("toolbar悬浮") {
                    startPage(ToolbarFloatFragment::class)
                }
                Item("toolbar扩展") {
                    startPage(ToolbarExtendFragment::class)
                }
                Item("toolbar扩展2") {
                    startPage(ToolbarExtend2Fragment::class)
                }
                Item("toolbar扩展3") {
                    startPage(ToolbarExtend3Fragment::class)
                }
                Item("toolbar扩展4") {
                    startPage(ToolbarExtend4Fragment::class)
                }
                Item("toolbar扩展5") {
                    startPage(ToolbarExtend5Fragment::class)
                }
                Item("toolbar扩展6") {
                    startPage(ToolbarExtend6Fragment::class)
                }
                Item("自定义toobar样式") {
                    startPage(SimpleToolbarFragment::class)
                }
                Item("自定义toobar样式") {
                    startPage(SimpleToolbarFragment::class)
                }
            }
            Group("retrofit2 协程 网络请求") {
                Item("简单的网络请求") {
                    startPage(WeatherDetailFragment::class)
                }
                Item("简单的网络请求 带加载状态") {
                    startPage(WeatherDetailLoadingFragment::class)
                }
            }
            Group("本地存储") {
                Item("PreferenceHolder") {
                    startPage(PreferenceFragment::class)
                }
            }
            Group("页面跳转---模块化") {
                Item("模块间页面跳转") {
                    startPage(route = MODULE1_FRAGMENT)
                }
                Item("页面参数传递,参数较少") {
                    startPage(ParamsFragment::class, "day" to "第一天", "week" to 3, "wea" to 30.5F)
                }
                Item("页面参数传递,参数较多") {
                    Bundle().run {
                        putString("day", "第一天")
                        putInt("week", 3)
                        putFloat("wea", 30.5F)
                        startPage(ParamsFragment::class, bundle = this, requestCode = 101)
                    }
                }
            }
            Group("event-bus 通知") {
                Item("消息回传") {
                    startPage(EventFragment::class)
                }
            }
            Group("Toast") {
                Item("Toast成功") {
                    "成功".toastSuc()
                }
                Item("Toast错误") {
                    "错误".toastSuc()
                }
                Item("Toast异常") {
                    "异常".toastSuc()
                }
                Item("Toast debug") {
                    "debug".toastDebug()
                }
            }
            Group("其他功能") {
                Item("Navigation") {
                    startActivity(Intent(activity, MainActivity::class.java))
                }
                Item("性能测试") {

                    Log.e("start1-------", Date().toString())
                    for (i in 0 until 400) {
                        startPage(SimpleFirstFragment::class)
//                        startPage(SimpleToolbarFragment::class)
//                        startActivity(Intent(activity, BaseStandardActivity::class.java))
                    }

                    Log.e("end1-------", Date().toString())
                }
                Item("Toast异常") {
                    "异常".toastSuc()
                }
                Item("Toast debug") {
                    "debug".toastDebug()
                }
            }
            Group("下载") {
                Item("下载pdf") {
                    startPage(DownloadFragment::class)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {

        }
    }


    //监听点击事件  需要在此方法内进行监听
    override fun initClickListener() {
        listenItemClick { _, _, data, id, p, _ ->
            if (data is GroupWrapBean<*, *>) {
                (data as GroupWrapBean<GroupBean, ItemBean>).run {
//                    "标题监听点击: ${groupData?.title}".toastSuc()
                }
            } else if (data is ItemWrapBean<*>) {
                (data as ItemWrapBean<ItemBean>).run {
//                    "item: ${itemData?.title}".toastSuc()
                }
            }
        }
    }
}