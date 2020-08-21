package com.chen.basemodule

import android.app.Application
import android.content.Context

import com.alibaba.android.arouter.launcher.ARouter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.chen.basemodule.constant.BasePreference

import devliving.online.securedpreferencestore.DefaultRecoveryHandler
import devliving.online.securedpreferencestore.SecuredPreferenceStore

/**
 * Created by chen on 2019/4/29
 */
object BaseModuleLoad {


    var app: Application? = null

    fun init(app: Application, storeFileName: String) {

        this.app = app

        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }

        ARouter.init(app)

        initSecureSharePreference(storeFileName)

        LiveEventBus.config()
                .setContext(app)
                .lifecycleObserverAlwaysActive(true)
                .autoClear(true)
    }

    val context: Context
        get() {
            if (app == null) {
                throw RuntimeException("继承BaseModule 需要在Application中 调用初始化方法init(Application app)")
            }
            return app!!.applicationContext
        }

    private fun initSecureSharePreference(storeFileName: String) {
        //not mandatory, can be null too
        val keyPrefix = "lxkj"
        //it's better to provide one, and you need to provide the same key each time after the first time
        val seedKey = BasePreference.SEED_KEY.toByteArray()
        try {
            SecuredPreferenceStore.init(context, storeFileName, keyPrefix, seedKey, DefaultRecoveryHandler())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
