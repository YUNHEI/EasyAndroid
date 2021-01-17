package com.chen.basemodule.mlist

import android.content.Context
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.extend.indexToPosition
import com.chen.basemodule.mlist.bean.DataWrapBean
import com.chen.basemodule.mlist.bean.GroupWrapBean

/**
 *  Created by 86152 on 2021-01-15
 **/
class BaseMultiGroupAdapter<P : RootBean, C : RootBean>(context: Context) :
    BaseMultiAdapter<DataWrapBean>(context) {

    var columns = 1

    //获取第index组大小
    fun getGroupSizeByIndex(index: Int): Int {
        getGroupByIndex(index)?.run {
            return getGroupSize(this)
        }
        return 0
    }

    //获取第index个组
    fun getGroupByIndex(index: Int): GroupWrapBean<P, C>? = data
        .filterIsInstance<GroupWrapBean<P, C>>()
        .getOrNull(index)

    //获取group的size
    fun getGroupSize(groupWrapData: GroupWrapBean<P, C>): Int {
        val p = originData.indexOf(groupWrapData)
        if (p.inc() >= originData.size) return 0
        for (i in p.inc() until originData.size) {
            if (originData[i] is GroupWrapBean<*, *>) {
                return i - p - 1
            }
        }
        return originData.size - p - 1
    }

    /**
     * position 对应的group 和 itemIndex
     */
    fun positionToGroupIndex(position: Int): Pair<Int, Int> {
        var groupIndex = -1
        var itemIndex = -1
        for (i in position downTo 0) {
            getDataByPosition(i).run {
                when {
                    this == null -> return -1 to -1
                    groupIndex < 0 && this !is GroupWrapBean<*, *> -> itemIndex++
                    this is GroupWrapBean<*, *> -> groupIndex++
                    else -> {
                    }
                }
            }
        }
        return groupIndex to itemIndex
    }

    //获取position对应的组
    fun getGroupByPosition(position: Int): GroupWrapBean<P, C>? {
        for (i in position downTo 0) {
            getDataByPosition(i).let { bean ->
                if (bean == null) return null
                if (bean is GroupWrapBean<*, *>) return bean as GroupWrapBean<P, C>
            }
        }
        return null
    }

    //获取第index个组的position
    fun getGroupPositionByIndex(index: Int): Int =
        data.indexToPosition(index) { it is GroupWrapBean<*, *> }.first


    fun getColumnByPosition(p: Int): Int {
        getDataByPosition(p)?.run {
            if (this !is GroupWrapBean<*, *>) {
                for (i in p - 1 downTo 0) {
                    if (getDataByPosition(i) is GroupWrapBean<*, *>) return (p - i - 1).rem(columns)
                }
            }
        }
        return -1
    }
}