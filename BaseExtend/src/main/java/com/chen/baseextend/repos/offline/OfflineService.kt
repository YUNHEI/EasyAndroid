package com.chen.baseextend.repos.offline

import android.app.Application
import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.chen.baseextend.BaseExtendApplication

/**
 *  Created by chen on 2019/10/12
 **/
class OfflineService : IntentService("OfflineService") {

    /**离线发送需要在这里注册单例*/
    private val offlineReposList = mutableListOf(
            ProjectOfflineRepos //发布任务 离线
    )

    companion object {
        fun start(app: Application = BaseExtendApplication.app!!) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                app.startForegroundService(Intent(app, OfflineService::class.java))
            }else{
                app.startService(Intent(app, OfflineService::class.java))
            }
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        offlineReposList.forEach {
            it.postOffline()
        }
    }

    override fun onCreate() {
        super.onCreate()
        //防止后台启动服务奔溃
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            //数字是随便写的“40”，
            nm.createNotificationChannel(NotificationChannel("40", "App Service", NotificationManager.IMPORTANCE_DEFAULT))

            val builder =  NotificationCompat.Builder(this, "40")

            //其中的2，是也随便写的，正式项目也是随便写
            startForeground(2 ,builder.build())
        }
    }
}