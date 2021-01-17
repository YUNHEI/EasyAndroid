package com.chen.basemodule.mlist

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.extend.toastError
import kotlin.reflect.KClass

/**
 * 所有子类需要实现 带Context 的构造函数
 * Created by chen on 2018/11/12
 */
open class BaseMultiAdapter<Bean : RootBean>(private val context: Context) :
    RecyclerView.Adapter<BaseItemViewHolder>() {

    val customerClickIds by lazy { mutableListOf<Int>() }

    private val MAX_ITEM_TYPE = 10000

    private val HEADER = 10000

    private val ITEM = 20000

    private val FOOTER = 30000

    var pageSize: Int = 20

    val currentPage: Int get() = data.size / pageSize + if (data.size % pageSize > 0) 1 else 0

    val nextPage: Int get() = currentPage.inc()

    var showFooter = false

    //无显示隐藏时等同于data
    val originData: MutableList<Bean> = mutableListOf()

    private var dataChange = true

    var expandAble = false

    val data: MutableList<Bean> = mutableListOf()
        get() {
            if (!expandAble) return originData
            if (dataChange) {
                field.clear()
                field.addAll(originData.filter { !it.isHideBaseListItem })
            }
            return field
        }


    val headerViewDelegates by lazy { mutableListOf<BaseHeaderViewDelegate>() }

    val itemViewDelegates by lazy { mutableListOf<BaseItemViewDelegate<Bean>>() }

    val footerViewDelegates by lazy { mutableListOf<BaseFooterViewDelegate>() }

    val completeFooterViewDelegate: BaseFooterViewDelegate by lazy {
        SimpleCompleteFooterViewDelegate(context).apply {
            (this as BaseItemViewDelegate<Bean>).mAdapter = this@BaseMultiAdapter
        }
    }

    private val onClicks: MutableList<((itemViewDelegate: BaseItemViewDelegate<Bean>, itemView: View, data: Bean?, id: Int, position: Int, dataPosition: Int) -> Unit)> =
        mutableListOf()

    private var onLongClick: ((itemViewDelegate: BaseItemViewDelegate<Bean>, viewHolder: BaseItemViewHolder, data: Bean?, id: Int, position: Int, dataPosition: Int) -> Unit)? =
        null

    private val headerCount: Int get() = headerViewDelegates.size

    private val footerCount: Int get() = footerViewDelegates.size

    val lastItem: Bean? get() = if (data.isEmpty()) null else data.last()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        if (viewType >= HEADER) {
            when {
                viewType < HEADER + MAX_ITEM_TYPE -> {
                    val headerIndex = viewType - HEADER
                    val headerViewDelegate = headerViewDelegates[headerIndex]
                    val headerHolder = headerViewDelegate.createItemViewHolder(parent)
                    bindOnAndLongClick(
                        (headerViewDelegate as BaseItemViewDelegate<Bean>),
                        headerHolder,
                        headerIndex,
                        HEADER
                    )
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
                    bindOnAndLongClick(
                        footerViewDelegate as BaseItemViewDelegate<Bean>,
                        footerViewHolder,
                        footerIndex,
                        FOOTER
                    )
                    return footerViewHolder
                }
            }
        }

        val errorDelegate = SimpleEmptyViewDelegate<Bean>(context).apply {
            mAdapter = this@BaseMultiAdapter
        }
        return errorDelegate.createItemViewHolder(parent)
    }

    private fun bindOnAndLongClick(
        itemViewDelegate: BaseItemViewDelegate<Bean>,
        itemViewHolder: BaseItemViewHolder,
        index: Int,
        itemType: Int
    ) {

        itemViewDelegate.customerClickViewIds.forEach {
            itemViewHolder.itemView.run {

                (if (it == -1) this else findViewById<View>(it))?.setOnClickListener {

                    val position = itemViewHolder.adapterPosition

                    val realP = if (itemType == ITEM) position - headerCount else index

                    val itemData = if (itemType == ITEM) data[realP] else null

                    if (!itemViewDelegate.onItemClick(
                            itemViewHolder,
                            this,
                            itemData,
                            it.id,
                            position,
                            realP
                        )
                    ) {
                        onClicks.forEach { click ->
                            click.invoke(
                                itemViewDelegate,
                                this,
                                itemData,
                                it.id,
                                position,
                                realP
                            )
                        }
                    }
                }
            }
        }

        itemViewHolder.itemView.setOnLongClickListener {

            val position = itemViewHolder.adapterPosition

            val realP = if (itemType == ITEM) position - headerCount else index

            val itemData = if (itemType == ITEM) data[realP] else null

            if (!itemViewDelegate.onItemLongClick(
                    itemViewHolder,
                    it,
                    itemData,
                    it.id,
                    position,
                    realP
                )
            ) {
                onLongClick?.invoke(
                    itemViewDelegate,
                    itemViewHolder,
                    itemData,
                    it.id,
                    position,
                    realP
                )
            }
            false
        }
    }

    override fun onBindViewHolder(
        holder: BaseItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (!payloads.isNullOrEmpty()) {
            val viewType = getItemViewType(position)
            if (viewType >= HEADER) {
                when {
                    viewType < HEADER + MAX_ITEM_TYPE -> {
                        val headerIndex = viewType - HEADER
                        headerViewDelegates[headerIndex].updateData(
                            holder,
                            null,
                            position,
                            position,
                            payloads
                        )
                    }
                    viewType < ITEM + MAX_ITEM_TYPE -> {
                        val itemIndex = viewType - ITEM
                        val realP = position - headerCount
                        itemViewDelegates[itemIndex].updateData(
                            holder,
                            data[realP],
                            position,
                            realP,
                            payloads
                        )
                    }
                    viewType < FOOTER + MAX_ITEM_TYPE -> {
                        val footerIndex = viewType - FOOTER
                        val realP = position - headerCount - data.size
                        footerViewDelegates[footerIndex].updateData(
                            holder,
                            null,
                            position,
                            realP,
                            payloads
                        )
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

    override fun onViewAttachedToWindow(holder: BaseItemViewHolder) {
        holder.onAttachListener?.invoke(
            holder.itemView,
            data.getOrNull(holder.adapterPosition - headerCount)
        )
        super.onViewAttachedToWindow(holder)
    }

    override fun getItemViewType(position: Int): Int {
        when {
            position < headerCount -> {
                return HEADER + position
            }
            position < headerCount + data.size -> {
                for (i in itemViewDelegates.indices) {
                    if (itemViewDelegates[i].isThisDelegate(
                            data[position - headerCount],
                            position,
                            position - headerCount
                        )
                    ) {
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

    fun addHeaderViewDelegate(headerViewDelegate: BaseHeaderViewDelegate, position: Int = -1) {
        (headerViewDelegate as BaseItemViewDelegate<Bean>).run {
            mAdapter = this@BaseMultiAdapter
            customerClickViewIds.addAll(customerClickIds)
            if (position > headerViewDelegates.size || position < 0) {
                headerViewDelegates.add(headerViewDelegate)
            } else {
                headerViewDelegates.add(position, headerViewDelegate)
            }
        }
    }

    /**
     * 需要手动设置BaseItemViewDelegate 的 bundle 来传递参数
     * @param itemViewDelegate
     */
    fun addItemViewDelegate(
        vararg itemViewDelegateClasses: KClass<out BaseItemViewDelegate<Bean>>,
        delegateBundle: Bundle? = null
    ) {
        itemViewDelegateClasses.forEach {
            try {
                val constructor = it.java.getConstructor(Context::class.java)
                val delegate = constructor.newInstance(context)
                addItemViewDelegate(delegate, delegateBundle)
            } catch (e: Exception) {
                e.printStackTrace()
                String.format("请实现%s父类的带有Context的构造函数，并且禁止混淆", it.simpleName).toastError()
            }
        }
    }

    fun addItemViewDelegate(
        itemViewDelegate: BaseItemViewDelegate<Bean>,
        delegateBundle: Bundle? = null
    ) {
        delegateBundle?.run { itemViewDelegate.bundle = this }
        itemViewDelegate.mAdapter = this
        itemViewDelegate.customerClickViewIds.addAll(customerClickIds)
        itemViewDelegates.add(itemViewDelegate)
    }

    fun getFooterViewHolder(position: Int) = footerViewDelegates.getOrNull(position)?.viewHolder

    fun addFooterViewDelegate(footerViewDelegate: BaseFooterViewDelegate, position: Int = -1) {
        (footerViewDelegate as BaseItemViewDelegate<Bean>).run {
            mAdapter = this@BaseMultiAdapter
            customerClickViewIds.addAll(customerClickIds)
            if (position < 0 || position > footerViewDelegates.size) {
                footerViewDelegates.add(footerViewDelegate)
            } else {
                footerViewDelegates.add(position, footerViewDelegate)
            }
        }
    }

    fun getDataByPosition(position: Int) = data.getOrNull(position - headerCount)

    fun addItems(data: List<Bean>?) {
        if (data == null) return
        originData.addAll(data)
        dataChange = true
        notifyItemRangeInserted(headerCount + this.data.size - data.size, data.size)
    }

    fun addItem(data: Bean, index: Int? = null) {
        if (index != null && index <= originData.size) {
            originData.add(index, data)
        } else {
            originData.add(data)
        }
        dataChange = true
        notifyItemInserted(headerCount + this.data.size - 1)
    }

    fun moveItem(from: Int, to: Int) {
        originData.add(to, originData.removeAt(from))
        notifyItemMoved(from, to)
        notifyItemChanged(to)
    }

    fun removeItem(d: Bean) {
        removeItem(d, data.indexOf(d))
    }

    fun removeItem(p: Int): Bean {
        val bean = data[p]
        removeItem(bean, p)
        return bean
    }

    private fun removeItem(d: Bean, p: Int) {
        originData.remove(d)
        dataChange = true
        if (originData.isEmpty()) {
            notifyDataSetChanged()
        } else {
            notifyItemRemoved(p)
        }
    }

    fun replaceItems(data: List<Bean>?) {
        this.originData.clear()
        dataChange = true
        if (!data.isNullOrEmpty()) {
            this.originData.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun updateItem(dataOld: Bean, dataNew: Bean) {
        val p = originData.indexOf(dataOld)
        originData.add(p, dataNew)
        originData.remove(dataOld)
        dataChange = true
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

    fun isHeader(position: Int) = getItemViewType(position) in HEADER until HEADER + MAX_ITEM_TYPE

    fun isItem(position: Int) = getItemViewType(position) in ITEM until ITEM + MAX_ITEM_TYPE

    fun isFooter(position: Int) = getItemViewType(position) in FOOTER until FOOTER + MAX_ITEM_TYPE

    fun addClickListener(
        vararg ids: Int,
        onClick: ((itemViewDelegate: BaseItemViewDelegate<Bean>, itemView: View, data: Bean?, id: Int, position: Int, dataPosition: Int) -> Unit)?
    ) {
        customerClickIds.addAll(ids.toMutableList())
        onClick?.run { onClicks.add(this) }
    }

    fun setLongClickListener(
        onLongClick: ((itemViewDelegate: BaseItemViewDelegate<Bean>, viewHolder: BaseItemViewHolder, data: Bean?, id: Int, position: Int, dataPosition: Int) -> Unit)?
    ) {
        this.onLongClick = onLongClick
    }
}
