package com.chen.basemodule.mlist

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.network.base.BaseResponse
import kotlin.reflect.KClass

/**
 *
 * 简单的单一布局列表
 *  Created by chen on 2019/6/6
 **/
abstract class BaseSingleListFragment<V : RootBean> : BaseMultiListFragment<V>() {

    override fun customerDelegateWithParams(): MutableList<KClass<out BaseItemViewDelegate<V>>>? = null

    override fun initDelegate(bundle: Bundle) {
        super.initDelegate(bundle)
        mAdapter.addItemViewDelegate(object : BaseItemViewDelegate<V>(requireContext()) {

            override val binding = itemBinding

            override fun bindData(viewHolder: BaseItemViewHolder, data: V?, position: Int, realP: Int) {
                bindItemData(viewHolder, data!!, position, realP)
            }

            override fun updateData(viewHolder: BaseItemViewHolder, data: V?, position: Int, realP: Int, payloads: MutableList<Any>) {
                updateItemData(viewHolder, data!!, position, realP, payloads)
            }

            override fun isThisDelegate(data: V, position: Int, realP: Int) = true

        }, bundle)
    }

    open fun updateItemData(viewHolder: BaseItemViewHolder, data: V, position: Int, realP: Int, payloads: MutableList<Any>) {

    }

    /**######################抽象方法区 复写父类中的抽象方法，保证idea自动补全的顺序 ######################*/
    /**
     * onViewCreated 之后调用
     */
    abstract override fun initAndObserve()

    abstract override fun initClickListener()

    /**
     * 返回liveData对象自动进入通用处理流程，返回null 进入自定义流程
     *
     * @param refresh
     * @param lastItem refresh 为true时  lastItem 为null
     * @return
     */
    abstract override fun loadData(refresh: Boolean, lastItem: V?): LiveData<BaseResponse<MutableList<V>>>?

    abstract val itemBinding: ViewBinding

    abstract fun bindItemData(viewHolder: BaseItemViewHolder, data: V, position: Int, realP: Int)
    /**######################抽象方法区 复写父类中的抽象方法，保证idea自动补全的顺序 ######################*/

}