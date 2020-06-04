package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

data class WeatherBean(
    val week:String,
    val day:String,
    val date:String,
    val wea:String
) :BaseBean()