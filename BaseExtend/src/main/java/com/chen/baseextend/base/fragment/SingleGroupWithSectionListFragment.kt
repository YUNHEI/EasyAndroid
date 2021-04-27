package com.chen.baseextend.base.fragment

import android.graphics.Rect
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.R
import com.chen.baseextend.databinding.ItemGroupTitleBinding
import com.chen.baseextend.repos.viewmodel.MainViewModel
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.dp2px
import com.chen.basemodule.extend.drawable
import com.chen.basemodule.mlist.BaseItemViewHolder
import com.chen.basemodule.mlist.BaseSingleGroupListFragment
import com.chen.basemodule.mlist.bean.GroupWrapBean

/**
 *  Created by 86152 on 2020-01-04
 **/
abstract class SingleGroupWithSectionListFragment<P : RootBean, C : RootBean> : BaseSingleGroupListFragment<P, C>() {

    abstract val titleStyle: TitleStyle

    override val viewModel by lazy { ViewModelProvider(requireActivity()).get(MainViewModel::class.java) }

    override val groupBinding by doBinding(ItemGroupTitleBinding::inflate)

    override fun bindGroupData(viewHolder: BaseItemViewHolder, groupWrapData: GroupWrapBean<P, C>?, position: Int, realP: Int) {
        (viewHolder.itemView as TextView).run {
            titleStyle.run {
                layoutParams.height = if (height < 0) height else dp2px(height)
                setTextSize(textSize)
                setTextColor(context.color(textColor))
                setPadding(dp2px(padding.left), dp2px(padding.top), dp2px(padding.right), dp2px(padding.bottom))
                text = getGroupTitle(groupWrapData?.groupData)
                background = context.drawable(backgroundResource)
            }
        }
    }

    abstract fun getGroupTitle(groupData: P?): String?

    data class TitleStyle(
            val height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
            val textSize: Float = 12f,
            val textColor: Int = R.color.gray_66,
            val backgroundResource: Int = R.color.gray_ef,
            val padding: Rect = Rect(15, 6, 15, 4)
    )
}