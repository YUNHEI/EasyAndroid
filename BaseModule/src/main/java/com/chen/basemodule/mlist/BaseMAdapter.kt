package com.chen.basemodule.mlist

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.extend.toastError

/**
 * 所有子类需要实现 带Context 的构造函数
 * Created by chen on 2018/11/12
 */
class BaseMAdapter<T : RootBean>(private val context: Context) : RecyclerView.Adapter<BaseItemViewHolder>() {

    val customerClickIds by lazy { mutableListOf<Int>() }

    private val MAX_ITEM_TYPE = 10000

    private val HEADER = 10000

    private val ITEM = 20000

    private val FOOTER = 30000

    var pageSize: Int = 20

    val currentPage: Int get() = data.size / pageSize + if (data.size % pageSize > 0) 1 else 0

    val nextPage: Int get() = currentPage.inc()

    var showFooter = false

    private val originData: MutableList<T> = mutableListOf()

    val data: MutableList<T> get() = originData.filter { !it.isHideBaseListItem }.toMutableList()

    val headerViewDelegates by lazy { mutableListOf<BaseHeaderViewDelegate<T>>() }

    val itemViewDelegates by lazy { mutableListOf<BaseItemViewDelegate<T>>() }

    val footerViewDelegates by lazy { mutableListOf<BaseFooterViewDelegate<T>>() }

    val completeFooterViewDelegate: BaseFooterViewDelegate<T> by lazy { SimpleCompleteFooterViewDelegate<T>(context).apply { mAdapter = this@BaseMAdapter } }

    private val onClicks: MutableList<((itemViewDelegate: BaseItemViewDelegate<T>, itemView: View, data: T?, id: Int, position: Int, dataPosition: Int) -> Unit)> = mutableListOf()

    private var onLongClick: ((itemViewDelegate: BaseItemViewDelegate<T>, itemView: View, data: T?, id: Int, position: Int, dataPosition: Int) -> Unit)? = null

    private val headerCount: Int get() = headerViewDelegates.size

    private val footerCount: Int get() = footerViewDelegates.size

    val lastItem: T? get() = if (data.isEmpty()) null else data.last()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        if (viewType >= HEADER) {
            when {
                viewType < HEADER + MAX_ITEM_TYPE -> {
                    val headerIndex = viewType - HEADER
                    val headerViewDelegate = headerViewDelegates[headerIndex]
                    val headerHolder = headerViewDelegate.createItemViewHolder(parent)
                    bindOnAndLongClick(headerViewDelegate, headerHolder, headerIndex, HEADER)
                    return headerHolder

                }
                viewType < ITEM + MAX_ITEM_TYPE -> {
                    val itemIndex = viewType - ITEM
                    val itemViewDelegate = itemViewDelegates[itemIndex]
                    val itemViewHolder = itemViewDelegate.createItemViewHolder(parent)
                    bindOnAndLongClick(itemViewDelegate, itemViewHolder, itemIndex, ITEM)
                    return itemViewHolder
                }
                viewType < FOOTER + MAX_ITEM_TYPE -> {
                    val footerIndex = viewType - FOOTER
                    val footerViewDelegate = footerViewDelegates[footerIndex]
                    val footerViewHolder = footerViewDelegate.createItemViewHolder(parent)
                    bindOnAndLongClick(footerViewDelegate, footerViewHolder, footerIndex, FOOTER)
                    return footerViewHolder
                }
            }
        }

