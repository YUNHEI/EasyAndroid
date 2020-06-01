package com.chen.basemodule.basem

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import com.chen.basemodule.R
import com.chen.basemodule.basem.FragmentCache.preFragment

@SuppressLint("Registered")
open class BaseSimActivity : BaseActivity() {

    private var paramsIsPkg = false

    override val fragment: BaseFragment?
        get() {
            if (preFragment != null) {
                val fg = preFragment
                paramsIsPkg = true
                preFragment = null
                return fg
            }
            return null
        }

    override val contentLayoutId = R.layout.base_simple_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragment?.run {
            if (!paramsIsPkg) arguments = intent.extras
            setFragment(R.id._root_container, this)
        }

    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return if (fragment is BaseFragment) {
            (fragment as BaseFragment).onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event)
        } else {
            super.onKeyUp(keyCode, event)
        }
    }
}

//fragment 预加载
object FragmentCache {
    var preFragment: BaseFragment? = null
}
