package com.chen.basemodule.mlist

import android.content.Context

import com.chen.basemodule.R

import com.chen.basemodule.allroot.RootBean

/**
 * Created by chen on 2018/11/13
 */
class SimpleCompleteHeaderViewDelegate<T: RootBean>(context: Context) : BaseHeaderViewDelegate<T>(context) {

    override val layoutId = R.layout.item_complete_footer

    override fun bindData(viewHolder: BaseItemViewHolder, data: T?, position: Int, realP: Int) {
    }

}
