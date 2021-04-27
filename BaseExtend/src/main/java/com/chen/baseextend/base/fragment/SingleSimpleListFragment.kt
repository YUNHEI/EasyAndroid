package com.chen.baseextend.base.fragment

import android.graphics.Rect
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chen.baseextend.R
import com.chen.baseextend.databinding.ItemCommonBinding
import com.chen.basemodule.basem.BaseBean
import com.chen.basemodule.extend.*
import com.chen.basemodule.mlist.BaseItemViewHolder
import com.chen.basemodule.network.base.BaseResponse
import kotlinx.android.synthetic.main.item_common.view.*

/**
 * 单一布局列表,已配置默认item样式
 *
 *  Created by chen on 2019/6/6
 **/
abstract class SingleSimpleListFragment : SingleListFragment<SingleSimpleListFragment.ItemBean>() {

    abstract val wrapData: MutableList<ItemBean>

    open val itemStyle = ItemStyle()

    override fun loadData(refresh: Boolean, lastItem: ItemBean?): LiveData<BaseResponse<MutableList<ItemBean>>>? {
        return MutableLiveData<BaseResponse<MutableList<ItemBean>>>().apply {
            postValue(BaseResponse(wrapData, 200, "成功"))
        }
    }

    open class ItemBean(var title: String, var brief: String?, var image: String? = null, var icon: Int? = null, val itemBeanStyle: ItemStyle? = null) : BaseBean()

    inline fun DATA(itemAction: MutableList<ItemBean>.() -> Unit): MutableList<ItemBean> {
        return mutableListOf<ItemBean>().apply { itemAction(this) }
    }

    inline fun MutableList<ItemBean>.Item(title: String, brief: String? = null, image: String? = null, icon: Int? = null, itemStyle: ItemStyle? = null) {
        add(ItemBean(title, brief, image, icon, itemStyle))
    }

//    override val itemLayoutId = R.layout.item_common

    override val itemBinding by doBinding(ItemCommonBinding::inflate)

    override fun bindItemData(viewHolder: BaseItemViewHolder, data: ItemBean, position: Int, realP: Int) {
        viewHolder.itemView.run {
            data.run {
                (itemBeanStyle ?: itemStyle).run {

                    imageStyle.let { style ->

                        _image.run {
                            isVisible = style.visible
                            (layoutParams as ConstraintLayout.LayoutParams).let {
                                it.width = style.size.first
                                it.height = style.size.second
                                it.setMargins(style.margin.left, style.margin.top, style.margin.right, style.margin.bottom)
                            }
                            if (icon != null && icon!! > 0) {
                                setImageResource(icon!!)
                            } else {
                                load(image, style.placeholder)
                            }
                        }
                    }

                    titleStyle.let { style ->
                        _title.run {
                            text = title
                            textSize = style.textSize
                            setTextColor(color(style.textColor))
                            (layoutParams as ConstraintLayout.LayoutParams).let {
                                it.setMargins(style.margin.left, style.margin.top, style.margin.right, style.margin.bottom)
                            }
                            maxLines = style.maxLine
                        }
                    }

                    briefStyle.let { style ->
                        _brief.run {
                            text = brief
                            textSize = style.textSize
                            setTextColor(color(style.textColor))
                            isVisible = !(style.autoHide && brief.isNullOrEmpty())
                            (layoutParams as ConstraintLayout.LayoutParams).let {
                                it.setMargins(style.margin.left, style.margin.top, style.margin.right, style.margin.bottom)
                            }
                            maxLines = style.maxLine
                        }
                    }

                    separatorStyle.let { style ->
                        _separator.run {
                            isVisible = style.show
                            setBackgroundColor(color(style.color))
                            (layoutParams as ConstraintLayout.LayoutParams).let {
                                it.height = dimen(style.width)
                                it.setMargins(style.margin.left, style.margin.top, style.margin.right, style.margin.bottom)
                            }
                        }
                    }
                }
            }
        }

    }

    data class ItemStyle(
            var imageStyle: ImageStyle = ImageStyle(),
            var titleStyle: TextStyle = TextStyle(18f, R.color.gray_22, false, Rect(dp2px(15), dp2px(0), 0, 0)),
            var briefStyle: TextStyle = TextStyle(14f, R.color.gray_22, true, Rect(dp2px(0), dp2px(0), 0, 0)),
            var separatorStyle: SeparatorStyle = SeparatorStyle()
    )

    data class TextStyle(
            var textSize: Float = 12f,
            var textColor: Int = R.color.gray_66,
            var autoHide: Boolean = true,
            val margin: Rect = Rect(0, 0, 0, 0),
            var maxLine : Int = 1
    )

    data class ImageStyle(
            var size: Pair<Int, Int> = dp2px(40) to dp2px(40),
            val margin: Rect = Rect(dp2px(15), dp2px(15), 0, dp2px(15)),
            var visible: Boolean = true,
            var placeholder: Int = R.drawable.ic_placeholder
    )

    data class SeparatorStyle(
            var width: Int = R.dimen.dimen_02,
            var color: Int = R.color.gray_e3,
            var show: Boolean = true,
            val margin: Rect = Rect(dp2px(15), 0, dp2px(15), 0)
    )
}