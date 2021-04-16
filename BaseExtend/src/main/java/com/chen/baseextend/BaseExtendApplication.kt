package com.chen.baseextend

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.room.Room
import com.chen.baseextend.repos.offline.OfflineService
import com.chen.baseextend.room.RoomConfig
import com.chen.baseextend.ui.sonic.SonicRuntimeImpl
import com.chen.basemodule.BaseModuleLoad
import com.chen.basemodule.constant.LiveBusKey.EVENT_NETWORK
import com.chen.basemodule.event_bus.BaseNetworkEvent
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.sonic.sdk.SonicConfig
import com.tencent.sonic.sdk.SonicEngine
import io.reactivex.plugins.RxJavaPlugins


/**
 * Created by chen on 2019/4/29
 */
object BaseExtendApplication {

    var app: Application? = null

    lateinit var WECHAT_APP_ID: String

    lateinit var wxapi: IWXAPI

    lateinit var applicationId: String

    val database: RoomConfig by lazy {
        Room.databaseBuilder(app!!.applicationContext, RoomConfig::class.java, "${applicationId}.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    fun init(app: Application, applicationId: String, wechatId: String? = null, enableOffline: Boolean = false) {

//        RxJavaPlugins.setErrorHandler {
//            it.printStackTrace()
//        }

        this.applicationId = applicationId

        this.app = app

        if (enableOffline) OfflineService.start(app)

        wechatId?.run {
            WECHAT_APP_ID = this
            wxapi = WXAPIFactory.createWXAPI(app, this)
            wxapi.registerApp(this)
        }


        (app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .registerNetworkCallback(NetworkRequest.Builder().build(), object : ConnectivityManager.NetworkCallback() {

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        LiveEventBus.get(EVENT_NETWORK).post(BaseNetworkEvent("application", false, "*"))
                    }

                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        if (enableOffline) OfflineService.start(app)
                        LiveEventBus.get(EVENT_NETWORK).post(BaseNetworkEvent("application", true, "*"))
                    }
                })

        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(SonicRuntimeImpl(app.applicationContext), SonicConfig.Builder().build())
        }

        BaseModuleLoad.init(app, applicationId)
    }

}
