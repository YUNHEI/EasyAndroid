package com.chen.app.ui.list.multigroup.delegate

import android.content.Context
import android.view.View
import com.chen.app.R
import com.chen.app.databinding.ItemInfoOneImageBinding
import com.chen.baseextend.bean.news.NewsBean
import com.chen.basemodule.extend.createBinding
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.load
import com.chen.basemodule.mlist.BaseItemViewHolder
import kotlinx.android.synthetic.main.item_info_one_image.view.*
import kotlinx.android.synthetic.main.item_info_one_image.view._title
import kotlinx.android.synthetic.main.item_info_video.view.*

/**
 *  Created by chen on 2019/10/18
 **/
class OneImageDelegate(context: Context) : BaseInfoDelegate(context) {

    override val binding get() = createBinding(ItemInfoOneImageBinding::inflate)

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: NewsBean?,
        position: Int,
        realP: Int
    ) {
        viewHolder.itemView.run {
            data?.run {
                _cover.run {
                    if (image_list.isNullOrEmpty()) {
                        load(null)
                        visibility = View.GONE
                    } else {
                        visibility = View.VISIBLE
                        load(image_list!![0].url)
                    }
                }
                handleCommon(viewHolder, data, position, realP, _title)
            }
        }
    }

    override fun isThisDelegate(data: NewsBean, position: Int, realP: Int): Boolean {
        return data.run { has_video != true && (image_list?.size ?: 0) <= 1 }
    }
}