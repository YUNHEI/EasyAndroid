package com.chen.app.ui.list.multigroup.delegate

import android.content.Context
import com.chen.app.R
import com.chen.baseextend.bean.JokeBean
import com.chen.baseextend.extend.bold
import com.chen.basemodule.extend.load
import com.chen.basemodule.mlist.BaseItemViewHolder
import com.chen.basemodule.mlist.BaseMultiSourceDelegate
import kotlinx.android.synthetic.main.item_joke.view.*

/**
 * 唐诗
 */
class JokeDelegate(context: Context) : BaseMultiSourceDelegate<JokeBean>(context) {

    override val layoutId = R.layout.item_joke

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: JokeBean?,
        position: Int,
        realP: Int
    ) {
        viewHolder.itemView.run{
            data?.run{
                _avatar.load(header)
                _nickname.text = name
                _nickname.bold()
                _msg.text = text
            }
        }
    }

    override fun isThisDelegate(data: JokeBean, position: Int, realP: Int) = true

}