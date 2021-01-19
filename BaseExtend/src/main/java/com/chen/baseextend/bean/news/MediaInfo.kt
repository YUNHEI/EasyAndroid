package com.chen.baseextend.bean.news

data class MediaInfo(
    val avatar_url: String?,
    val follow: Boolean?,
    val is_star_user: Boolean?,
    val media_id: Long?,
    val name: String?,
    val recommend_reason: String?,
    val recommend_type: Int?,
    val user_id: Long?,
    val user_verified: Boolean?,
    val verified_content: String?
)