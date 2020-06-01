package com.chen.baseextend.bean.project

import androidx.room.Entity
import androidx.room.Ignore
import com.chen.basemodule.basem.BaseOfflineRoomBean
import java.math.BigDecimal

/**
 *  Created by chen on 2019/7/26
 **/
@Entity(primaryKeys = ["taskId", "category"])
data class ProjectBean(
        var taskId: String = (0..9999999999).random().toString() + System.currentTimeMillis(),//任务id
        var id: String? = null, //任务id 任务详情，提交任务用
        var avatar: String? = null, //发布人头像
        var itemDesc: String? = null, //项目名称
        var title: String? = null, //标题
        var typeId: String? = null, //任务类型id
        var typeDesc: String? = null, //任务类型描述
        var taskTypeDesc: String? = null, //任务类型描述
        var supportDevice: Int? = 1, //支持设备类型 1.全部 2.安卓 3.苹果
        var description: String? = null, //任务说明
        var finishTime: Int? = 0, //接单完成时间，单位h，0不限时
        var verifyTime: Int? = 0, //审核时间，单位h，0不限时
        var acceptCount: Int? = 1, //做单次数 1.每人一次 2.每人三次 3.每日一次
        var unitprice: BigDecimal = BigDecimal(0), //单价
        var taskNum: Int = 0, //任务数量
        var userId: String? = null, //创建人id
        var channelDesc: String? = null, //渠道名称
        var appDesc: String? = null, //应用名称
        var createTime: String? = null, //创建时间
        var subEndTime: String? = null, //截止日期时间
        var channelId: Int? = 0, //渠道id
        var appId: Int? = 0, //应用id
        var hasBond: Int = 0, //是否有保证金
        var nickname: String? = null, //用户昵称
        var remainNum: Int = 0, //任务剩余数量
        var showStatus: Int = 0, //任务状态
        var status: Int? = null,//任务状态 1.待审核 2.审核中 3.审核失败 4.拒绝审核 5.上架 6.暂停 7.下架 8.下架验证 9.冻结 10.离线发送中 11.离线发送失败
        var paded: Int = 0, //是否已支付，修改受限制
        var isCollect: Boolean = false, //是否已收藏
        @Ignore
        var step: List<ProjectStepBean>? = null,//任务步骤

        var totalNum: Int? = null,//任务数量

        var underwayNum: Int = 0,//进行中任务数量

        var completeNum: Int = 0,//任务完成数量

        var waitVerifyNum: Int = 0,//审核中任务数量

        var appealNum: Int = 0,//申诉数量

        var taskTypeIcon: String? = null,//任务类型图标

        var recommendEndTime: String? = null,//推荐截止时间

        var topEndTime: String? = null,//置顶截止时间

        var errorMsg: String? = null,//驳回理由

        var crtTime: String? = null//创建时间
) : BaseOfflineRoomBean() {
    //是否推荐
    var isRecommend: Int = 0

    fun isIsRecommend(): Int {
        return isRecommend
    }

    fun setIsRecommend(vaule: Int) {
        isRecommend = vaule
    }

    //是否置顶
    var isTop: Int = 0

    fun isIsTop(): Int {
        return isTop
    }

    fun setIsTop(vaule: Int) {
        isTop = vaule
    }

    //是否是发布人
    var isMaster: Int = 0

    fun isIsMaster(): Int {
        return isMaster
    }

    fun setIsMaster(vaule: Int) {
        isMaster = vaule
    }

}