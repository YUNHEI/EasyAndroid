package com.chen.app

import android.app.Application
import android.os.Build
import android.os.Process
import android.webkit.WebView
import cn.jpush.android.api.JPushInterface
import cn.jpush.im.android.api.JMessageClient
import com.chen.baseextend.BaseExtendApplication
import com.chen.basemodule.constant.BasePreference
import com.chen.basemodule.util.AppStatusUtil
import com.chen.basemodule.util.SystemUtil
import com.tencent.bugly.Bugly
import com.tencent.bugly.crashreport.CrashReport.UserStrategy


class App : Application() {

    companion object {

        lateinit var app: Application

//        const val TIM_APP_ID = 1400246751
    }

    override fun onCreate() {
        super.onCreate()

        app = this

        AppStatusUtil.INS.init(applicationContext)

        BaseExtendApplication.init(this, BuildConfig.APPLICATION_ID, BuildConfig.WECHAT_ID,true)

//        JPushInterface.setDebugMode(true)
//        JPushInterface.init(this)

//        JMessageClient.init(applicationContext, false)// 是否启用消息漫游
//        JMessageClient.setDebugMode(BuildConfig.DEBUG)
//        //设置Notification的模式
//        JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_WITH_SOUND or JMessageClient.FLAG_NOTIFY_WITH_LED or JMessageClient.FLAG_NOTIFY_WITH_VIBRATE)

        //注册通知栏点击
//        NotiClickEvent()
        //系统消息接收
//        OnMessageEvent()
//        if (SessionWrapper.isMainProcess(app)) {
//            TIMSdkConfig(TIM_APP_ID).run {
//                TIMManager.getInstance().init(app, this)
//            }
//        }

        // 获取当前进程名
        val processName = SystemUtil.getProcessName(Process.myPid())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && processName != applicationContext.packageName) {
            WebView.setDataDirectorySuffix(processName)
        }

        //安全联盟初始化
//        JLibrary.InitEntry(applicationContext)
//        if (BasePreference._OAID.isEmpty() && processName == applicationContext.packageName){
//            MiitHelper().getDeviceIds(applicationContext)
//        }

        // 设置是否为上报进程
        // 初始化Bugly
//        Bugly.init(applicationContext, "6db0766c6a", BuildConfig.DEBUG || true, UserStrategy(applicationContext).apply {
//            isUploadProcess = processName == null || processName == applicationContext.packageName
//            appChannel = BuildConfig.FLAVOR
//            deviceID = BasePreference._OAID
//        })

//         CrashReport.testJavaCrash()
    }
}
