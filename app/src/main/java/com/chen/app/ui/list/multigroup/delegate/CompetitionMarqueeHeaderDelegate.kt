package com.chen.app.ui.list.multigroup.delegate

import android.content.Context
import com.chen.app.R
import com.chen.app.databinding.LayoutHeaderTextMarqueeBinding
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.extend.createBinding
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.mlist.BaseHeaderViewDelegate
import com.chen.basemodule.mlist.BaseItemViewHolder
import kotlinx.android.synthetic.main.layout_header_text_marquee.view.*

class CompetitionMarqueeHeaderDelegate(context: Context) : BaseHeaderViewDelegate(context) {

//    override val layoutId = R.layout.layout_header_text_marquee

    override val binding  get() = createBinding(LayoutHeaderTextMarqueeBinding::inflate)

    private var update = false

    var bannerData = mutableListOf<String>()
        set(value) {
            update = true
            bannerData.clear()
            bannerData.addAll(value)
        }

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: RootBean?,
        position: Int,
        realP: Int
    ) {
        viewHolder.itemView.run {
            if (update) {
                _text_banner.setDatas(bannerData)
                update = false
            }
        }
    }
}