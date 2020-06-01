package com.chen.baseextend.bean.infomation

import com.chen.basemodule.basem.BaseBean

class InfoItemBean : BaseBean() {

    lateinit var informationId: String
    var enterpriseId: String? = null
    var appId: String? = null
    var coverId: String? = null
    var remark: String? = null
    var title: String? = null
    var titleHl: String? = null
    var typeId: String? = null
    var views: Int = 0
    var commentCount: Int = 0
    var top: Int = 0
    var topDesc: String? = null
    var status = 2//1待发布， 2已发布  (未发布即收藏列表)
    var reviewStatus = 1//0待审核 1 审核通过 2审核未通过
    var statusDesc: String? = null
    var likes: Int = 0
    /**推荐   1-推荐 0-不推荐 */
    var recommend: Int = 0
    var recommendDesc: String? = null
    var typeName: String? = null
    var crtTime: String? = null
    var updTime: String? = null
    var source: String? = null
    var videoUrl: String? = null
    var content: String? = null
    var tag: String? = null
    var originalUrl: String? = null//原文链接
    var horizon: Int = 0
    var isLike: Boolean = false

    var videolength: Int = 0

    var nickname: String? = null
    var avatar: String? = null
    var description: String? = null
    var crtUserId: String? = null
    var hostStatus: Int = 0
    /**是否收藏 */
    var isCollect = false
    //是否是搜索
    var isSearch = false
    /**多图 链接 */
    var coverList: MutableList<String>? = null

    var infoFrom = 1//1内部， 2外部 (详情)
    var bizFrom = 1//1内部， 2外部 （列表）
    var analyzeStatus = 1//0解析中， 1解析成功 2 解析失败

    var infoType = 1//资讯类型（1-文章 2-视频 3-言论)
    var communityName = ""//社区 （招行）     原 ：authTagName

    var publishTime: String? = null//发布时间

    var shareCount: Int = 0//分享数

    var communityId: String? = null//社区Id

    var picture: String? = null//社区icon

    var recommendTime: String? = null//推荐时间

    /**分享状态（0：未分享   1：已分享） */
    var shareStatus: Int = 0

    /**社区类型（0：主题社区   1：员工社区） */
    var communityType: Int = 0

    var crtWay: Int = 2

}
