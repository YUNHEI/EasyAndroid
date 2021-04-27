package com.chen.basemodule.mlist

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.chen.basemodule.R
import com.chen.basemodule.databinding.ItemCommonFragmentBinding
import com.chen.basemodule.extend.createBinding
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.mlist.bean.ItemWrapBean

abstract class BaseFragmentDelegate(context: Context, val key: String?) :
    BaseMultiSourceDelegate<BaseItemFragmentBean>(context) {

//    override val layoutId = R.layout.item_common_fragment

    override val binding get() = createBinding(ItemCommonFragmentBinding::inflate)

    override fun createItemViewHolder(parent: ViewGroup): BaseItemViewHolder {
        val holder = super.createItemViewHolder(parent)
        holder.onAttachListener = { _, bean ->
            ((bean as? ItemWrapBean<*>)?.itemData as? BaseItemFragmentBean)?.run {
                (context as FragmentActivity).supportFragmentManager.findFragmentByTag(key) ?: run {
                    context.supportFragmentManager
                        .beginTransaction()
                        .add(
                            R.id._fragment_container,
                            (fragmentClass.java.getConstructor().newInstance() as Fragment).apply {
                                arguments = bd
                            },
                            key
                        )
                        .commitNowAllowingStateLoss()
                }
            }
        }
        return holder
    }

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: BaseItemFragmentBean?,
        position: Int,
        realP: Int
    ) {
    }

    override fun isThisDelegate(data: BaseItemFragmentBean, position: Int, realP: Int) =
        key == data.key

}