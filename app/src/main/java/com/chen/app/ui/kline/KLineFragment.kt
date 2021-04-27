package com.chen.app.ui.kline

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Launch
import com.alibaba.android.arouter.facade.enums.SwipeType
import com.chen.app.databinding.FragmentKLineExampleBinding
import com.chen.app.k_line.DataRequest
import com.chen.app.k_line.formatter.DateFormatter
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.defaultLaunch
import com.chen.basemodule.extend.doBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Launch(swipeType = SwipeType.DISABLE)
class KLineFragment : BaseSimpleFragment() {

    override val binding by doBinding(FragmentKLineExampleBinding::inflate)

    private val mAdapter by lazy {  KChartAdapter() }

    override fun initAndObserve() {

        binding.kchartView.run {
            adapter = mAdapter
            dateTimeFormatter = DateFormatter()
            setGridRows(4)
            setGridColumns(4)
            setOnSelectedChangedListener { view, point, index ->
                val data: KLineEntity = point as KLineEntity
                Log.i("onSelectedChanged", "index:" + index + " closePrice:" + data.closePrice)
            }
            showLoading()
        }

        defaultLaunch {

            val data: List<KLineEntity> = DataRequest.getALL(activity)

            withContext(Dispatchers.Main) {
                mAdapter.addFooterData(data)
                binding.kchartView.startAnimation()
                binding.kchartView.refreshEnd()
            }
        }
    }

}