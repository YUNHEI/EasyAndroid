package com.chen.basemodule.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.lang.Exception

/**
 * @author alan
 * @date 2019-06-11
 */
object  QrCodeUtil {

    /**
     * 在二维码中间添加Logo图案
     */
    private fun addLogo(context: Context ,src: Bitmap, logo: Bitmap): Bitmap? {
        if (src == null) {
            return null
        }

        if (logo == null) {
            return src
        }

        //获取图片的宽高
        val srcWidth = src.width
        val srcHeight = src.height
        val logoWidth = logo.width
        val logoHeight = logo.height

        if (srcWidth == 0 || srcHeight == 0) {
            return null
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src
        }

        val scaleFactor = CommonUtil.dip2px(context, 45f).toFloat()
        var bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888)
        try {
            val canvas = Canvas(bitmap)
            canvas.drawBitmap(src, 0f, 0f, null)
            canvas.drawBitmap(logo, ((srcWidth - scaleFactor) / 2), ((srcHeight - scaleFactor) / 2), null)
            canvas.save()
            canvas.restore()
        } catch (e: Exception) {
            bitmap = null
            e.stackTrace
        }

        return bitmap
    }

    /**
     * 生成二维码Bitmap
     *
     * @param data   文本内容
     * @param logoBm    二维码中心的Logo图标（可以为null）
     * @return 合成后的bitmap
     */
    fun createQRImage(context: Context, data: String, logoBm: Bitmap?=null): Bitmap? {

        try {

            if (data.isEmpty()) {
                return null
            }

            var widthPix = CommonUtil.dip2px(context, 220f)
            val heightPix = widthPix

            //配置参数
            val hints = HashMap<EncodeHintType, Any>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            //容错级别
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
            //设置空白边距的宽度
            hints[EncodeHintType.MARGIN] = 3 //default is 4

            // 图像数据转换，使用了矩阵转换
            var bitMatrix = QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, widthPix, heightPix, hints)
            val pixels = IntArray(widthPix * heightPix)
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (y in 0 until heightPix) {
                for (x in 0 until widthPix) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = -0x1000000
                    } else {
                        pixels[y * widthPix + x] = -0x1
                    }
                }
            }

            // 生成二维码图片的格式，使用ARGB_8888
            var bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix)

            if (logoBm != null) {
                bitmap = addLogo(context,bitmap, logoBm)
            }

            return bitmap
            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            //return bitmap != null && bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(filePath));
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }




}