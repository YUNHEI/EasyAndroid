package com.chen.app.ui.simple.net

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.network.base.BaseRequest
import kotlinx.android.synthetic.main.fragment_weather.*

@Launch
class WeatherDetailFragment : BaseSimpleFragment(){

    override val contentLayoutId = R.layout.fragment_weather

    override fun initAndObserve() {

        toolbar.run{
            center("网络请求")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        viewModel.run {
            requestData(
                    { weatherService.getWeatherDetail(BaseRequest()).apply { if (!data.isNullOrEmpty()) status = 200 } },
                    {
                        it.data?.getOrNull(0)?.run {
                            _day.text = day
                            _date.text = date
                            _week.text = week
                            _wea.text = wea
                        }
                    }
            )
        }
    }
}