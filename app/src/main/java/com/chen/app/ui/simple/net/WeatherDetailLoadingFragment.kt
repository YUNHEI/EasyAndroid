package com.chen.app.ui.simple.net

import androidx.lifecycle.LiveData
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.databinding.FragmentWeatherBinding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.network.base.BaseRequest

@Launch
class WeatherDetailLoadingFragment : BaseSimpleFragment() {

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
                        binding.let {
                            it.day.text = day
                            it.date.text = date
                            it.week.text = week
                            it.wea.text = wea
                        }
                    }
                }
            )
        }
    }
}