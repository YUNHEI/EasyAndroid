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

    override val layoutId = -1

    override fun onCreateView(context: Context): View {
        return TextView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(dp2px(15))
            textSize = 18f
            gravity = Gravity.CENTER
            text = "还未添加相应样式"
        }
    }

    override fun bindData(viewHolder: BaseItemViewHolder, data: T?, position: Int, realP: Int) {
    }

    /*未添加布局自动显示，此开关无效*/
    override fun isThisDelegate(data: T, position: Int, realP: Int) = true
}
