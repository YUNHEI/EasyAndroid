//package com.chen.baseextend.ui.delegate
//
//import android.content.Context
//import android.view.View
//import com.chen.baseextend.R
//import com.chen.baseextend.bean.project.ProjectBean
//import com.chen.baseextend.databinding.ItemProjectCardBinding
//import com.chen.baseextend.extend.fenToYuan
//import com.chen.basemodule.extend.load
//import com.chen.basemodule.mlist.BaseItemViewHolder
//import kotlinx.android.synthetic.main.item_project_card.view.*
//
///**
// *  Created by chen on 2019/11/26
// **/
//class ProjectCardDelegate(context: Context) : ProjectDelegate(context) {
//
////    override val layoutId = R.layout.item_project_card
//
//
//
//    override fun bindData(viewHolder: BaseItemViewHolder, data: ProjectBean?, position: Int, realP: Int) {
//        viewHolder.itemView.run {
//            data?.run {
//                _title.setText(title, realP)
//                _price.text = unitprice.fenToYuan()
//                _avatar.load(avatar)
//                _type.text = typeDesc
//                _app.text = itemDesc
//                _remaining.text = "${completeNum}人已赚丨剩余$remainNum"
//                _ensure.visibility = if (hasBond == 1) View.VISIBLE else View.GONE
//                _top.visibility = if (isTop == 1) View.VISIBLE else View.GONE
//                _rec.visibility = if (isRecommend == 1) View.VISIBLE else View.GONE
//            }
//        }
//    }
//
//    override fun isThisDelegate(data: ProjectBean, position: Int, realP: Int) = columns > 1
//}