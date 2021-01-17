package com.chen.app.ui.kline

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Launch
import com.alibaba.android.arouter.facade.enums.SwipeType
import com.chen.app.R
import com.chen.app.k_line.DataRequest
import com.chen.app.k_line.formatter.DateFormatter
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.defaultLaunch
import kotlinx.android.synthetic.main.fragment_k_line_example.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Launch(swipeType = SwipeType.DISABLE)
class KLineFragment :BaseSimpleFragment(){

    override val contentLayoutId = R.layout.fragment_k_line_example
    override fun initAndObserve() {

        val mAdapter = KChartAdapter()
        kchart_view.adapter = mAdapter
        kchart_view.dateTimeFormatter = DateFormatter()
        kchart_view.setGridRows(4)
        kchart_view.setGridColumns(4)
        kchart_view.setOnSelectedChangedListener { view, point, index ->
            val data: KLineEntity = point as KLineEntity
            Log.i("onSelectedChanged", "index:" + index + " closePrice:" + data.closePrice)
        }


        kchart_view.showLoading()

        defaultLaunch {

            val data: List<KLineEntity> = DataRequest.getALL(activity)

            withContext(Dispatchers.Main) {
                mAdapter.addFooterData(data)
                kchart_view.startAnimation()
                kchart_view.refreshEnd()
            }
        }
    }

}