package com.chen.app.ui.delegate

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.chen.app.R
import com.chen.baseextend.bean.menu.HomeMenuBean
import com.chen.baseextend.extend.context.startPage
import com.chen.baseextend.ui.WebActivity
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.mlist.BaseHeaderViewDelegate
import com.chen.basemodule.mlist.BaseItemViewHolder
import com.chen.basemodule.mlist.BaseMultiAdapter
import com.chen.basemodule.mlist.layoutmanager.AutoGridLayoutManager

class MenuHeaderDelegate(context: Context) : BaseHeaderViewDelegate(context) {

    override val layoutId = R.layout.layout_strategy_menu_header

    private val lm by lazy { AutoGridLayoutManager(context, 4) }

    val gridAdapter by lazy {
        BaseMultiAdapter<HomeMenuBean>(context).apply {
            addClickListener { _, _, data, _, _, _ ->
                when (data?.cmd) {
                    //4
                    "tgw://data_Center" -> WebActivity.toWebView(context, "https://m.tgw360.com/datacenter/#/home")
                    //无忧选股
                    "tgw://wuyou_list" -> WebActivity.toWebView(context, "http://q1.tgw360.com/phones.html", "无忧选股")
                    else -> {

                    }
                }
            }

            addItemViewDelegate(GridMenuDelegate(context))
        }
    }

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: RootBean?,
        position: Int,
        realP: Int
    ) {
        (viewHolder.itemView as RecyclerView).run {
            if (layoutManager == null) layoutManager = lm
            if (adapter == null) adapter = gridAdapter
        }
    }
}