package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BasePageRequest

data class InfoListRequest(
        var informationType: String? = null,
        var title: String? = null,
        var informationId: String? = null,
        var id: String? = null,
        var searchType: Int = 0,
        var videoUrl: String? = "-1",//过滤视频
        var recommend: Int = 0,//推荐(1-不推荐 2-推荐 默认不推荐)

        //获取阅读历史文章等
        //    public int bizType;//1-资讯 2-视频 3-言论
        /**
         * 1-资讯 2-视频 3-言论
         */
        var infoType: Int? = null,

        /**
         * 分享状态（0：未分享   1：已分享）
         */
        var shareStatus: Int? = null,

        /**
         * 分享状态（0：未读   1：已读）
         */
        var isRead: Int? = null
) : BasePageRequest()
