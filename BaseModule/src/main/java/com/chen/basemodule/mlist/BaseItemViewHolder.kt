package com.chen.basemodule.mlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.chen.basemodule.allroot.RootBean
import kotlinx.android.extensions.LayoutContainer

/**
 *  Created by chen on 2019/6/5
 **/
class BaseItemViewHolder(
    val binding: ViewBinding,
    val mAdapter: BaseMultiAdapter<*>,
    var onAttachListener : ((v:View, bean:RootBean?) -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

//    override val containerView = binding.root

    fun notifySelf() {
        mAdapter.notifyItemChanged(adapterPosition)
    }

    fun notifyDataSet() {
        mAdapter.notifyDataSetChanged()
    }
}