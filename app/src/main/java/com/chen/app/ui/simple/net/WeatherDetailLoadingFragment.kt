package com.chen.app.ui.simple.net

import androidx.lifecycle.LiveData
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.databinding.Fragment1Binding
import com.chen.app.databinding.FragmentWeatherBinding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.network.base.BaseRequest
import kotlinx.android.synthetic.main.fragment_weather.*

@Launch
class WeatherDetailLoadingFragment : BaseSimpleFragment() {

//    override val contentLayoutId = R.layout.fragment_weather
    override val binding by doBinding(FragmentWeatherBinding::inflate)
    override fun initAndObserve() {

        toolbar.run {
            center("网络请求带加载状态")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        startLoadData()
    }

    override fun loadData(): LiveData<*>? {
        return viewModel.run {
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