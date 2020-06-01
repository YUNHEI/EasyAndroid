//package com.chen.baseextend.repos.offline
//
//import android.annotation.TargetApi
//import android.content.Context
//import android.os.Build
//import android.util.Log
//import androidx.work.*
//import com.chen.baseextend.BaseExtendApplication
//
///**
// *  Created by chen on 2019/10/28
// **/
//class OfflineWork(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
//
//    companion object {
//        @TargetApi(Build.VERSION_CODES.O)
//        fun startOfflineWork() {
//            WorkManager.getInstance(BaseExtendApplication.app!!.applicationContext)
//                    .enqueue(OneTimeWorkRequestBuilder<OfflineWork>()
//                            .setConstraints(Constraints.Builder()
//                                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
//                                    .build())
//                            .build())
//        }
//    }
//
//    override fun doWork(): Result {
//
//        Log.e("-------------离线", "开始离线")
//        return Result.success()
//    }
//
//}