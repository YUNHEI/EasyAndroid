package com.chen.basemodule.mlist

import android.content.Context

import com.chen.basemodule.R
import com.chen.basemodule.allroot.RootBean


/**
 * Created by chen on 2018/11/13
 */
class SimpleEmptyViewDelegate<T: RootBean>(context: Context) : BaseItemViewDelegate<T>(context) {

    override val layoutId = R.layout.item_simple_empty

    override fun bindData(viewHolder: BaseItemViewHolder, data: T?, position: Int, realP: Int) {
    }

    /*未添加布局自动显示，此开关无效*/
    override fun isThisDelegate(data: T, position: Int, realP: Int) = true
}
