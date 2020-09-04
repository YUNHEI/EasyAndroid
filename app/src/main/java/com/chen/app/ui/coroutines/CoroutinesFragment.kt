package com.chen.app.ui.coroutines

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.GroupSSListFragment
import com.chen.baseextend.extend.startPage

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
            }
        }
    }

    override fun initAndObserve() {
        toolbar.run {
            center("协程")
            left(R.mipmap.ic_back) { activity?.finish() }
        }
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