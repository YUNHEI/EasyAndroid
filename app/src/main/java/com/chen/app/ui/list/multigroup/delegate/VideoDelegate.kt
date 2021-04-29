package com.chen.app.ui.list.multigroup.delegate

import android.content.Context
import android.view.View
import com.chen.app.R
import com.chen.baseextend.bean.news.NewsBean
import com.chen.basemodule.extend.load
import com.chen.basemodule.mlist.BaseItemViewHolder
import kotlinx.android.synthetic.main.item_info_video.view.*

/**
 *  Created by chen on 2019/10/18
 **/
class VideoDelegate(context: Context) : BaseInfoDelegate(context) {

    override val layoutId = R.layout.item_info_video

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: NewsBean?,
        position: Int,
        realP: Int
    ) {
        super.bindData(viewHolder, data, position, realP)
        viewHolder.itemView.run {
            data?.run {
                _video_cover.run {
                    if (large_image_list.isNullOrEmpty()) {
                        load(source)
                        visibility = View.VISIBLE
                    } else {
                        visibility = View.VISIBLE
                        load(large_image_list!![0].url)
                    }
                }
            }
        }
    }

    override fun isThisDelegate(data: NewsBean, position: Int, realP: Int): Boolean {
        return data.run { has_video == true } ?: false
    }
}