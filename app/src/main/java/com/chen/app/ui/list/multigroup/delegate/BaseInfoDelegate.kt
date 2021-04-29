package com.chen.app.ui.list.multigroup.delegate

import android.content.Context
import com.chen.baseextend.bean.news.NewsBean
import com.chen.basemodule.mlist.BaseItemViewHolder
import com.chen.basemodule.mlist.BaseMultiSourceDelegate
import kotlinx.android.synthetic.main.item_info_one_image.view.*

/**
 *  Created by chen on 2019/10/18
 **/
abstract class BaseInfoDelegate(context: Context) : BaseMultiSourceDelegate<NewsBean>(context) {

    override fun bindData(viewHolder: BaseItemViewHolder, data: NewsBean?, position: Int, realP: Int) {

        viewHolder.itemView.run {
            data?.run {

                _title.text = title

//                records?.run {
//                    if (this.contains(informationId) || isRead) {
//                        _title.setTextColor(context.color(R.color.gray_88))
//                    } else {
//                        _title.setTextColor(context.color(R.color.gray_33))
//                    }
//                }

//                _delete.visibility = if (userId != data.crtUserId && showDelete) View.VISIBLE else View.GONE

                /*控制信息显示*/
//                StringBuilder().run {
//                    if (groupType != "2") {
//                        append("$nickname  ")
//                    }
//                    append(if (commentCount > 0) "${commentCount}评论  " else "")
//                    append(if (likes > 0) "${likes}赞  " else "")
//                    append(DateUtil.getDateWithHours(data.publishTime, false, false, false))
//
//                    _information.text = toString()
//                }

            }
        }
    }
}