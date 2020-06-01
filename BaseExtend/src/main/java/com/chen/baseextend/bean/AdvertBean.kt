package com.chen.baseextend.bean

import androidx.room.Entity
import com.chen.basemodule.basem.BaseRoomBean

/**
 * Created by chen on 2018/10/12
 */
@Entity(primaryKeys = ["id", "category"])
data class AdvertBean(
        val id: Int = 0,
        val param: String? = null,
        val appId: Int? = null,
        val bannerType: Int? = null,
        val channelId: Int? = null,
        val desc: String? = null,
        val image: String? = null,
        val bg_pic: String? = null,
        val index: Int? = null,
        val jumpType: Int? = null,
        val name: String? = null,
        val status: Int? = null
) : BaseRoomBean()
