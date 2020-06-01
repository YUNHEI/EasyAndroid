package com.chen.basemodule.util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


/**
 *  Created by chen on 2019/8/28
 **/
object ImageUtil {

    fun getImageWidthHeight(path: String): Pair<Int, Int> {
        BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, this) // 此时返回的bitmap为null
            return outWidth to outHeight
        }
    }

    @Throws(IOException::class)
    fun compressImage(imageFile: File, reqWidth: Float = 1080f, reqHeight: Float = 1920f, compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.WEBP,
                      quality: Int = 80): ByteArray {

        ByteArrayOutputStream().run {
            decodeBitmapFromFile(imageFile, reqWidth, reqHeight)!!.compress(compressFormat, quality, this)
            val bitmapBytes = toByteArray()
            flush()
            close()
            return bitmapBytes
        }
    }

    @Throws(IOException::class)
    private fun decodeBitmapFromFile(imageFile: File, reqWidth: Float, reqHeight: Float): Bitmap? {
        // First decode with inJustDecodeBounds=true to check dimensions

        var scaledBitmap: Bitmap? = null

        var bmp: Bitmap? = null

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        bmp = BitmapFactory.decodeFile(imageFile.absolutePath, options)

        var actualHeight = options.outHeight
        var actualWidth = options.outWidth

        var imgRatio = actualWidth.toFloat() / actualHeight.toFloat()
        val maxRatio = reqWidth / reqHeight

        if (actualHeight > reqHeight || actualWidth > reqWidth) {
            //If Height is greater
            if (imgRatio < maxRatio) {
                imgRatio = reqHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = reqHeight.toInt()

            }  //If Width is greater
            else if (imgRatio > maxRatio) {
                imgRatio = reqWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = reqWidth.toInt()
            } else {
                actualHeight = reqHeight.toInt()
                actualWidth = reqWidth.toInt()
            }
        }

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
        options.inJustDecodeBounds = false
        options.inPurgeable = true
        options.inTempStorage = ByteArray(16 * 1024)

        try {
            bmp = BitmapFactory.decodeFile(imageFile.absolutePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()

        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }

        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f

        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

        val canvas = Canvas(scaledBitmap!!)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(bmp!!, middleX - bmp.width / 2,
                middleY - bmp.height / 2, Paint(Paint.FILTER_BITMAP_FLAG))
        bmp.recycle()
        val exif: ExifInterface
        try {
            exif = ExifInterface(imageFile.absolutePath)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90f)
            } else if (orientation == 3) {
                matrix.postRotate(180f)
            } else if (orientation == 8) {
                matrix.postRotate(270f)
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.width,
                    scaledBitmap.height, matrix, true)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return scaledBitmap

    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            inSampleSize *= 2
            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize launchType that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    fun getImageContentUri(context: Context, path: String): Uri? {

        val cursor: Cursor? = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Images.Media._ID), MediaStore.Images.Media.DATA + "=? ", arrayOf(path), null)
        return if (cursor != null && cursor.moveToFirst()) {
            val id: Int = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri: Uri = Uri.parse("content://media/external/images/media")
            Uri.withAppendedPath(baseUri, "" + id)
        } else {
            // 如果图片不在手机的共享图片数据库，就先把它插入。
            if (File(path).exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, path)
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                null
            }
        }
    }

    fun bmpToByteArray(bmp: Bitmap, needRecycle: Boolean): ByteArray {
        val output = ByteArrayOutputStream()

        bmp.compress(Bitmap.CompressFormat.PNG, 100, output)

        val result = output.toByteArray()

        if (needRecycle) {
            bmp.recycle()
        }
        try {
            output.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}