package com.chen.basemodule.extend

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.journeyapps.barcodescanner.BarcodeEncoder

/**
 * size width*width
 *  Created by 86152 on 2020-05-04
 **/

fun String.QRCode(width: Int = 100): Bitmap {
    return BarcodeEncoder().encodeBitmap(this, BarcodeFormat.QR_CODE, width, width, mutableMapOf(EncodeHintType.CHARACTER_SET to "utf-8"))
}