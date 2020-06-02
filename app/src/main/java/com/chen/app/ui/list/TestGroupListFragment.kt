package com.chen.app.ui.list

import androidx.lifecycle.LiveData
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.GroupSListFragment
import com.chen.baseextend.bean.project.ItemTypeBean
import com.chen.baseextend.bean.project.ItemTypeRequest
import com.chen.basemodule.extend.toastSuc
import com.chen.basemodule.mlist.BaseItemViewHolder
import com.chen.basemodule.network.base.BaseResponse
import kotlinx.android.synthetic.main.item_message.*

/**
 *  Created by 86152 on 2020-01-04
 **/
@Launch
class TestGroupListFragment : GroupSListFragment<ItemTypeBean, ItemTypeBean>() {

    override fun initAndObserve() {
        toolbar.run {
            center("分组测试")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        expandable = true
    }

    override fun initClickListener() {
        listenItemClick(R.id._nickname) { _, _, data, id, p, _ ->
            when (id) {
                R.id._nickname -> {
                    if (data is GroupWrapBean<*, *>) {
                        (data as GroupWrapBean<ItemTypeBean, ItemTypeBean>).run {
                            "标题: ${groupData?.itemName}".toastSuc()
                        }
                    } else if (data is ItemWrapBean<*>) {
                        (data as ItemWrapBean<ItemTypeBean>).run {
                            "item: ${itemData?.itemName}".toastSuc()
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun loadData(): LiveData<BaseResponse<MutableList<ItemTypeBean>>>? {
        return viewModel.run { requestData({ projectService.listMultiItems(ItemTypeRequest(7)) }) }
    }

    override fun getItemData(groupData: ItemTypeBean?): MutableList<ItemTypeBean> {
        return groupData?.children ?: mutableListOf()
    }

    override val titleStyle: TitleStyle = TitleStyle()

    override fun getGroupTitle(groupData: ItemTypeBean?): String? = groupData?.itemName

    override val itemLayoutId: Int get() = R.layout.item_message

    override fun bindItemData(viewHolder: BaseItemViewHolder, data: ItemTypeBean?, position: Int, realP: Int) {

        viewHolder.itemView.run {
            data?.run {
                _nickname.text = "item：${itemName}"
            }
        }
    }
}