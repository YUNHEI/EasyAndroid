package com.chen.app.ui.list

import android.graphics.Rect
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.ui.kline.KLineFragment
import com.chen.baseextend.base.fragment.GroupSSListFragment
import com.chen.baseextend.extend.startPage
import com.chen.basemodule.extend.toastSuc

@Launch
class ListSampleFragment : GroupSSListFragment() {

    override fun initAndObserve() {

        toolbar.run {
            center("列表")
        }

        //是否可展开 默认false
        expandable = false
        //是否可展开 默认false
        defaultHide = true

        //item 列数
        columns = 1

    }

    override val titleStyle = TitleStyle(
            40,
            14f,
            R.color.red,
            backgroundResource = R.color.gray_f5,
            padding = Rect(10, 10, 20, 10)
    )

    override val wrapData by lazy {
        DATA {
            Group(
                    "简单列表",
                    titleStyle = TitleStyle(
                            40,
                            14f,
                            R.color.white,
                            backgroundResource = R.color.main_theme,
                            padding = Rect(10, 10, 20, 10)
                    )
            ) {
                Item("简单单一样式列表") {
//                    "点击 单一样式列表: $t".toastSuc()
                    startPage(SingleListSampleFragment::class)
                }
                Item("banner列表") {
                    startPage(BannerListSampleFragment::class)
                }
                Item("slide 列表") {
                    startPage(SlideListSampleFragment::class)
                }
                Item("view page") {
                    startPage(ViewPageListSampleFragment::class)
                }
                Item("Echelon list") {
                    startPage(EchelonListSampleFragment::class)
                }
                Item("item1-5") {
                    "item: $title".toastSuc()
                }
                Item("item1-6") {
                    "item: $title".toastSuc()
                }
            }
            Group("k-line") {
                Item("k-线图") {
                    startPage(KLineFragment::class)
                }
                Item("item2-2") {

                }
                Item("item2-3") {

                }
            }
            Group("标题3") {
                Item("item3-1") {

                }
                Item("item3-2") {

                }
                Item("item3-3") {

                }
            }
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