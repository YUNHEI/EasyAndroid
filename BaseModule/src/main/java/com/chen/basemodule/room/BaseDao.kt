package com.chen.basemodule.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.chen.basemodule.basem.BaseRoomBean

/**
 *  Created by chen on 2019/10/28
 **/
interface BaseDao<T : BaseRoomBean> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(T: List<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(T: T): Long

    @Delete
    suspend fun delete(T: T): Int

    @Update
    suspend fun update(T: T): Int

    @RawQuery
    suspend fun abc(query: SupportSQLiteQuery): Int?

    @RawQuery
    fun deleteAll(query: SupportSQLiteQuery): Int?

    @RawQuery
    fun getOne(query: SupportSQLiteQuery): T?

    @RawQuery
    fun list(query: SupportSQLiteQuery): MutableList<T>
}