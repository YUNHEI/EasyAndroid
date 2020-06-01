package com.chen.baseextend.widget.dialog

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.chen.baseextend.R
import com.chen.basemodule.extend.dp2px
import com.chen.basemodule.extend.drawable
import com.chen.basemodule.widget.smartrefresh.layout.util.DensityUtil

/**
 *  Created by chen on 2019/8/2
 **/
class SimpleItemDialog(context: Context, vararg items: DialogItem) : AlertDialog(context) {

    private val container: LinearLayout

    private val dialogItems: MutableList<DialogItem> = mutableListOf()

    init {

        setCanceledOnTouchOutside(true)

        setCancelable(true)

        NestedScrollView(context).run {
            container = LinearLayout(context).apply {

                orientation = LinearLayout.VERTICAL

                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

                background = context.drawable(R.drawable.bg_white_radius)

            }
            addView(container)
            setView(this)
        }

        dialogItems.run {

            clear()
            addAll(items)

            forEach {
                val item = TextView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    setBackgroundResource(R.drawable.bg_item_white_ef_line)
                    setTextColor(ContextCompat.getColor(context, it.textColor ?: R.color.gray_33))
                    textSize = 16f
                    setPadding(DensityUtil.dp2px(26f), DensityUtil.dp2px(17f), DensityUtil.dp2px(26f), DensityUtil.dp2px(17f))
                }
                item.text = it.title
                it.img?.run {
                    item.compoundDrawablePadding = dp2px(12)
                    item.setCompoundDrawables(context.drawable(this)?.apply {
                        setBounds(0, 0, dp2px(22), dp2px(22))
                    }, null, null, null)
                }
                container.addView(item)
            }
        }
    }

    fun readyShow(onClick: ((item: DialogItem) -> Unit)? = null): SimpleItemDialog {

        for (i in 0 until container.childCount) {
            container.getChildAt(i).setOnClickListener {
                if ((dialogItems[i].onClick?.invoke(dialogItems[i].id
                                ?: dialogItems[i].title) != true)) {
                    onClick?.invoke(dialogItems[i])
                }

                dismiss()
            }
        }

        show()

        return this
    }
}