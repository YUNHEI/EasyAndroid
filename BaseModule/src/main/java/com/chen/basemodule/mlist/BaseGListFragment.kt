package com.chen.basemodule.mlist

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.basem.BaseBean
import com.chen.basemodule.network.base.BaseResponse
import kotlinx.android.synthetic.main.base_mlist_fragment.*

/**
 *
 * 简单的分组布局列表
 *  Created by chen on 2019/6/6
 **/
abstract class BaseGListFragment<P : RootBean, C : RootBean> : BaseMListFragment<BaseGListFragment.DataWrapBean>() {

    override fun customerDelegateWithParams(): MutableList<Class<out BaseItemViewDelegate<DataWrapBean>>>? = null

    var expandable = false

    var defaultHide = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableLoadPageData()
        _refresh.isEnableRefresh = false

        listenItemClick { _, _, data, id, p, _ ->
            if (expandable && data is GroupWrapBean<*, *>) {
                expand(data as GroupWrapBean<P, C>, !data.isExpand)
            }
        }
    }

    override var columns = 1
        set(value) {
            field = value
            delegateBundle.putInt("columns", value)
            _recycler.run {
                removeItemDecoration(itemDecoration)
                if (value <= 1) {
                    layoutManager = LinearLayoutManager(context)
                } else {
                    layoutManager = GridLayoutManager(context, value).apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return if (mAdapter.getDataByPosition(position) !is GroupWrapBean<*, *>) 1 else value
                            }
                        }
                    }
                    addItemDecoration(itemDecoration)
                }
            }
        }

    override val itemDecoration: RecyclerView.ItemDecoration by lazy {
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                if (mAdapter.getDataByPosition(position) !is GroupWrapBean<*, *>) {
                    val realP = position - mAdapter.headerViewDelegates.size
                    val row = realP.rem(columns)
                    outRect.run {
                        left = mPadding - row * mPadding / columns
                        right = row.inc() * mPadding / columns
                        bottom = mPadding
                        if (realP < columns) {
                            top = mPadding
                        }
                    }
                }
            }
        }
    }

    protected fun expand(groupWrapBean: GroupWrapBean<P, C>, expand: Boolean) {
        groupWrapBean.isExpand = expand

        groupWrapBean.items.forEach { it.isHideBaseListItem = !expand }

        mAdapter.run {
            val startIndex = data.indexOf(groupWrapBean).inc()
            if (expand) {
                notifyItemRangeInserted(startIndex, groupWrapBean.items.size)
            } else {
                notifyItemRangeRemoved(startIndex, groupWrapBean.items.size)
            }
        }
    }

    override fun initDelegate(bundle: Bundle) {
        super.initDelegate(bundle)

        mAdapter.addItemViewDelegate(object : BaseItemViewDelegate<DataWrapBean>(context!!) {

            override val layoutId = groupLayoutId

            override fun bindData(viewHolder: BaseItemViewHolder, data: DataWrapBean?, position: Int, realP: Int) {
                bindGroupData(viewHolder, (data as GroupWrapBean<P, C>).groupData, position, realP)
            }

            override fun isThisDelegate(data: DataWrapBean, position: Int, realP: Int) = data is GroupWrapBean<*, *>

        }, bundle)

        mAdapter.addItemViewDelegate(object : BaseItemViewDelegate<DataWrapBean>(context!!) {

            override val layoutId = itemLayoutId

            override fun bindData(viewHolder: BaseItemViewHolder, data: DataWrapBean?, position: Int, realP: Int) {
                bindItemData(viewHolder, (data as ItemWrapBean<C>).itemData, position, realP)
            }

            override fun isThisDelegate(data: DataWrapBean, position: Int, realP: Int) = data is ItemWrapBean<*>

        }, bundle)
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

    abstract fun loadData(): LiveData<BaseResponse<MutableList<P>>>?

    abstract fun getItemData(groupData: P?): MutableList<C>

    override fun loadData(refresh: Boolean, lastItem: DataWrapBean?): LiveData<BaseResponse<MutableList<DataWrapBean>>>? {
        return MutableLiveData<BaseResponse<MutableList<DataWrapBean>>>().apply {
            loadData()?.observe(this@BaseGListFragment, Observer {
                if (it.suc()) {
                    val res = BaseResponse(it.data?.flatMap { P ->
                        val child = getItemData(P).map {
                            ItemWrapBean(itemData = it).apply {
                                isHideBaseListItem = defaultHide && expandable
                            }
                        }.toMutableList()
                        mutableListOf<DataWrapBean>(GroupWrapBean(P, child, isExpand = !(defaultHide && expandable))).apply {
                            addAll(child)
                        }
                    }?.toMutableList(), it.status, it.message)
                    postValue(res)
                } else {
                    postValue(BaseResponse(status = it.status, message = it.message))
                }
            })
        }
    }

    abstract val groupLayoutId: Int

    abstract fun bindGroupData(viewHolder: BaseItemViewHolder, groupData: P?, position: Int, realP: Int)

    abstract val itemLayoutId: Int

    abstract fun bindItemData(viewHolder: BaseItemViewHolder, data: C?, position: Int, realP: Int)

    /**######################抽象方法区 复写父类中的抽象方法，保证idea自动补全的顺序 ######################*/

    open class DataWrapBean : BaseBean()

    data class GroupWrapBean<P : RootBean, C : RootBean>(val groupData: P? = null, var items: MutableList<ItemWrapBean<C>>, var isExpand: Boolean = true) : ItemWrapBean<C>()

    open class ItemWrapBean<C : RootBean>(val itemData: C? = null) : DataWrapBean()
}