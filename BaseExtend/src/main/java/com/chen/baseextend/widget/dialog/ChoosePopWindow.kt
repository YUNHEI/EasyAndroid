package com.chen.baseextend.widget.dialog

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chen.baseextend.R
import com.chen.baseextend.bean.project.ItemTypeBean
import com.chen.basemodule.widget.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.layout_project_choose.view.*

/**
 *  Created by chen on 2019/7/29
 **/
class ChoosePopWindow(val context: Context, height: Int? = 0) : PopupWindow() {

    val spanCount = 4

    private val mPadding by lazy { DensityUtil.dp2px(15f) }

    val mAdapter by lazy {
        object : BaseQuickAdapter<ItemTypeBean, BaseViewHolder>(R.layout.item_project_type) {
            override fun convert(helper: BaseViewHolder?, item: ItemTypeBean?) {
                helper?.getView<CheckBox>(R.id._type)?.run {
                    text = item?.itemName
                }
            }
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_project_choose, null).run {

            contentView = this

            mAdapter.apply {

                bindToRecyclerView(_recycler)

                setNewData(mutableListOf())
            }

            _recycler.run {
                layoutManager = GridLayoutManager(context, spanCount)

                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)

                        val position = parent.getChildAdapterPosition(view)

                        val column = position.rem(spanCount)

                        outRect.run {

                            left = mPadding - column * mPadding / spanCount
                            right = (column + 1) * mPadding / spanCount
                            bottom = mPadding
                            if (position < spanCount) {
                                top = mPadding
                            }
                        }
                    }
                })
            }

            _cover.setOnClickListener { dismiss() }
        }

        width = LinearLayout.LayoutParams.MATCH_PARENT

        this.height = if (height == null || height == 0) LinearLayout.LayoutParams.WRAP_CONTENT else height

        isOutsideTouchable = true
        isFocusable = true
    }

    fun getSelectedIds(): String {

        val ids = StringBuilder()

        for (i in 0 until mAdapter.itemCount) {

            if ((contentView._recycler.getChildAt(i) as CheckBox).isChecked) {
                ids.append(if (ids.isEmpty()) mAdapter.data[i].id else ",${mAdapter.data[i].id}")
            }
        }

        return ids.toString()
    }

    fun onConfirmClick(onClick: (ids: String) -> Unit): ChoosePopWindow {
        contentView._confirm.setOnClickListener {
            onClick.invoke(getSelectedIds())
            dismiss()
        }
        return this
    }

    fun onResetClick(onClick: () -> Unit): ChoosePopWindow {
        contentView._reset.setOnClickListener {

            for (i in 0 until mAdapter.itemCount) {
                (contentView._recycler.getChildAt(i) as CheckBox).isChecked = false
            }

            onClick.invoke()
            dismiss()
        }
        return this
    }
}