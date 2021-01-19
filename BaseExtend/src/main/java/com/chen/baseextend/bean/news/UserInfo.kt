package com.chen.baseextend.bean.news

data class UserInfo(
    val avatar_url: String?,
    val description: String?,
    val follow: Boolean?,
    val follower_count: Int?,
    val living_count: Int?,
    val name: String?,
    val schema: String?,
    val user_auth_info: String?,
    val user_id: Long?,
    val user_verified: Boolean?,
    val verified_content: String?
)