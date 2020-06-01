package com.chen.baseextend.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chen.baseextend.bean.AdvertBean
import com.chen.baseextend.bean.project.AcceptProjectBean
import com.chen.baseextend.bean.project.ItemTypeBean
import com.chen.baseextend.bean.project.ProjectBean

/**
 *  Created by chen on 2019/10/9
 **/
@Database(entities = [ProjectBean::class, ItemTypeBean::class, AdvertBean::class, AcceptProjectBean::class], version = 53)
@TypeConverters(Converters::class)
abstract class RoomConfig : RoomDatabase() {

    abstract fun projectBeanDao(): ProjectBeanDao

    abstract fun advertBeanDao(): AdvertBeanDao

    abstract fun itemTypeBeanDao(): ItemTypeBeanDao

    abstract fun acceptProjectBeanDao(): AcceptProjectBeanDao
}