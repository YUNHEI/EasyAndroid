package com.chen.app.ui.coroutines

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.GroupSSListFragment
import com.chen.basemodule.extend.toastCus
import com.chen.basemodule.util.DateUtil
import kotlinx.coroutines.*

@Launch
class ViewModelScopeFragment : GroupSSListFragment() {

    override val wrapData by lazy {
        DATA {
            Group("协程作用域") {
                Item("可取消协程") {
                    lifecycleScope.launch {
                        while (true) {
                            "".toastCus(R.mipmap.ic_load_suc, 500)
                            delay(1500)
                        }
                    }
                }
                Item("不可取消协程") {
                    lifecycleScope.launch {
                        async(Dispatchers.Default) {
                            while (true) {
                                println("不可取消协程")
                                Thread.sleep(1000)
                            }
                        }
                    }
                }
                Item("协作取消协程") {
                    lifecycleScope.launch {
                        async(Dispatchers.Default) {
                            while (isActive) {
                                println("协作取消协程")
                                Thread.sleep(1000)
                            }
                        }
                    }
                }
                Item("延迟取消协程") {
                    lifecycleScope.launch {
                        val job = launch {
                            print(lifecycleScope)
                        }

                        delay(5000)
                        job.cancelAndJoin()
                    }
                }
            }
        }
    }

    override fun initAndObserve() {
        toolbar.run {
            center("viewModelScope")
            left(R.mipmap.ic_back) { activity?.finish() }
        }
    }

    override fun initClickListener() {

    }

    private suspend fun print(scope: CoroutineScope) = suspendCancellableCoroutine<Unit> {

        scope.launch {
            while (true) {
                println("协作取消协程")
                delay(1000)
            }
        }
        it.invokeOnCancellation {
            println("协程协作取消")
        }
    }

}