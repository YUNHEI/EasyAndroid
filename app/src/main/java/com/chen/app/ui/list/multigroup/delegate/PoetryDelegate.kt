package com.chen.app.ui.list.multigroup.delegate

import android.content.Context
import com.chen.app.R
import com.chen.baseextend.bean.PoetryBean
import com.chen.basemodule.mlist.BaseItemViewHolder
import com.chen.basemodule.mlist.BaseMultiSourceDelegate
import kotlinx.android.synthetic.main.item_poetry.view.*

/**
 * 唐诗
 */
class PoetryDelegate(context: Context) : BaseMultiSourceDelegate<PoetryBean>(context) {


    override val layoutId = R.layout.item_poetry

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: PoetryBean?,
        position: Int,
        realP: Int
    ) {
        viewHolder.itemView.run{
            data?.run{
                _title.text = content
                _des.text = "$title · $authors"
            }
        }
    }

    override fun isThisDelegate(data: PoetryBean, position: Int, realP: Int) = true

}