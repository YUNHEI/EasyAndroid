package com.chen.app.ui.simple.params

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.basem.argument.ArgFloat
import com.chen.basemodule.basem.argument.ArgInt
import com.chen.basemodule.basem.argument.ArgString
import com.chen.basemodule.basem.argument.ArgStringNull
import kotlinx.android.synthetic.main.fragment_weather.*

@Launch
class ParamsFragment :BaseSimpleFragment(){

    private val day by ArgString()

    private val date by ArgStringNull()

    private val week by ArgInt()

    private val wea by ArgFloat()


    override val contentLayoutId = R.layout.fragment_weather


    override fun initAndObserve() {

        _day.text = day
        _date.text = date
        _week.text = "$week"
        _wea.text = "$wea"

    }
}