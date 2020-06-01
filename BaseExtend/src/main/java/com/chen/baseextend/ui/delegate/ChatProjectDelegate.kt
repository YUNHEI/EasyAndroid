package com.chen.baseextend.ui.delegate

import android.content.Context
import android.view.View
import cn.jpush.im.android.api.content.CustomContent
import cn.jpush.im.android.api.enums.ContentType
import com.google.gson.Gson
import com.chen.baseextend.R
import com.chen.baseextend.bean.project.ProjectBean
import com.chen.baseextend.bean.tim.TMessage
import com.chen.baseextend.extend.context.startPage
import com.chen.baseextend.extend.fenToYuan
import com.chen.baseextend.route.MainRoute.PATH_PROJECT_DETAIL_FRAGMENT
import com.chen.basemodule.extend.load
import com.chen.basemodule.mlist.BaseItemViewDelegate
import com.chen.basemodule.mlist.BaseItemViewHolder
import kotlinx.android.synthetic.main.item_project.view.*

/**
 *  Created by chen on 2019/12/9
 **/
class ChatProjectDelegate(context: Context) : BaseItemViewDelegate<TMessage>(context) {

    override val layoutId = R.layout.item_project_chat_msg

    override fun bindData(viewHolder: BaseItemViewHolder, data: TMessage?, position: Int, realP: Int) {
        viewHolder.itemView.run {
            (data?.message?.content as CustomContent?)?.getStringValue("projectBean")?.let {
                Gson().fromJson(it, ProjectBean::class.java).run {
                    _title.setText(title)
                    _price.text = unitprice.fenToYuan()
                    _avatar.load(avatar)
                    _type.text = typeDesc ?: taskTypeDesc
                    _app.text = itemDesc
                    _remaining.text = "${completeNum}人已赚丨剩余$remainNum"
                    _ensure.visibility = if (hasBond == 1) View.VISIBLE else View.GONE
                    _top.visibility = if (isTop == 1) View.VISIBLE else View.GONE
                    _rec.visibility = if (isRecommend == 1) View.VISIBLE else View.GONE
                }
            }
        }
    }

    override fun onItemClick(viewHolder: BaseItemViewHolder, itemView: View, data: TMessage?, id: Int, position: Int, realP: Int): Boolean {
        viewHolder.run {
            (data?.message?.content as CustomContent?)?.getStringValue("projectBean")?.let {
                Gson().fromJson(it, ProjectBean::class.java).let { pro ->
                    when (id) {
                        else -> {
                            context.startPage(route = PATH_PROJECT_DETAIL_FRAGMENT, args = *arrayOf("id" to pro.id))
                        }
                    }
                }
            }
        }

        return super.onItemClick(viewHolder, itemView, data, id, position, realP)
    }

    override fun isThisDelegate(data: TMessage, position: Int, realP: Int): Boolean {
        return (data.message.contentType == ContentType.custom)
    }

}