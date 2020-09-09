package com.chen.baseextend.base.fragment

import android.graphics.Rect
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.R
import com.chen.baseextend.repos.MainViewModel
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.dp2px
import com.chen.basemodule.extend.drawable
import com.chen.basemodule.mlist.BaseGListFragment
import com.chen.basemodule.mlist.BaseItemViewHolder

/**
 *  Created by 86152 on 2020-01-04
 **/
abstract class GroupSListFragment<P : RootBean, C : RootBean> : BaseGListFragment<P, C>() {

    abstract val titleStyle: TitleStyle

    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java).apply { owner = activity!! } }

    override val groupLayoutId: Int get() = R.layout.item_group_title

    override fun bindGroupData(viewHolder: BaseItemViewHolder, groupData: P?, position: Int, realP: Int) {
        (viewHolder.itemView as TextView).run {
            titleStyle.run {
                layoutParams.height = if (height < 0) height else dp2px(height)
                setTextSize(textSize)
                setTextColor(context.color(textColor))
                setPadding(dp2px(padding.left), dp2px(padding.top), dp2px(padding.right), dp2px(padding.bottom))
                text = getGroupTitle(groupData)
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