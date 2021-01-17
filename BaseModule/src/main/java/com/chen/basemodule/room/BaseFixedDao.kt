package com.chen.basemodule.room

import androidx.sqlite.db.SupportSQLiteQuery
import com.chen.basemodule.basem.BaseRoomBean
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 *  Created by chen on 2019/10/28
 **/
abstract class BaseFixedDao<T : BaseRoomBean> : BaseDao<T> {

    abstract val data: MutableList<T>

    suspend fun listFixedData(): MutableList<T> = suspendCoroutine {
        it.resume(data)
    }

    override suspend fun addAll(T: List<T>) = listOf<Long>()

    override suspend fun delete(T: T) = 0

    override suspend fun update(T: T) = 1

    override fun deleteAll(query: SupportSQLiteQuery) = 0

    override fun getOne(query: SupportSQLiteQuery) = null

    override fun list(query: SupportSQLiteQuery): MutableList<T> = mutableListOf()

    override suspend fun add(T: T) = 0L
}