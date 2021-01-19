package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

/**
 * 笑话
 *  Created by chen on 2019/9/5
 **/
data class JokeBean(
        val comment: String?,
        val down: String?,
        val forward: String?,
        val header: String?,
        val images: Any?,
        val name: String?,
        val passtime: String?,
        val sid: String?,
        val text: String?,
        val thumbnail: String?,
        val top_comments_content: String?,
        val top_comments_header: String?,
        val top_comments_name: String?,
        val top_comments_uid: String?,
        val top_comments_voiceuri: String?,
        val type: String?,
        val uid: String?,
        val up: String?,
        val video: String?
) : BaseBean()