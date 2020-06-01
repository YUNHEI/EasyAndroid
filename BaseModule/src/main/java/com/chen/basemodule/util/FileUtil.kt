package com.chen.basemodule.util

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.webkit.MimeTypeMap
import com.chen.basemodule.util.ToastUtil.show
import java.io.File
import java.net.URLConnection
import java.util.*

object FileUtil {
    /**
     * 通过文件获取   mimeType
     *
     * @param file
     * @return
     */
    fun getMimeTypeByFile(file: File): String? {
        var result = getMimeTypeByUrl(file.absolutePath)
        if (result != null) {
            return result
        }
        val option = BitmapFactory.Options()
        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        option.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.path, option)
        return if (option.outMimeType != null) {
            option.outMimeType
        } else {
            result = getMimeType(file)
            if (TextUtils.isEmpty(result)) {
                result = URLConnection.guessContentTypeFromName(file.name)
            }
            result
        }
    }

    // url = file path or whatever suitable URL you want.
    fun getMimeTypeByUrl(url: String?): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    /**
     * 获取文件的MIME类型
     *
     * @param file
     * @return
     */
    fun getMimeType(file: File?): String? {
        val suffix = getSuffix(file) ?: return "file/*"
        val type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix)
        return if (type != null && type.isNotEmpty()) {
            type
        } else "file/*"
    }

    private fun getSuffix(file: File?): String? {
        if (file == null || !file.exists() || file.isDirectory) {
            return null
        }
        val fileName = file.name
        if (fileName == "" || fileName.endsWith(".")) {
            return null
        }
        val index = fileName.lastIndexOf(".")
        return if (index != -1) {
            fileName.substring(index + 1).toLowerCase(Locale.US)
        } else {
            null
        }
    }

    fun isDownloadFileExist(context: Context, fileName: String): Boolean {
        return fileExists(getDownloadDir(context) + "/" + fileName)
    }

    fun getDownloadFileDir(context: Context, fileName: String): File {
        return File(getDownloadDir(context) + "/" + fileName)
    }

    fun fileExists(_sPathFileName: String?) = File(_sPathFileName).exists()

    /**
     * 得到手机的缓存目录
     *
     * @param context
     * @return
     */
    fun getCacheDir(context: Context): File {
        Log.i("getCacheDir", "cache sdcard state: " + Environment.getExternalStorageState())
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val cacheDir = context.externalCacheDir
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                Log.i("getCacheDir", "cache dir: " + cacheDir.absolutePath)
                return cacheDir
            }
        }
        val cacheDir = context.cacheDir
        Log.i("getCacheDir", "cache dir: " + cacheDir.absolutePath)
        return cacheDir
    }

    /**
     * 得到下载目录
     */
    fun getDownloadDir(context: Context): String? {
        val skinDir = File(context.getExternalFilesDir(""),
                "download")
        if (!skinDir.exists()) {
            if (!skinDir.mkdirs()) {
                show("无法创建文件目录，请检查是否关闭了相关权限")
                return null
            }
        }
        return skinDir.absolutePath
    }

    fun soundPathExists(path: String?): Boolean {
        val file = File(path)
        return file.exists() && file.length() > 10
    }

    fun getSoundFilePath(uuid: String): String? {
        val mediaStorageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
                "miaozhuan/Sound")
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                show("目录不存在，无法保存图片")
                return null
            }
        }
        return mediaStorageDir.path + File.separator + uuid
    }

    fun isLocalPath(path: String?): Boolean {
        return path != null && path.startsWith("/storage/emulated/")
    }
}