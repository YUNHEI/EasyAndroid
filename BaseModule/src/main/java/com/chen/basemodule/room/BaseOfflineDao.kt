package com.chen.basemodule.room

import androidx.sqlite.db.SimpleSQLiteQuery
import com.chen.basemodule.basem.BaseOfflineRoomBean
import com.chen.basemodule.room.DataBaseCategory.NOT_SHOW
import com.chen.basemodule.room.DataBaseCategory.SUFFIX_OFFLINE
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KClass

/**
 *  Created by chen on 2019/10/28
 **/
abstract class BaseOfflineDao<T : BaseOfflineRoomBean>(clazz: KClass<T>) : BaseCacheDao<T>(clazz) {

    suspend fun listOfflineToShow(category: String = ""): List<T> = suspendCoroutine {
        val cate = "%$category${SUFFIX_OFFLINE}"
        val orderBy: String = if (order.isNullOrEmpty()) "" else "ORDER BY $order DESC"
        it.resume(list(SimpleSQLiteQuery("SELECT * FROM $simpleName WHERE category LIKE '$cate' AND category NOT LIKE '$NOT_SHOW%' $orderBy")))
    }

    fun listOfflineToPost(category: String = "", retryTimes: Int = 0): List<T> {
        val cate = "%$category${SUFFIX_OFFLINE}"
        val orderBy: String = if (order.isNullOrEmpty()) "" else "ORDER BY $order ASC"
        return list(SimpleSQLiteQuery("SELECT * FROM $simpleName WHERE category LIKE '$cate' AND retryTimes < '$retryTimes' $orderBy"))
    }

}