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
abstract class GroupSSListFragment : GroupSListFragment<GroupSSListFragment.GroupBean, GroupSSListFragment.ItemBean>() {

    abstract val wrapData: (() -> MutableList<GroupBean>)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenItemClick { _, itemView, data, _, _, _ ->
            if (data is GroupWrapBean<*, *>) {
                if (!expandable) (data as GroupWrapBean<GroupBean, ItemBean>).groupData?.run {
                    onClick?.invoke(itemView as SimpleItemView)
                }
            } else if (data is ItemWrapBean<*>) {
                (data as ItemWrapBean<ItemBean>).itemData?.run {
                    onClick?.invoke(itemView as SimpleItemView)
                }
            }
        }
    }

    override fun loadData(): LiveData<BaseResponse<MutableList<GroupBean>>>? {
        return MutableLiveData<BaseResponse<MutableList<GroupBean>>>().apply {
            postValue(BaseResponse(wrapData(), 200, "成功"))
        }
    }

    override fun getItemData(groupData: GroupBean?): MutableList<ItemBean> {
        return groupData?.items ?: mutableListOf()
    }

    override val titleStyle: TitleStyle = TitleStyle()

    override fun getGroupTitle(groupData: GroupBean?): String? = groupData?.title

    override fun bindGroupData(viewHolder: BaseItemViewHolder, groupData: GroupBean?, position: Int, realP: Int) {
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

    override fun bindItemData(viewHolder: BaseItemViewHolder, data: ItemBean?, position: Int, realP: Int) {
        (viewHolder.itemView as SimpleItemView).let {
            data?.run {
                it.title = title
                it.layoutParams.height = if (height >= 0) dp2px(height) else height

                it.setBackgroundColor(color(R.color.red))
                titleStyle?.run {
                    it.setContentColor(textColor)
                    it.setPadding(dp2px(padding.left), dp2px(padding.top), dp2px(padding.right), dp2px(padding.bottom))
                    it.background = context?.drawable(backgroundResource)
                }
            }
        }
    }

    class GroupBean(title: String, val items: MutableList<ItemBean> = mutableListOf(), titleStyle: TitleStyle? = null, onClick: ((itemView: View) -> Unit)? = null) : ItemBean(title, titleStyle = titleStyle, onClick = onClick)

    open class ItemBean(val title: String, val height: Int = 50, val titleStyle: TitleStyle? = null, val onClick: (SimpleItemView.() -> Unit)? = null) : BaseBean()

    inline fun DATA(itemAction: MutableList<GroupBean>.() -> Unit): MutableList<GroupBean> {
        return mutableListOf<GroupBean>().apply { itemAction(this) }
    }

    inline fun MutableList<GroupBean>.Group(title: String, titleStyle: TitleStyle? = null, noinline onClick: ((itemView: View) -> Unit)? = null, itemAction: GroupBean.() -> Unit) {
        add(GroupBean(title, titleStyle = titleStyle, onClick = onClick).apply { itemAction(this) })
    }

    inline fun GroupBean.Item(title: String, height: Int = 50, titleStyle: TitleStyle? = null, noinline onClick: (SimpleItemView.() -> Unit)? = null) {
        items.add(ItemBean(title, height, titleStyle, onClick))
    }
}