        val errorDelegate = SimpleEmptyViewDelegate<T>(context).apply {
            mAdapter = this@BaseMAdapter
        }
        return errorDelegate.createItemViewHolder(parent)
    }

    private fun bindOnAndLongClick(itemViewDelegate: BaseItemViewDelegate<T>, itemViewHolder: BaseItemViewHolder, index: Int, itemType: Int) {

        itemViewDelegate.customerClickViewIds.forEach {
            itemViewHolder.itemView.run {
                when (it) {
                    -1 -> {
                        setOnClickListener {
                            val position = itemViewHolder.adapterPosition

                            val realP = if (itemType == ITEM) position - headerCount else index

                            val itemData = if (itemType == ITEM) data[realP] else null

                            if (!itemViewDelegate.onItemClick(itemViewHolder, it, itemData, it.id, position, realP)) {
                                onClicks.forEach { click ->
                                    click.invoke(itemViewDelegate, it, itemData, it.id, position, realP)
                                }
                            }
                        }
                    }
                    else -> {
                        findViewById<View>(it)?.setOnClickListener {
                            val position = itemViewHolder.adapterPosition

                            val realP = if (itemType == ITEM) position - headerCount else index

                            val itemData = if (itemType == ITEM) data[realP] else null

                            if (!itemViewDelegate.onItemClick(itemViewHolder, this, itemData, it.id, position, realP)) {
                                onClicks.forEach { click ->
                                    click.invoke(itemViewDelegate, this, itemData, it.id, position, realP)
                                }
                            }
                        }
                    }
                }
            }
        }

        itemViewHolder.itemView.setOnLongClickListener {

            val position = itemViewHolder.adapterPosition

            val realP = if (itemType == ITEM) position - headerCount else index

            val itemData = if (itemType == ITEM) data[realP] else null

            if (!itemViewDelegate.onItemLongClick(itemViewHolder, it, itemData, it.id, position, realP)) {
                onLongClick?.invoke(itemViewDelegate, it, itemData, it.id, position, realP)
            }
            false
        }
    }

    override fun onBindViewHolder(holder: BaseItemViewHolder, position: Int, payloads: MutableList<Any>) {
        if (!payloads.isNullOrEmpty()) {
            val viewType = getItemViewType(position)
            if (viewType >= HEADER) {
                when {
                    viewType < HEADER + MAX_ITEM_TYPE -> {
                        val headerIndex = viewType - HEADER
                        headerViewDelegates[headerIndex].updateData(holder, null, position, position, payloads)
                    }
                    viewType < ITEM + MAX_ITEM_TYPE -> {
                        val itemIndex = viewType - ITEM
                        val realP = position - headerCount
                        itemViewDelegates[itemIndex].updateData(holder, data[realP], position, realP, payloads)
                    }
                    viewType < FOOTER + MAX_ITEM_TYPE -> {
                        val footerIndex = viewType - FOOTER
                        val realP = position - headerCount - data.size
                        footerViewDelegates[footerIndex].updateData(holder, null, position, realP, payloads)
                    }
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: BaseItemViewHolder, position: Int) {

        val viewType = getItemViewType(position)
        if (viewType >= HEADER) {
            when {
                viewType < HEADER + MAX_ITEM_TYPE -> {
                    val headerIndex = viewType - HEADER
                    headerViewDelegates[headerIndex].bindData(holder, null, position, position)
                }
                viewType < ITEM + MAX_ITEM_TYPE -> {
                    val itemIndex = viewType - ITEM
                    val realP = position - headerCount
                    itemViewDelegates[itemIndex].bindData(holder, data[realP], position, realP)
                }
                viewType < FOOTER + MAX_ITEM_TYPE -> {
                    val footerIndex = viewType - FOOTER
                    val realP = position - headerCount - data.size
                    footerViewDelegates[footerIndex].bindData(holder, null, position, realP)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        when {
            position < headerCount -> {
                return HEADER + position
            }
            position < headerCount + data.size -> {
                for (i in itemViewDelegates.indices) {
                    if (itemViewDelegates[i].isThisDelegate(data[position - headerCount], position, position - headerCount)) {
                        return ITEM + i
                    }
                }
                return super.getItemViewType(position)
            }
            position < headerCount + data.size + footerCount -> {
                return FOOTER + position - headerCount - data.size
            }
            else -> {
                return super.getItemViewType(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size + headerCount + if (showFooter) footerCount else 0
    }

    fun getHeaderViewHolder(position: Int): BaseItemViewHolder? {
        return if (position >= headerViewDelegates.size) null else headerViewDelegates[position].viewHolder
    }

    fun addHeaderViewDelegate(headerViewDelegate: BaseHeaderViewDelegate<T>) {
        addHeaderViewDelegate(0, headerViewDelegate)
    }

    fun addHeaderViewDelegate(position: Int, headerViewDelegate: BaseHeaderViewDelegate<T>) {
        headerViewDelegate.run {
            mAdapter = this@BaseMAdapter
            customerClickViewIds.addAll(customerClickIds)
            if (position > headerViewDelegates.size) {
                headerViewDelegates.add(this)
            } else {
                headerViewDelegates.add(position, this)
            }
        }
    }

    /**
     * 需要手动设置BaseItemViewDelegate 的 bundle 来传递参数
     * @param itemViewDelegate
     */
    fun addItemViewDelegate(vararg itemViewDelegateClasses: Class<out BaseItemViewDelegate<T>>, delegateBundle: Bundle? = null) {
        itemViewDelegateClasses.forEach {
            try {
                val constructor = it.getConstructor(Context::class.java)
                val delegate = constructor.newInstance(context)
                addItemViewDelegate(delegate, delegateBundle)
            } catch (e: Exception) {
                e.printStackTrace()
                String.format("请实现%s父类的带有Context的构造函数，并且禁止混淆", it.name).toastError()
            }
        }
    }

    fun addItemViewDelegate(itemViewDelegate: BaseItemViewDelegate<T>, delegateBundle: Bundle? = null) {
        delegateBundle?.run { itemViewDelegate.bundle = this }
        itemViewDelegate.mAdapter = this
        itemViewDelegate.customerClickViewIds.addAll(customerClickIds)
        itemViewDelegates.add(itemViewDelegate)
    }

    fun getFooterViewHolder(position: Int): BaseItemViewHolder? {
        return if (position >= footerViewDelegates.size) null else footerViewDelegates[position].viewHolder
    }

    fun addFooterViewDelegate(footerViewDelegate: BaseFooterViewDelegate<T>) {
        addFooterViewDelegate(0, footerViewDelegate)
    }

    fun addFooterViewDelegate(position: Int, footerViewDelegate: BaseFooterViewDelegate<T>) {
        footerViewDelegate.run {
            mAdapter = this@BaseMAdapter
            customerClickViewIds.addAll(customerClickIds)
            if (position > footerViewDelegates.size) {
                footerViewDelegates.add(this)
            } else {
                footerViewDelegates.add(position, this)
            }
        }
    }

    fun getDataByPosition(position: Int): T? {
        return if (position >= headerCount && position < headerCount + data.size) {
            data[position - headerCount]
        } else null
    }

    fun addItems(data: List<T>?) {
        if (data == null) return
        this.originData.addAll(data)
        notifyItemRangeChanged(this.data.size - data.size, data.size)
    }

    fun removeItems(d: T? = null, p: Int = -1) {
        if (d == null) return

        val index = if (p >= 0) p else data.indexOf(d)

        originData.remove(d)
        if (originData.isEmpty()) {
            notifyDataSetChanged()
        } else {
            notifyItemRemoved(index)
        }
    }

    fun replaceItems(data: List<T>?) {
        this.originData.clear()
        if (!data.isNullOrEmpty()) {
            this.originData.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun updateItem(dataOld: T, dataNew: T) {
        val p = originData.indexOf(dataOld)
        originData.add(p, dataNew)
        originData.remove(dataOld)
        notifyItemChanged(p)
    }

    fun showCompleteFooter(showCompleteFooter: Boolean) {

        if (!showCompleteFooter && footerViewDelegates.contains(completeFooterViewDelegate)) {
            footerViewDelegates.remove(completeFooterViewDelegate)
        } else if (showCompleteFooter) {
            if (!footerViewDelegates.contains(completeFooterViewDelegate)) {
                footerViewDelegates.add(completeFooterViewDelegate)
            }
        }
    }

    fun addClickListener(vararg ids: Int, onClick: ((itemViewDelegate: BaseItemViewDelegate<T>, itemView: View, data: T?, id: Int, position: Int, dataPosition: Int) -> Unit)?) {
        customerClickIds.addAll(ids.toMutableList())
        onClick?.run { onClicks.add(this) }
    }

    fun setLongClickListener(vararg ids: Int, onLongClick: ((itemViewDelegate: BaseItemViewDelegate<T>, itemView: View, data: T?, id: Int, position: Int, dataPosition: Int) -> Unit)?) {
        customerClickIds.addAll(ids.toMutableList())
        this.onLongClick = onLongClick
    }
}
