package com.chen.baseextend.room

import androidx.room.*
import com.chen.baseextend.bean.project.ProjectBean
import com.chen.basemodule.room.BaseOfflineDao


/**
 *  Created by chen on 2019/10/9
 **/
@Dao
abstract class ProjectBeanDao : BaseOfflineDao<ProjectBean>(ProjectBean::class) {

    override var order: String? = "crtTime"

}