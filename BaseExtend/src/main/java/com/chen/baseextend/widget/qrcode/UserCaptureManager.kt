package com.chen.baseextend.widget.qrcode

import android.app.Activity
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView

/**
 * @author alan
 * @date 2019/3/7
 */
class UserCaptureManager(activity: Activity?, barcodeView: DecoratedBarcodeView?, private val onResult: (result: String) -> Unit) : CaptureManager(activity, barcodeView) {

    override fun returnResult(rawResult: BarcodeResult) {
        onResult.invoke(rawResult.text)
    }
}