package com.chen.baseextend.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.baseextend.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.widget.photopicker.PhotoPicker
import com.chen.baseextend.widget.qrcode.UserCaptureManager
import com.chen.basemodule.extend.toast
import com.chen.basemodule.network.base.BaseResponse
import com.chen.basemodule.util.WindowsUtil
import com.google.zxing.DecodeHintType
import com.google.zxing.RGBLuminanceSource
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import kotlinx.android.synthetic.main.fragment_qr_code.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 *  Created by 86152 on 2020-05-02
 **/

@Launch
class QRCodeFragment : BaseSimpleFragment() {

    private val capture: UserCaptureManager by lazy {
        UserCaptureManager(activity, _dbv_custom) {

        }
    }

    override val contentLayoutId = R.layout.fragment_qr_code

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        capture.initializeFromIntent(activity!!.intent, savedInstanceState)
        capture.decode()
    }

    override fun initAndObserve() {

        WindowsUtil.setDarkTheme(activity!!, true)

        toolbar.run {
            background = R.color.dark
            isLight = false
            center("扫一扫")
            left(R.mipmap.ic_back_white) {
                capture.onDestroy()
                activity?.finish()
            }
            right("相册") {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setGridColumnCount(4)
                        .setShowCamera(false)
                        .setShowGif(false)
                        .setPreviewEnabled(true)
                        .setIsShowSelect(false)
                        .start(context, this@QRCodeFragment, PhotoPicker.REQUEST_CODE)
            }
            divider(0)
        }
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }


    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return _dbv_custom.onKeyDown(keyCode, event) || super.onKeyUp(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            data?.run {
                val photos = getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS)
                if (!photos.isNullOrEmpty()) {
                    viewModel.run {
                        requestData(
                                { decodeImage(photos[0]) },
                                {
                                    it.toast()
                                }
                        )
                    }

                }
            }
        }
    }

    private suspend fun decodeImage(path: String): BaseResponse<String> = suspendCoroutine {
        BitmapFactory.decodeFile(path).run {
            val pixels = IntArray(width * height)
            getPixels(pixels, 0, width, 0, 0, width, height)
            val source = RGBLuminanceSource(width, height, pixels)
            val decoder = DefaultDecoderFactory().createDecoder(mutableMapOf<DecodeHintType, Any>())
            decoder.decode(source)?.run {
                it.resume(BaseResponse(text, 200, "扫描成功"))
            } ?: run {
                it.resume(BaseResponse("", 300, "未识别二维码"))
            }
        }
    }
}