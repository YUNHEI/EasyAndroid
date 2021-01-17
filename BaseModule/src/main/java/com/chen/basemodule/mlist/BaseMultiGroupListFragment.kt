package com.chen.basemodule.mlist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.mlist.bean.DataWrapBean
import com.chen.basemodule.mlist.bean.GroupWrapBean
import com.chen.basemodule.mlist.bean.ItemWrapBean
import com.chen.basemodule.mlist.itemdecoration.DefaultDecoration
import com.chen.basemodule.network.base.BaseResponse
import kotlinx.android.synthetic.main.base_mlist_fragment.*
import kotlin.math.max

/**
 *
 * 简单的分组布局列表
 *  Created by chen on 2019/6/6
 **/
abstract class BaseMultiGroupListFragment<P : RootBean, C : RootBean> :
    BaseMultiListFragment<DataWrapBean>() {

    override val mAdapter by lazy {
        BaseMultiGroupAdapter<P, C>(context!!).apply {
            pageSize = PAGE_SIZE
        }
    }


    //是否可展开 默认false
    var expandable = false
        set(value) {
            field = value
            mAdapter.expandAble = value
        }

    //可展开下是否默认收起  默认收起
    var defaultHide = true

    override var mPadding = 30
        set(value) {
            itemDecoration.mPadding = value
            field = value
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        disableLoadPageData()
        _refresh.isEnableRefresh = false

        listenItemClick { _, _, data, id, p, _ ->
            if (expandable && data is GroupWrapBean<*, *>) {
                expand(data as GroupWrapBean<P, C>, !data.isExpand)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    val itemDecoration by lazy { DefaultDecoration() }

    override var columns = 1
        set(value) {
            field = value
            delegateBundle.putInt("columns", value)
            mAdapter.columns = value
            _recycler.run {
                removeItemDecoration(itemDecoration)
                itemDecoration.columns = value
                if (value <= 1) {
                    layoutManager = LinearLayoutManager(context)
                } else {
                    layoutManager = GridLayoutManager(context, value).apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return if (mAdapter.isItem(position) && mAdapter.getDataByPosition(
                                        position
                                    ) !is GroupWrapBean<*, *>
                                ) 1 else value
                            }
                        }
                    }
                    addItemDecoration(itemDecoration)
                }
            }
        }

    protected fun expand(groupWrapBean: GroupWrapBean<P, C>, expand: Boolean) {
        groupWrapBean.isExpand = expand

        val p = mAdapter.originData.indexOf(groupWrapBean)

        for (i in p.inc() until mAdapter.originData.size) {
            if (mAdapter.originData[i] is GroupWrapBean<*, *>) {
                break
            } else {
                mAdapter.originData[i].isHideBaseListItem = !expand
            }
        }

        mAdapter.run {
            val sectionIndex =
                data.indexOf(groupWrapBean).inc() + max(headerViewDelegates.size - 1, 0)
            notifyItemChanged(sectionIndex)
            val startIndex = data.indexOf(groupWrapBean).inc() + headerViewDelegates.size
            _recycler.animation?.cancel()
            if (expand) {
                notifyItemRangeInserted(startIndex, getGroupSize(groupWrapBean))
            } else {
                notifyItemRangeRemoved(startIndex, getGroupSize(groupWrapBean))
            }
        }
    }


    override fun initDelegate(bundle: Bundle) {
        super.initDelegate(bundle)

        mAdapter.addItemViewDelegate(object : BaseItemViewDelegate<DataWrapBean>(context!!) {

            override val layoutId = groupLayoutId

            override fun bindData(
                viewHolder: BaseItemViewHolder,
                data: DataWrapBean?,
                position: Int,
                realP: Int
            ) {
                bindGroupData(viewHolder, data as GroupWrapBean<P, C>, position, realP)
            }

            override fun isThisDelegate(data: DataWrapBean, position: Int, realP: Int) =
                data is GroupWrapBean<*, *>

        }, bundle)
    }

    /**######################抽象方法区 复写父类中的抽象方法，保证idea自动补全的顺序 ######################*/
    /**
     * onViewCreated 之后调用
     */
//    abstract override fun initAndObserve()

    abstract override fun initClickListener()

    /**
     * 返回liveData对象自动进入通用处理流程，返回null 进入自定义流程
     *
     * @param refresh
     * @param lastItem refresh 为true时  lastItem 为null
     * @return
     */

    abstract fun loadData(): LiveData<BaseResponse<MutableList<P>>>?

    abstract fun getItemData(groupData: P): MutableList<C>

    override fun loadData(
        refresh: Boolean,
        lastItem: DataWrapBean?
    ): LiveData<BaseResponse<MutableList<DataWrapBean>>>? {
        return MutableLiveData<BaseResponse<MutableList<DataWrapBean>>>().apply {
            loadData()?.observe(this@BaseMultiGroupListFragment, Observer {
                if (it.suc()) {
                    val res = BaseResponse(it.data?.flatMap { group ->
                        mutableListOf<DataWrapBean>(
                            GroupWrapBean<P, C>(
                                group,
                                isExpand = !(defaultHide && expandable)
                            )
                        ).apply {
                            val child = getItemData(group).map {
                                ItemWrapBean(itemData = it).apply {
                                    isHideBaseListItem = defaultHide && expandable
                                }
                            }.toMutableList()
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

    abstract fun bindGroupData(
        viewHolder: BaseItemViewHolder,
        groupWrapData: GroupWrapBean<P, C>?,
        position: Int,
        realP: Int
    )

}