package com.chen.baseextend.widget.image_view

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import cn.jpush.im.android.api.callback.DownloadCompletionCallback
import cn.jpush.im.android.api.callback.ProgressUpdateCallback
import cn.jpush.im.android.api.content.ImageContent
import cn.jpush.im.android.api.model.Message
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.databinding.FragmentImageItemBinding
import com.chen.basemodule.extend.argString
import com.chen.basemodule.extend.doBinding
import com.davemorrissey.labs.subscaleview.ImageSource
import java.io.File

class ChatImageItemFragment : BaseSimpleFragment() {

    companion object {

        fun newInstance(message: Message): ChatImageItemFragment {
            val fragment = ChatImageItemFragment()
            val bundle = Bundle()
            bundle.putString("message", message.toJson())
            fragment.arguments = bundle
            return fragment
        }
    }

    override val binding by doBinding(FragmentImageItemBinding::inflate)

    private val message: Message by lazy { Message.fromJson(argString("message")) }

    override fun initAndObserve() {

        binding.run {

            Image.setOnClickListener {
                if (activity != null && !requireActivity().isFinishing) {

                    activity?.window?.setFlags(
                        WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
                    )

                    ActivityCompat.finishAfterTransition(requireActivity())
                }
            }

            Image.setMaxTileSize(1024)

            (message.content as ImageContent).run {
                if (!localPath.isNullOrEmpty()) {
                    Progressbar.visibility = View.GONE
                    Image.setImage(
                        ImageSource.uri(localPath).apply { dimensions(width, height) },
                        ImageSource.uri(localThumbnailPath),
                        null
                    )
                } else {
                    Image.setImage(ImageSource.uri(localThumbnailPath), null, null)
                    downloadOriginImage(message, object : DownloadCompletionCallback() {
                        override fun onComplete(p0: Int, p1: String?, p2: File?) {
                            if (p0 == 0) {
                                p2?.run {
                                    Image.setImage(ImageSource.uri(p2.toUri()))
                                }
                            }
                        }
                    })
                    message.setOnContentDownloadProgressCallback(object : ProgressUpdateCallback() {
                        override fun onProgressUpdate(p0: Double) {
                            Progressbar.setDonut_progress(p0.toString())
                            if (p0 >= 1) {
                                Progressbar.visibility = View.GONE
                            } else {
                                Progressbar.visibility = View.VISIBLE
                            }
                        }
                    })
                }
            }
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
            )
        }
        return super.onKeyUp(keyCode, event)
    }

    fun save() {
//        if (resource != null) {
//            ImageUtil_.saveBitmapToFile(context, resource, true)
//        } else {
//            "图片还未加载完成，请稍后".toastWarn()
//        }
    }
}
