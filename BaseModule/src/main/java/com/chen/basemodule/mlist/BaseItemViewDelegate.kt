package com.chen.basemodule.mlist


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chen.basemodule.allroot.RootBean


/**
 * Created by chen on 2018/11/12
 */
abstract class BaseItemViewDelegate<T : RootBean>(val context: Context) {

    var viewHolder: BaseItemViewHolder? = null

    val customerClickViewIds = mutableListOf(-1)//添加整个item点击事件

    var bundle: Bundle? = null

    lateinit var mAdapter: BaseMAdapter<T>

    /*返回item的布局 如 R.layout.item_info*/
    abstract val layoutId: Int

    val columns: Int
        get() = bundle?.getInt("columns", 1) ?: 1


    fun createItemViewHolder(parent: ViewGroup): BaseItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        viewHolder = BaseItemViewHolder(view, mAdapter)
        onAddCustomerClickListener(customerClickViewIds)
        return viewHolder!!
    }

    protected open fun onAddCustomerClickListener(customerClickViewIds: MutableList<Int>) {

    }

    open fun onItemClick(viewHolder: BaseItemViewHolder, itemView: View, data: T?, id: Int, position: Int, realP: Int) = false

    open fun onItemLongClick(viewHolder: BaseItemViewHolder, itemView: View, data: T?, id: Int, position: Int, realP: Int) = false

    open fun updateData(viewHolder: BaseItemViewHolder, data: T?, position: Int, realP: Int, payloads: MutableList<Any>) {

    }

    abstract fun bindData(viewHolder: BaseItemViewHolder, data: T?, position: Int, realP: Int)

    abstract fun isThisDelegate(data: T, position: Int, realP: Int): Boolean


}
