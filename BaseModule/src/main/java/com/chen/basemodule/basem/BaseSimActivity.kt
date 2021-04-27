package com.chen.basemodule.basem

import android.annotation.SuppressLint
import android.os.Bundle
import java.util.*

@SuppressLint("Registered")
open class BaseSimActivity : BaseActivity() {

    override val fragment: BaseFragment? by lazy { fragmentQueue.poll() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       configFragment()
    }

    open fun configFragment() {
        fragment?.run {
            setFragment(android.R.id.content, this)
        }
    }

}

val fragmentQueue by lazy { LinkedList<BaseFragment>() }
