package com.chen.app.ui.coroutines

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.SingleGroupSimpleListFragment
import com.chen.baseextend.extend.startPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

@Launch
class CoroutinesFragment : SingleGroupSimpleListFragment() {


    override fun initAndObserve() {

        toolbar.run {
            center("协程")
            left(R.mipmap.ic_back) { activity?.finish() }
        }
    }

    override val wrapData by lazy {
        DATA {
            Group("协程作用域") {
                Item("viewModelScope") {
                    startPage(ViewModelScopeFragment::class)
                }
                Item("GlobalScope") {
                    startPage(GlobalScopeFragment::class)
                }

                Item("协程线程切换") {
                    val length = Random.nextInt(7)
                    var start = System.currentTimeMillis()
                    var sum = 0
                    val size = (10f.pow(length)).toInt()
                    for (i in 0..size) {
                        sum = ln(ln(sqrt(sqrt(i.toFloat()).pow(5)))).toInt()
                    }
                    println("main 性能测试 sum:${sum} total:${System.currentTimeMillis() - start} ${(System.currentTimeMillis() - start).toFloat() / size}")
                    sum = 0
                    for (i in 0..size) {
                        lifecycleScope.launch(Dispatchers.Unconfined) {
                            sum = ln(ln(sqrt(sqrt(i.toFloat()).pow(5)))).toInt()
                        }
                    }
                    println("launch 性能测试 size:${size} total:${System.currentTimeMillis() - start} ${(System.currentTimeMillis() - start).toFloat() / size}")
                }
                Item("协程性能测试") {
                    val length = Random.nextInt(5)
                    var start = System.currentTimeMillis()

                    printMain(length)
                    println("printMain 性能测试 ${System.currentTimeMillis() - start}")

                    printThread(length)
                    println("printThread 性能测试 ${System.currentTimeMillis() - start}")

                    lifecycleScope.launch {
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

                Item("协程性能测试2") {
                    val length = Random.nextInt(7)
                    var start = System.currentTimeMillis()
                    var sum = 1

                    for (i in 1..(10f.pow(length).toInt())) {
                        sum += (i.toFloat().pow(length)).toInt()
                        sum = -sum
                    }

                    println("main 性能测试 length :${length} sum: ${sum}  time: ${System.currentTimeMillis() - start}")

                    sum = 1

                    lifecycleScope.launch(Dispatchers.Default) {
                        start = System.currentTimeMillis()

                        for (i in 1..(10f.pow(length).toInt())) {
                            launch(Dispatchers.Default) {
                                sum += (i.toFloat().pow(length)).toInt()
                                sum = -sum
                            }.join()
                        }

                        println("Default 性能测试 length :${length} sum: ${sum}  time: ${System.currentTimeMillis() - start}")
                    }
                }
            }
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
            Thread {
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