package com.chen.app.ui.simple.net

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.databinding.FragmentWeatherBinding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.network.base.BaseRequest
import java.io.BufferedReader
import java.io.FileReader
import java.lang.NullPointerException
import kotlin.jvm.Throws

@Launch
class WeatherDetailFragment : BaseSimpleFragment() {

    override val binding by doBinding(FragmentWeatherBinding::inflate)

    override fun initAndObserve() {

        toolbar.run {
            center("网络请求")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        viewModel.run {
            requestData(
                {
                    weatherService.getWeatherDetail(BaseRequest())
                        .apply { if (!data.isNullOrEmpty()) status = 200 }
                },
                {
                    binding.let {v ->

                        it.data?.getOrNull(0)?.run {
                            v.day.text = day
                            v.date.text = date
                            v.week.text = week
                            v.wea.text = wea
                        }
                    }
                }
            )
        }
    }
}