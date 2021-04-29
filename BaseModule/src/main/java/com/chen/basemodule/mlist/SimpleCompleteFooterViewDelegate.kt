package com.chen.basemodule.mlist

import android.content.Context

import com.chen.basemodule.R
import com.chen.basemodule.allroot.RootBean

/**
 * 加载完成
 * Created by chen on 2018/11/13
 */
class SimpleCompleteFooterViewDelegate(context: Context) : BaseFooterViewDelegate(context) {

    override val layoutId = R.layout.item_complete_footer

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: RootBean?,
        position: Int,
        realP: Int
    ) {
    }

    override fun isThisDelegate(data: RootBean, position: Int, realP: Int) = true
}
