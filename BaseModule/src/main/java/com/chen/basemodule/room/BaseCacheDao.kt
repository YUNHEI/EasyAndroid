package com.chen.basemodule.room

import androidx.sqlite.db.SimpleSQLiteQuery
import com.chen.basemodule.room.DataBaseCategory.SUFFIX_CACHE
import com.chen.basemodule.basem.BaseRoomBean
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KClass

/**
 *  Created by chen on 2019/10/28
 **/
abstract class BaseCacheDao<T : BaseRoomBean>(private val clazz: KClass<T>) : BaseDao<T> {

    val simpleName by lazy { clazz.simpleName }

    open var order: String? = null

    open suspend fun listCache(category: String = ""): MutableList<T> = suspendCoroutine{
        val cate = "%$category${SUFFIX_CACHE}"
        val orderBy: String = if (order.isNullOrEmpty()) "" else "ORDER BY $order ASC"
        it.resume(list(SimpleSQLiteQuery("SELECT * FROM $simpleName WHERE category LIKE '$cate' $orderBy")))
    }

    suspend fun deleteAll(category: String = "") = suspendCoroutine<Unit> {
        deleteAll(SimpleSQLiteQuery("DELETE FROM $simpleName WHERE category = '$category'"))
        it.resume(Unit)
    }
}