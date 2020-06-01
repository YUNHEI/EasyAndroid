package com.chen.baseextend.base.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chen.baseextend.R
import com.chen.baseextend.view.SimpleItemView
import com.chen.basemodule.basem.BaseBean
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.dp2px
import com.chen.basemodule.extend.drawable
import com.chen.basemodule.mlist.BaseItemViewHolder
import com.chen.basemodule.network.base.BaseResponse

/**
 *  Created by 86152 on 2020-01-04
 **/
abstract class GroupSSListFragment : GroupSListFragment<GroupSSListFragment.Group, GroupSSListFragment.Item>() {

    abstract val wrapData: (() -> MutableList<Group>)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenItemClick { _, itemView, data, _, _, _ ->
            if (data is GroupWrapBean<*, *>) {
                if (!expandable) (data as GroupWrapBean<Group, Item>).groupData?.run {
                    onClick?.invoke(itemView, title)
                }
            } else if (data is ItemWrapBean<*>) {
                (data as ItemWrapBean<Item>).itemData?.run {
                    onClick?.invoke(itemView, title)
                }
            }
        }
    }

    override fun loadData(): LiveData<BaseResponse<MutableList<Group>>>? {
        return MutableLiveData<BaseResponse<MutableList<Group>>>().apply {
            postValue(BaseResponse(wrapData(), 200, "成功"))
        }
    }

    override fun getItemData(groupData: Group?): MutableList<Item> {
        return groupData?.items?.toMutableList() ?: mutableListOf()
    }

    override val titleStyle: TitleStyle = TitleStyle()

    override fun getGroupTitle(groupData: Group?): String? = groupData?.title

    override fun bindGroupData(viewHolder: BaseItemViewHolder, groupData: Group?, position: Int, realP: Int) {
        (viewHolder.itemView as TextView).run {
            (groupData?.titleStyle ?: titleStyle).run {
                layoutParams.height = if (height < 0) height else dp2px(height)
                setTextSize(textSize)
                setTextColor(context.color(textColor))
                setPadding(dp2px(padding.left), dp2px(padding.top), dp2px(padding.right), dp2px(padding.bottom))
                text = getGroupTitle(groupData)
                background = context.drawable(backgroundResource)
            }
        }
    }

    override val itemLayoutId: Int get() = R.layout.item_simple_item_view

    override fun bindItemData(viewHolder: BaseItemViewHolder, data: Item?, position: Int, realP: Int) {
//        (viewHolder.itemView as SimpleItemView).run {
//            (data?.titleStyle ?: titleStyle).run {
//                layoutParams.height = if (height < 0) height else dp2px(height)
//                setArrowVisibility(textSize)
//                setContentColor(textColor)
//                setPadding(dp2px(padding.left), dp2px(padding.top), dp2px(padding.right), dp2px(padding.bottom))
//                setTitle(data?.title.orEmpty())
//                background = context.drawable(backgroundResource)
//            }
//        }
        (viewHolder.itemView as SimpleItemView).let {
            data?.run {
                it.setTitle(title)
                it.layoutParams.height = if (height >= 0) dp2px(height) else height
            }
        }
    }

    class Group(title: String, vararg val items: Item, titleStyle: TitleStyle? = null, onClick: ((itemView: View, title: String) -> Unit)? = null) : Item(title, titleStyle = titleStyle, onClick = onClick)

    open class Item(val title: String, val height: Int = 50, val titleStyle: TitleStyle? = null, val onClick: ((itemView: View, title: String) -> Unit)? = null) : BaseBean()

}