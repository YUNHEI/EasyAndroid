package com.chen.basemodule.mlist

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding

import com.chen.basemodule.R
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.extend.dp2px
import com.chen.basemodule.extend.sp2px


/**
 * Created by chen on 2018/11/13
 */
class SimpleEmptyViewDelegate<T : RootBean>(context: Context) : BaseItemViewDelegate<T>(context) {

    override val layoutId = R.layout.item_simple_empty

    override fun bindData(viewHolder: BaseItemViewHolder, data: T?, position: Int, realP: Int) {
    }

    /*未添加布局自动显示，此开关无效*/
    override fun isThisDelegate(data: T, position: Int, realP: Int) = true
}
