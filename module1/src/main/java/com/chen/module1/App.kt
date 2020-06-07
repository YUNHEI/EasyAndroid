package com.chen.module1

import android.app.Application
import com.chen.basemodule.BaseModuleLoad.init


/**
 * Created by chen on 2019/5/7
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        init(this, "com.chen.module1")
    }
}