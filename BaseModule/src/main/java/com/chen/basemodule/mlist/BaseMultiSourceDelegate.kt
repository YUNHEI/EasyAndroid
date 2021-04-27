package com.chen.basemodule.mlist

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.chen.basemodule.basem.BaseBean
import com.chen.basemodule.mlist.bean.DataWrapBean
import com.chen.basemodule.mlist.bean.GroupWrapBean
import com.chen.basemodule.mlist.bean.ItemWrapBean
import java.lang.Exception


abstract class BaseMultiSourceDelegate<OT : BaseBean>(context: Context) :
    BaseItemViewDelegate<DataWrapBean>(context) {

    /*返回item的布局 如 R.layout.item_info*/
//    abstract override val layoutId: Int

    abstract override val binding: ViewBinding

    @Suppress("UNCHECKED_CAST")
    override fun isThisDelegate(
        data: DataWrapBean,
        position: Int,
        realP: Int
    ): Boolean {
        return if (data !is GroupWrapBean<*, *> && data is ItemWrapBean<*>) {
            return try {
                (data.itemData as? OT)?.run { isThisDelegate(this, position, realP) } ?: false
            } catch (e: Exception) {
                false
            }
        } else false
    }


    @Suppress("UNCHECKED_CAST")
    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: DataWrapBean?,
        position: Int,
        realP: Int
    ) {
        bindData(
            viewHolder,
            (data as ItemWrapBean<*>).itemData as OT,
            position,
            realP
        )
    }

    abstract fun bindData(viewHolder: BaseItemViewHolder, data: OT?, position: Int, realP: Int)

    abstract fun isThisDelegate(data: OT, position: Int, realP: Int): Boolean
}