package com.chen.app.ui.coroutines

import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.base.fragment.GroupSSListFragment
import com.chen.baseextend.extend.startPage
import com.chen.basemodule.extend.listenClick
import com.chen.basemodule.extend.toastCus
import kotlinx.android.synthetic.main.fragment_coroutines_scope.*
import kotlinx.coroutines.*

@Launch
class GlobalScopeFragment : GroupSSListFragment() {

    override val wrapData by lazy {
        DATA {
            Group("协程作用域") {
                Item("不可取消协程") {
                    GlobalScope.launch {
                        while (true) {
                            withContext(Dispatchers.Main){
                                "不可取消协程".toastCus(R.mipmap.ic_load_suc, 500)
                            }
                            delay(1500)
                        }
                    }
                }
                Item("不可取消协程") {
                    MainScope().launch {
                        val job =  async(Dispatchers.Default) {
                            while (true) {
                                println("不可取消协程")
                                Thread.sleep(1000)
                            }
                        }
                        delay(10000)
                        job.cancelAndJoin()
                    }
                }
                Item("不可取消协程") {
                    scope.launch {
                        async(Dispatchers.Default) {
                            while (true) {
                                println("不可取消协程")
                                Thread.sleep(1000)
                            }
                        }

                    }
                }
                Item("协作取消协程") {

                    scope.launch {
                        async(Dispatchers.Default) {
                            while (isActive) {
                                println("协作取消协程")
                                Thread.sleep(1000)
                            }
                        }
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

    suspend fun canCancel() = suspendCancellableCoroutine<Unit>{
        it.invokeOnCancellation {

        }
    }
}