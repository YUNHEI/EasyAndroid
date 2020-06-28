package com.chen.basemodule.basem

import android.annotation.SuppressLint
import android.os.Bundle
import java.util.*

@SuppressLint("Registered")
open class BaseSimActivity : BaseActivity() {

    override val fragment: BaseFragment? by lazy { fragmentQueue.poll() }

    override val contentLayoutId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragment?.run {
            setFragment(android.R.id.content, this)
        }
    }

}

val fragmentQueue by lazy { LinkedList<BaseFragment>() }
