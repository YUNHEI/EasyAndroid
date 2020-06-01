package com.chen.baseextend.bean.activity

import com.chen.basemodule.basem.BaseBean

data class SignInStatusListBean(
        val completeOrderNum:Int = 0,
        val clockDetailList:MutableList<SignInDayBean> = mutableListOf(),
        val isAttend:Int = 0//是否已签到
):BaseBean()