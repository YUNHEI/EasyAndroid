package com.chen.app

import kotlinx.coroutines.*

fun main(args: Array<String>) {

    val size = 100
    var i = 1
    runBlocking {
        launch {
            while (i < size) {
                println("1: ${i++}")
                yield()
            }
        }
        launch {
            while (i < size) {
                println("2: ${i++}")
                yield()
            }
        }
    }
}