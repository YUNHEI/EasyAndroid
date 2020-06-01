package com.chen.baseextend.bean.comment

import com.chen.basemodule.basem.BaseBean

import java.io.Serializable

data class CommentBean(
        var enterpriseId: String? = null,
        var informationId: String? = null,
        var reply: String? = null,
        var type: String? = null,
        var commentator: String? = null,
        var crtTime: String? = null,
        var crtUserId: String? = null,
        var crtUserName: String? = null,
        var replyCount: Int = 0,
        var eid: String? = null,
        var id: String,
        var likes: Int? = null,
        var replyCommentId: String? = null,
        var belongCommentId: String? = null,
        var updTime: String? = null,
        var updUserId: String? = null,
        var updUserName: String? = null,
        var avatar: String? = null,
        var toCommentId: String? = null,
        var toNickName: String? = null,
        var toComment: String? = null,
        var description: String? = null,
        var hostStatus: Int = 0,
        var communityId: String? = null,
        var communityName: String? = null,
        var communityIcon: String? = null
) : BaseBean(), Serializable
