package com.chen.basemodule.mlist

import android.content.Context

import com.chen.basemodule.R
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.databinding.ItemLoadingMoreFooterBinding
import com.chen.basemodule.extend.createBinding
import com.chen.basemodule.extend.doBinding

/**
 * 加载中
 * Created by chen on 2018/11/13
 */
class SimpleLoadingFooterViewDelegate(context: Context) : BaseFooterViewDelegate(context) {

//    override val layoutId = R.layout.item_loading_more_footer

    override val binding get() =  createBinding(ItemLoadingMoreFooterBinding::inflate)

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: RootBean?,
        position: Int,
        realP: Int
    ) {
    }

    override fun isThisDelegate(data: RootBean, position: Int, realP: Int) = true
}
