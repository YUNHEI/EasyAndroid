package com.chen.basemodule.extend

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 *  Created by 86152 on 2020-09-09
 **/

fun LifecycleOwner.defaultLaunch(
    context: CoroutineContext = Dispatchers.Default,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launch(context, start, block)