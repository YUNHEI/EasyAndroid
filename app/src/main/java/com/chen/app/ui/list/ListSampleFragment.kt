package com.chen.app.ui.list

import android.graphics.Rect
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
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

    override val titleStyle = TitleStyle(40, 14f, R.color.red, backgroundResource = R.color.gray_f5, padding = Rect(10, 10, 20, 10))

    override val wrapData = {
        mutableListOf(
                Group(
                        "简单列表",
                        Item("简单单一样式列表")
                        { v, t ->
//                    "点击 单一样式列表: $t".toastSuc()
                            startPage(SingleListSampleFragment::class)
                        },

                        Item("item1-2")
                        { v, t ->
                            "item: $t".toastSuc()
                        },

                        Item("item1-3")
                        { v, t ->
                            "item: $t".toastSuc()
                        },
                        titleStyle = TitleStyle(40, 14f, R.color.white, backgroundResource = R.color.main_theme, padding = Rect(10, 10, 20, 10))
                )
                { v, t ->
//                    "标题内嵌点击: $t".toastSuc()
                },

                Group(
                        "标题2",
                        Item("item2-1"),
                        Item("item2-2"),
                        Item("item2-3")
                ),
                Group(
                        "标题3",
                        Item("item3-1"),
                        Item("item3-2"),
                        Item("item3-3")
                )
        )
    }


    //监听点击事件  需要在此方法内进行监听
    override fun initClickListener() {
        listenItemClick { _, _, data, id, p, _ ->
            if (data is GroupWrapBean<*, *>) {
                (data as GroupWrapBean<Group, Item>).run {
//                    "标题监听点击: ${groupData?.title}".toastSuc()
                }
            } else if (data is ItemWrapBean<*>) {
                (data as ItemWrapBean<Item>).run {
//                    "item: ${itemData?.title}".toastSuc()
                }
            }
        }
    }
}