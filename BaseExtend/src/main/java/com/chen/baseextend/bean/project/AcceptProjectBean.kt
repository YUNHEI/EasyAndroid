package com.chen.baseextend.bean.project

import androidx.room.Entity
import com.chen.basemodule.basem.BaseRoomBean
import java.math.BigDecimal

/**
 *  Created by chen on 2019/8/23
 **/
@Entity(primaryKeys = ["acceptTaskId", "category"])
data class AcceptProjectBean(
        var acceptTaskId: String = (0..9999999999).random().toString() + System.currentTimeMillis(),//接单id ,
        val completeTime: String? = null,//完成时间 ,
        val createTime: String? = null,//接单时间 ,
        val taskTypeDesc: String? = null,//任务类型 ,
        val itemDesc: String? = null,//项目名称 ,
        val createrHeadImg: String? = null,//发单人头像url ,
        val createrUserId: String? = null,//发单人头像url ,
        val status: Int? = null,// 接单状态 ,
        val taskId: String? = null,//任务id
        val taskTypeId: Int? = null,//任务类型id
        val title: String? = null,//任务标题
        val unitprice: BigDecimal = BigDecimal(0)//任务单价
) : BaseRoomBean()