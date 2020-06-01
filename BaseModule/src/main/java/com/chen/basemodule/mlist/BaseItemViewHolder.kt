package com.chen.basemodule.mlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 *  Created by chen on 2019/6/5
 **/
class BaseItemViewHolder(v: View, val mAdapter: BaseMAdapter<*>) : RecyclerView.ViewHolder(v), LayoutContainer {

    override val containerView = v

    fun notifySelf() {
        mAdapter.notifyItemChanged(adapterPosition)
    }

    fun notifyDataSet() {
        mAdapter.notifyDataSetChanged()
    }
}