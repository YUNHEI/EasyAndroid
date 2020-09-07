package com.chen.app.ui.coroutines

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.GroupSSListFragment
import com.chen.baseextend.extend.startPage
import kotlinx.coroutines.*
import kotlin.math.pow
import kotlin.random.Random

@Launch
class CoroutinesFragment : GroupSSListFragment() {

    override val wrapData by lazy {
        DATA {
            Group("协程作用域") {
                Item("viewModelScope") {
                    startPage(ViewModelScopeFragment::class)
                }
                Item("GlobalScope") {
                    startPage(GlobalScopeFragment::class)
                }

                Item("协程性能测试") {
                    val length = Random.nextInt(5)
                    var start = System.currentTimeMillis()

                    printMain(length)
                    println("printMain 性能测试 ${System.currentTimeMillis() - start}")

                    printThread(length)
                    println("printThread 性能测试 ${System.currentTimeMillis() - start}")

                    scope.launch {
                        start = System.currentTimeMillis()
                        printIO(length)
                        println("printIO 性能测试 ${System.currentTimeMillis() - start}")

                        start = System.currentTimeMillis()
                        printDefault(length)
                        println("printDefault 性能测试 ${System.currentTimeMillis() - start}")

                        start = System.currentTimeMillis()
                        printIO2(length)
                        println("printIO2 性能测试 ${System.currentTimeMillis() - start}")

                        start = System.currentTimeMillis()
                        printIO3(length)
                        println("printIO3 性能测试 ${System.currentTimeMillis() - start}")

                        start = System.currentTimeMillis()
                        printDefault2(length)
                        println("printDefault2 性能测试 ${System.currentTimeMillis() - start}")
                    }
                }
            }
        }
    }

    override fun initAndObserve() {
        toolbar.run {
            center("协程")
            left(R.mipmap.ic_back) { activity?.finish() }
        }
    }

    fun printMain(length: Int): Int {
        var sum = 0
        for (i in 1..1000 * length) {
            for (j in 1..10000) {
                sum += (j * i.toFloat().pow(length) + j).toInt()
            }
        }
        return sum
    }

    fun printThread(length: Int): Int {
        var sum = 0
        for (i in 1..1000 * length) {
            Thread{
                for (j in 1..10000) {
                    sum += (j * i.toFloat().pow(length) + j).toInt()
                }
            }.start()
        }
        return sum
    }

    suspend fun printIO(length: Int): Int {
        var sum = 0
        coroutineScope {
            launch(Dispatchers.IO) {
                for (i in 1..1000 * length) {
                    launch(Dispatchers.IO) {
                        for (j in 1..10000) {
                            sum += (j * i.toFloat().pow(length) + j).toInt()
                        }
                    }
                }
            }.join()
        }
        return sum
    }

    suspend fun printIO2(length: Int): Int {
        var sum = 0
        coroutineScope {
            launch(Dispatchers.IO) {
                for (i in 1..1000 * length) {
                    for (j in 1..10000) {
                        sum += (j * i.toFloat().pow(length) + j).toInt()
                    }
                }
            }.join()
        }
        return sum
    }

    suspend fun printIO3(length: Int): Int {
        var sum = 0
        coroutineScope {
            launch(Dispatchers.IO) {
                for (i in 1..1000 * length) {
                    launch(Dispatchers.Default) {
                        for (j in 1..10000) {
                            sum += (j * i.toFloat().pow(length) + j).toInt()
                        }
                    }
                }
            }.join()
        }
        return sum
    }

    suspend fun printDefault(length: Int): Int {
        var sum = 0
        coroutineScope {
            launch(Dispatchers.Default) {
                for (i in 1..1000 * length) {
                    launch(Dispatchers.Default) {
                        for (j in 1..10000) {
                            sum += (j * i.toFloat().pow(length) + j).toInt()
                        }
                    }
                }
            }.join()
        }
        return sum
    }

    suspend fun printDefault2(length: Int): Int {
        var sum = 0
        coroutineScope {
            launch(Dispatchers.Default) {
                for (i in 1..1000 * length) {
                    launch(Dispatchers.IO) {
                        for (j in 1..10000) {
                            sum += (j * i.toFloat().pow(length) + j).toInt()
                        }
                    }
                }
            }.join()
        }
        return sum
    }

    override fun initClickListener() {
        listenItemClick { _, _, data, id, p, _ ->
            when (id) {
                else -> {
                }
            }
        }
    }
}