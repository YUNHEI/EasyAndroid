package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/12/2
 **/
data class AppealBean(
        val acceptTaskId: String? = null,//接单Id
        val accusedHaedImg: String? = null,//被申述人头像
        val accusedNickName: String? = null,//被申述人名称
        val accusedUserId: String? = null,//被申述人用户id
        val appealHeadImg: String? = null,//申述人头像
        val appealNickName: String? = null,//申述人名称
        val appealUserId: String? = null,//申述人用户id
        val createTime: String? = null,//申述创建时间
        val fstatus: Int? = null,//1.未处理 2.处理中 3.申述成功 4.申述失败
        val ftype: Int? = null,//1.驳回申诉 2.乱提交申述 3.咨询举报
        val id: String? = null,//申述id
        val taskId: String? = null,//任务id
        val content: String? = null,//发单人驳回理由
        val msg: String? = null,//申诉理由
        val remark: String? = null,//客服驳回理由
        val title: String? = null//任务标题
) : BaseBean()
