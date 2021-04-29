package com.chen.app.ui.delegate

import android.content.Context
import com.chen.app.R
import com.chen.baseextend.bean.menu.HomeMenuBean
import com.chen.basemodule.extend.load
import com.chen.basemodule.mlist.BaseItemViewDelegate
import com.chen.basemodule.mlist.BaseItemViewHolder
import kotlinx.android.synthetic.main.item_home_menu.view.*

class GridMenuDelegate(context: Context) : BaseItemViewDelegate<HomeMenuBean>(context) {

    override val layoutId = R.layout.item_home_menu

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: HomeMenuBean?,
        position: Int,
        realP: Int
    ) {
        viewHolder.itemView.run {
            data?.run {

                icon?.run {
                    _icon.setImageResource(this)
                } ?: run { _icon.load(image, R.drawable.bg_item_white) }

                _title.text = name
            }
        }
    }

    override fun isThisDelegate(data: HomeMenuBean, position: Int, realP: Int) = true

}