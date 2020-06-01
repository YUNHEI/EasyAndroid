package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/6/18
 **/
data class MsgCommentBean(
        val communityIcon: String,
        val communityId: String,
        val communityName: String,
        val crtTime: String,
        val crtUserId: String,
        val id: String,
        var information: MsgInformation?,
        val informationId: String,
        val isLike: Boolean,
        val reply: String,
        val replyCount: Int,
        val toNickName: String?,
        val toUserId: String,
        val type: Int,
        val avatar: String?,
        val toComment: String?,
        val belongCommentId: String?,
        val crtUserName: String?
) : BaseBean()