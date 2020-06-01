package com.chen.basemodule.util

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.BuildConfig
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chen.basemodule.R
import com.chen.basemodule.util.ToastUtil.show
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object ImageUtil_ {
    const val IMAGE_SIZE_AUTO = -1
    const val IMAGE_SIZE_ORIG = -2

    /*图片加载相关配置*/ /*缓存模式*/
    val IMAGE_CACHE_MODE = DiskCacheStrategy.AUTOMATIC

    /*内存缓存*/
    const val IMAGE_SKIP_MEMCACHE = false
    val ENABLE_RESIZE_NET_IMAGE = !BuildConfig.DEBUG || false

    fun saveImageFromImageView(context: Context, imageView: ImageView, toast: Boolean = true): String? {
        imageView.isDrawingCacheEnabled = true
        val path = saveBitmapToFile(context, imageView.drawingCache, toast)
        imageView.isDrawingCacheEnabled = false
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://$path")))
        return path
    }

    fun saveBitmapToFile(context: Context, bitmap: Bitmap?, toast: Boolean = true): String? {
        return saveBitmapToFile(context, bitmap, null, toast)
    }

    fun saveBitmapToFile(context: Context, bitmap: Bitmap?, key: String?, toast: Boolean): String? {
        var bitmap = bitmap
        if (bitmap == null) {
            if (toast) show("无法保存图片")
            return null
        }
        val mediaStorageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "fmzhuan/Image")
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                if (toast) show("目录不存在，无法保存图片")
                return null
            }
        }
        val mediaFile: File
        mediaFile = if (key != null && key.trim { it <= ' ' }.isNotEmpty()) {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(Date())
            File(mediaStorageDir.path + File.separator + key)
        } else {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(Date())
            File(mediaStorageDir.path + File.separator + "IMG_" + timeStamp
                    + ".jpg")
        }
        if (mediaFile.exists()) return mediaFile.path
        val baos: FileOutputStream
        try {
            baos = FileOutputStream(mediaFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            baos.flush()
            baos.close()
            // bitmap.recycle();
            bitmap = null
        } catch (e: IOException) {
            e.printStackTrace()
            if (toast) show("图片保存异常")
            return null
        }

        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://${mediaFile.path}")))

        if (toast) show("图片已保存至" + mediaStorageDir.path)
        return mediaFile.path
    }

    fun createCover(context: Context?, source: Bitmap?, width: Int, height: Int): Bitmap {
        var source = source
        val des = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        return if (source == null || width <= 0 || height <= 0) {
            des.eraseColor(ContextCompat.getColor(context!!, R.color.gray_ed))
            des
        } else {
            val canvas = Canvas(des)
            canvas.drawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
            val sw = source.width
            val sh = source.height
            val ratioW = sw / (width + 0f)
            val ratioH = sh / (height + 0f)
            val rectDA = Rect(0, 0, width, height)
            val ratio: Float
            if (sw >= sh) {
                ratio = Math.min(ratioH, ratioW)
                source = Bitmap.createScaledBitmap(source, (sw / ratio).toInt(), (sh / ratio).toInt(), true)
                val left = (source.width - width) / 2
                val top = (source.height - height) / 2
                val rectS = Rect(left, top, left + width, top + height)
                canvas.drawBitmap(source, rectS, rectDA, paint)
                des
            } else {
                ratio = ratioH
                val bg = Bitmap.createScaledBitmap(Bitmap.createScaledBitmap(source, (80 / ratioH * ratioW).toInt(), 80, true), width, height, true)
                canvas.drawBitmap(bg, Rect(0, 0, 50, 50), rectDA, paint)
                source = Bitmap.createScaledBitmap(source, (sw / ratio).toInt(), (sh / ratio).toInt(), true)
                val left = (source.width - width) / 2
                val top = (source.height - height) / 2
                val rectD = Rect(-left, top, left + width, top + height)
                val rectS = Rect(0, 0, source.width, source.height)
                canvas.drawBitmap(source, rectS, rectD, paint)
                des
            }
        }
    }

//    private fun getPathById(id: Int, width: Int, height: Int): String? {
//        if (id <= 0) return null
//        val path = StringBuilder(100)
//        path.append(com.chen.basemodule.BuildConfig.HOST).append("api/v2/files/").append(id)
//        if (ENABLE_RESIZE_NET_IMAGE && (width > 0 || height > 0)) path.append("?")
//        if (ENABLE_RESIZE_NET_IMAGE && width > 0) path.append("w=").append(width)
//        if (ENABLE_RESIZE_NET_IMAGE && height > 0) path.append("h=").append(height)
//        return path.toString()
//    }

    fun isLocalPath(path: String?): Boolean {
        return path != null && path.startsWith("/storage/emulated/")
    }
}