package com.chen.basemodule.mlist

import android.os.Bundle
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.mlist.bean.DataWrapBean
import com.chen.basemodule.mlist.bean.ItemWrapBean
import kotlin.reflect.KClass

/**
 *
 * 简单的分组布局列表
 *  Created by chen on 2019/6/6
 **/
abstract class BaseSingleGroupListFragment<P : RootBean, C : RootBean> :
    BaseMultiGroupListFragment<P, C>() {

    override fun customerDelegateWithParams(): MutableList<KClass<out BaseItemViewDelegate<DataWrapBean>>>? =
        null

    override fun initDelegate(bundle: Bundle) {
        super.initDelegate(bundle)


        mAdapter.addItemViewDelegate(object : BaseItemViewDelegate<DataWrapBean>(context!!) {

            override val layoutId = itemLayoutId

            override fun bindData(
                viewHolder: BaseItemViewHolder,
                data: DataWrapBean?,
                position: Int,
                realP: Int
            ) {
                bindItemData(viewHolder, (data as ItemWrapBean<C>).itemData, position, realP)
            }

            override fun isThisDelegate(data: DataWrapBean, position: Int, realP: Int) =
                data is ItemWrapBean<*>

        }, bundle)
    }

    /**######################抽象方法区 复写父类中的抽象方法，保证idea自动补全的顺序 ######################*/
    /**
     * onViewCreated 之后调用
     */


    abstract val itemLayoutId: Int

    abstract fun bindItemData(viewHolder: BaseItemViewHolder, data: C?, position: Int, realP: Int)

}