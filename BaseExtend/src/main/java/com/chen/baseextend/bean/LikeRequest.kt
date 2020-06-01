package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BaseRequest

data class LikeRequest(
        val bizId: String//对象Id
        , val bizType: Int//业务类型（1为资讯 2为评论 3为活动）
        , val status: Int//1-点赞 0-取消点赞
) : BaseRequest()
