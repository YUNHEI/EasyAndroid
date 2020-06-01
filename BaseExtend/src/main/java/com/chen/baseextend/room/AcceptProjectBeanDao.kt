package com.chen.baseextend.room

import androidx.room.Dao
import com.chen.baseextend.bean.project.AcceptProjectBean
import com.chen.basemodule.room.BaseCacheDao


/**
 *  Created by chen on 2019/10/9
 **/
@Dao
abstract class AcceptProjectBeanDao : BaseCacheDao<AcceptProjectBean>(AcceptProjectBean::class) {

    override var order: String? = "createTime"

}