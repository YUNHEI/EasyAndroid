package com.chen.basemodule.network.base

import android.os.Build
import com.chen.basemodule.BuildConfig
import com.chen.basemodule.allroot.RootRequest
import com.chen.basemodule.constant.BasePreference

/**
 * Created by chen on 2017/4/20.
 */
open class BaseRequest : RootRequest() {

    val appBuild: String = BuildConfig.VERSION_NAME

    val deviceType: Int = 1//"设备类型，0-未知， 1-Android，2-IOS, 3-其他"

    val appId: Int = 1//"appId，0-未知， 1-Android，2-IOS, 3-其他"

    val channelId: Int = 1//"appId，0-未知， 1-Android，2-IOS, 3-其他"

    val platformType: Int = 1//"appId，0-未知， 1-Android，2-IOS, 3-其他"

    val deviceName: String = Build.DEVICE

    val deviceId: String = BasePreference._OAID
}

