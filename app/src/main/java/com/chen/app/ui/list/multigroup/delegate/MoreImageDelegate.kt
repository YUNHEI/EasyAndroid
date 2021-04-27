package com.chen.app.ui.list.multigroup.delegate

import android.content.Context
import com.chen.app.R
import com.chen.app.databinding.ItemInfoMoreImageBinding
import com.chen.baseextend.bean.news.NewsBean
import com.chen.basemodule.extend.createBinding
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.load
import com.chen.basemodule.mlist.BaseItemViewHolder
import kotlinx.android.synthetic.main.item_info_more_image.view.*
import kotlinx.android.synthetic.main.item_info_more_image.view._title
import kotlinx.android.synthetic.main.item_info_video.view.*

/**
 *  Created by chen on 2019/10/18
 **/
class MoreImageDelegate(context: Context) : BaseInfoDelegate(context) {

    //    override val layoutId = R.layout.item_info_more_image
    override val binding  get() = createBinding(ItemInfoMoreImageBinding::inflate)

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: NewsBean?,
        position: Int,
        realP: Int
    ) {
        viewHolder.itemView.run {
            data?.run {
                _image_one.load(image_list!![0].url)
                _image_two.load(image_list!![1].url)
                _image_three.load(image_list!![2].url)

                handleCommon(viewHolder, data, position, realP, _title)
            }
        }
    }

    override fun isThisDelegate(data: NewsBean, position: Int, realP: Int): Boolean {
        return data.run { (has_image == true) && (image_list?.size ?: 0) >= 3 }
            ?: false
    }
}