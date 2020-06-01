//package com.chen.baseextend.util
//
//import android.app.Activity
//import android.app.DownloadManager
//import android.app.DownloadManager.Query
//import android.app.DownloadManager.Request
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.database.ContentObserver
//import android.net.Uri
//import android.os.Build
//import android.os.Environment
//import android.os.Handler
//import android.util.Log
//import androidx.core.content.FileProvider
//import androidx.fragment.app.FragmentActivity
//import com.chen.baseextend.BuildConfig
//import com.chen.baseextend.base.fragment.BaseSimpleFragment
//import com.chen.baseextend.bean.VersionBean
//import com.chen.baseextend.util.ExtendPreHelper
//import com.chen.basemodule.BaseModuleLoad
//import com.chen.basemodule.constant.BasePreference
//import com.chen.basemodule.extend.*
//import com.chen.basemodule.network.base.BaseRequest
//import com.chen.basemodule.util.NetworkUtil
//import com.chen.system_module.BuildConfig
//import com.chen.system_module.widget.dialog.UpdateDialog
//import java.io.File
//import kotlin.math.roundToInt
//
///**
// * Created by chen on 2016/6/29.
// */
//object ApkDownloadUtil {
//
//    //"content://downloads/my_downloads"必须这样写不可更改
//    val CONTENT_URI = Uri.parse("content://downloads/my_downloads")
//
//    val FILE_PROVIDER_AUTH = "com.chen.system_module.fileprovider"
//
//    private var downloadObserver: DownloadChangeObserver? = null
//
//    private val manager: DownloadManager by lazy { BaseModuleLoad.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
//
//    internal var version: Int = 0
//
//    private var id: Long = 0
//
//    fun installApk(context: Context, url: String) {
//
//        var uri: Uri? = null
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
//                    String.format(BuildConfig.APK_NAME, getKey(BasePreference.DOWNLOAD_URL)))
//
//            if (file.exists() && file.length() > 1024 * 1024 * 5) {
//                uri = FileProvider.getUriForFile(BaseModuleLoad.context, FILE_PROVIDER_AUTH, file)
//            }
//        } else {
//            uri = getFileUriById(BasePreference.DOWNLOAD_ID)
//        }
//
//        when (getDownloadStatus(BasePreference.DOWNLOAD_ID)) {
//            DownloadManager.STATUS_SUCCESSFUL -> if (uri != null) {
//                startInstall(uri)
//            } else {
//                startDownload(context)
//            }
//            DownloadManager.STATUS_FAILED -> {
//                if (uri != null) {
//                    val file = File(uri.path)
//                    if (file.exists()) file.delete()
//                }
//                startDownload(context)
//            }
//            DownloadManager.STATUS_RUNNING, DownloadManager.STATUS_PAUSED, DownloadManager.STATUS_PENDING -> "安装包下载中...".toastWarn()
//        }
//    }
//
//    private fun getDownloadStatus(downloadId: Long): Int {
//
//        val query = Query().setFilterById(downloadId)
//        val c = manager.query(query)
//
//        var downloadStatus = -1
//
//        if (c != null && c.moveToFirst()) {
//            downloadStatus = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))
//        }
//
//        return downloadStatus
//    }
//
//    private fun startDownload(context: Context) {
//
//        val request = Request(Uri.parse(BasePreference.DOWNLOAD_URL))
//        //设置在什么网络情况下进行下载
//        request.setAllowedOverMetered(true)
//        request.setAllowedOverRoaming(true)
//
//        //设置通知栏标题
//        request.setNotificationVisibility(Request.VISIBILITY_VISIBLE)
//        request.setTitle(String.format(BuildConfig.APK_NAME, getKey(BasePreference.DOWNLOAD_URL)))
//        request.setDescription("EaseAndroid正在下载")
//        //设置文件存放目录
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, String.format(BuildConfig.APK_NAME, getKey(BasePreference.DOWNLOAD_URL)))
//        val receiver = DownLoadCompleteReceiver()
//        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
//
//        BaseModuleLoad.context.registerReceiver(receiver, filter)
//
//        id = manager!!.enqueue(request)
//
//        BasePreference.DOWNLOAD_VERSION = version
//        BasePreference.DOWNLOAD_ID = id
//
//        //10.采用内容观察者模式实现进度
//        downloadObserver = DownloadChangeObserver(null, context as Activity)
//        context.getContentResolver().registerContentObserver(CONTENT_URI, true, downloadObserver!!)
//
//
//        Log.d("download id", id.toString() + "")
//
//    }
//
//    private class DownLoadCompleteReceiver : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            if (intent.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
//
//                if (d.isShowing) {
//                    d.setProgress(100)
//                }
//
//                //下载成功 取消注册
//                context.contentResolver.unregisterContentObserver(downloadObserver!!)
//                downloadObserver = null
//
//                val uri: Uri?
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
//                            String.format(BuildConfig.APK_NAME, getKey(BasePreference.DOWNLOAD_URL)))
//                    uri = FileProvider.getUriForFile(context, FILE_PROVIDER_AUTH, file)
//                    startInstall(uri)
//                } else {
//                    val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
//                    if (BasePreference.DOWNLOAD_ID == id) {
//                        uri = getFileUriById(id)
//                        startInstall(uri)
//                    }
//                }
//
//                context.unregisterReceiver(this)
//            } else if (intent.action == DownloadManager.ACTION_NOTIFICATION_CLICKED) {
//
//            }
//        }
//    }
//
//    private fun getFileUriById(id: Long): Uri? {
//
//        val query = Query()
//        query.setFilterById(id)
//        query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL)
//        val cursor = manager!!.query(query)
//
//        if (cursor.moveToNext()) {
//            val uri = Uri.parse(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)))
//            cursor.close()
//            if (File(uri.path).exists()) return uri
//        }
//
//        return null
//    }
//
//    private fun startInstall(uri: Uri?) {
//
//        val i = Intent(Intent.ACTION_VIEW)
//        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//        }
//        i.setDataAndType(uri, "application/vnd.android.package-archive")
//        //        ExtendPreHelper.setFirstLogin(false); // reset the first login state // ugly certificateCode style and I still fuck with it ugly too // and it still doesn't work!
//        BaseModuleLoad.context.startActivity(i)
//    }
//
//    private fun getKey(url: String?): String {
//        return if (url == null || url.length <= 12 || !url.contains("http:")) "" else url.substring(url.length - 20, url.length - 4)
//    }
//
//
//    //用于显示下载进度
//    internal class DownloadChangeObserver(handler: Handler?, private val activity: Activity) : ContentObserver(handler) {
//
//        override fun onChange(selfChange: Boolean) {
//
//            val dManager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//            val cursor = dManager.query(Query().apply { setFilterById(id) })
//            if (cursor != null && cursor.moveToFirst()) {
//                val totalColumn = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
//                val currentColumn = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
//                val totalSize = cursor.getInt(totalColumn)
//                val currentSize = cursor.getInt(currentColumn)
//                val percent = currentSize.toFloat() / totalSize.toFloat()
//                val progress = (percent * 100).roundToInt()
//
//                if (isForceUpdate) {
//                    if (d.isShowing) {
//                        d.setProgress(progress)
//                    }
//                }
//            }
//        }
//    }
//
//}
