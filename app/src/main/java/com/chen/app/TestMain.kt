package com.chen.app

import java.lang.Exception

fun main(args: Array<String>) {

    fun multiply(num1: String, num2: String): String {
        if (num1 == "0" || num2 == "0") return "0"
        val l = num1.length + num2.length
        val result = IntArray(l) { 0 }
        val num0 = '0'.toInt()

        for (idx1 in num1.length - 1 downTo 0) {
            for (idx2 in num2.length - 1 downTo 0) {
                val curr = idx1 + idx2 + 1
                val next = curr - 1
                val value =
                    result[curr] + ((num1[idx1].toInt() - num0) * (num2[idx2].toInt() - num0))
                result[curr] = value % 10
                result[next] = result[next] + value / 10

            }
        }

        return result.filterIndexed { index, i -> index != 0 || i != 0 }.joinToString("")
    }


//    println(System.currentTimeMillis())
////    val a = multiply("9", "99")
//    println(System.currentTimeMillis())
//    val count = 4
//    val result = DoubleArray(count) { 0.0 }
//    val a = step(count, result)
//
//    println(a)

//    Thread(PriNum()).start()
//    Thread(PriNum()).start()

    val f = Child()
    (f as Father).eat()
    f.eat()
    println(f.age)
    println((f as Father).age)
}

open class Father() {
    open val age: Int = 40
    open fun eat() {
        println("爸爸在吃饭")
    }
}

class Child() : Father() {
    override val age: Int = 18
    override fun eat() {
        println("儿子在吃饭")
        super.eat()
    }
}

var i = 1

val lock = Object()

fun step(total: Int, cache: DoubleArray): Double {
    if (total < 0) return 0.toDouble()
    else if (total <= 1) return 1.toDouble()
    else if (total == 2) return 2.toDouble()
    else if (cache[total - 1] > 0) return cache[total - 1]
    else {
        cache[total - 1] = step(total - 1, cache) + step(total - 2, cache)
        return cache[total - 1]
    }
}

class PriNum : Runnable {

    override fun run() {
//        synchronized(lock) {
        while (i <= 100) {
            try {
                println("线程：${Thread.currentThread().name}, 打印 : ${i++}")
//                    lock.notifyAll()
//                    lock.wait()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
//        }
    }

}

//fun priArray(arrays: Array<IntArray>) {
//
//    var x = 0
//
//    var y = 0
//
//    val visited = Array(arrays.size) { IntArray(arrays[0].size) { 0 } }
//
//    while (true) {
//
//        while (true) {
//            if (visited[y][x])
//        }
//    }
//
//}