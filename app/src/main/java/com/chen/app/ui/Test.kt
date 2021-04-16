@file:Suppress("WarningOnMainUnusedParameterMigration")

package com.chen.app.ui

import java.util.*

/**
 *  Created by 86152 on 2020-07-05
 **/

fun main(arg: Array<String>) {


    fun search(nums: IntArray, target: Int): Int {
        if (nums.isEmpty()) return -1
        if (nums.first() == target) return 0
        if (nums.last() == target) return nums.size - 1
        var low = 0
        var high = nums.size

        while (high > low) {
            var mid = (high + low).shr(1)
            if (mid == low) return -1
            if (nums[mid] == target) return mid
            when{
                nums[mid] > target && mid - low > 1 && nums[low] > target -> {

                }
            }
        }
        return -1
    }

    println(Date())
    val a = search(intArrayOf(7, 0, 1, 2, 3, 4, 5, 6), 0)
    println(Date())
    println(a)
}

class Land(private val key: String, private val leftLand: Land?, private val topLand: Land?, map: MutableMap<String, Land>) {

    var set: MutableSet<String>

    init {
        set = mutableSetOf(key)

        leftLand?.let {
            it.set.add(key)
            this.set = it.set
        }

        topLand?.let { l ->
            if (l.set !== set) {
                l.set.addAll(set)
                set.forEach { map[it]?.set = l.set }
                set = l.set
            }
        }
    }

    fun count(): Int {
        return set.size
    }
}

