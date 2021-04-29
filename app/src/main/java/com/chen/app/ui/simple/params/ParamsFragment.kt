package com.chen.app.ui.simple.params

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.databinding.FragmentWeatherBinding
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.basem.argument.ArgFloat
import com.chen.basemodule.basem.argument.ArgInt
import com.chen.basemodule.basem.argument.ArgString
import com.chen.basemodule.basem.argument.ArgStringNull
import com.chen.basemodule.extend.doBinding

@Launch
class ParamsFragment : BaseSimpleFragment() {

    private val day by ArgString()

    private val date by ArgStringNull()

    private val week by ArgInt()

    private val wea by ArgFloat()

    override val binding by doBinding(FragmentWeatherBinding::inflate)

    override fun initAndObserve() {

        binding.run {
            Day.text = day
            Date.text = date
            Week.text = "$week"
            Wea.text = "$wea"
        }

    }
}