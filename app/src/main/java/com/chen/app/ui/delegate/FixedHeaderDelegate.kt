package com.hx.stock.ui.delegate

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.extend.load
import com.chen.basemodule.mlist.BaseHeaderViewDelegate
import com.chen.basemodule.mlist.BaseItemViewHolder

/**
 * 简单固定header
 */
class FixedHeaderDelegate(
    context: Context,
    override val layoutId: Int,
    val map: MutableMap<Int, String> = mutableMapOf()
) : BaseHeaderViewDelegate(context) {

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: RootBean?,
        position: Int,
        realP: Int
    ) {
        viewHolder.itemView.run {
            map.forEach { entity ->
                findViewById<View>(entity.key)?.run {
                    when (this) {
                        is TextView -> text = entity.value
                        is ImageView -> load(entity.value)
                        else -> {
                        }
                    }
                }
            }
        }
    }
}