package com.chen.baseextend.room

import androidx.room.*
import com.chen.baseextend.bean.AdvertBean
import com.chen.basemodule.room.BaseCacheDao


/**
 *  Created by chen on 2019/10/9
 **/
@Dao
abstract class AdvertBeanDao : BaseCacheDao<AdvertBean>(AdvertBean::class)