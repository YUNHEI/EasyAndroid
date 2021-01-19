package com.chen.baseextend.bean.news

data class VideoDetailInfo(
    val detail_video_large_image: DetailVideoLargeImage?,
    val direct_play: Int?,
    val group_flags: Int?,
    val show_pgc_subscribe: Int?,
    val video_id: String?,
    val video_preloading_flag: Int?,
    val video_type: Int?,
    val video_watch_count: Int?,
    val video_watching_count: Int?
)