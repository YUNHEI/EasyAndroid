package com.chen.app.ui.simple.toolbar.pager

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BasePageFragment
import com.chen.baseextend.bean.StringBean
import com.chen.basemodule.basem.BaseFragment
import com.chen.basemodule.network.base.BaseResponse
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @author chen
 * @date 2019/3/27
 */
@Launch
class ToolbarExtend5Fragment : BasePageFragment<StringBean>() {

    /**0在extend left 1在center  left 2、extend center 3、center center*/
    override val indicateType = 2

    override fun getTitle(data: StringBean): String = data.title

    override fun initAndObserve() {

        toolbar.run {
            center("高级扩展5")
            left(R.mipmap.ic_back) { activity?.finish() }
            divider(0)
        }

        startLoadData()

    }

    override fun getFragmentPage(data: StringBean, position: Int): BaseFragment {
        return SimplePageFragment().apply {
            arguments = Bundle().apply {
                putString("title", data.title)
            }
        }
    }

    override fun loadData(): LiveData<*>? {
        return viewModel.run {
            requestData(
                    { listItems() },
                    {
                        it.data?.run {
                            items = this
                        }
                    }
            )
        }
    }

    //模拟获取网络数据
    private suspend fun listItems(): BaseResponse<MutableList<StringBean>> = suspendCoroutine {
        it.resume(BaseResponse(mutableListOf("1", "2", "3", "4").map { StringBean(it) }.toMutableList(), 200))
    }
}