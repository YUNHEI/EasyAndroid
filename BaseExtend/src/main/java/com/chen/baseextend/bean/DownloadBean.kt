package com.chen.baseextend.bean

import androidx.room.Entity
import com.chen.basemodule.basem.BaseRoomBean

/**
 *  Created by 86152 on 2020-09-04
 **/

@Entity(primaryKeys = ["id", "category"])
open class DownloadBean(val url: String, val start: Long) : BaseRoomBean()