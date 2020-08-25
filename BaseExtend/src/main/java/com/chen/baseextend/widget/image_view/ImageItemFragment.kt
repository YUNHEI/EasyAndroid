package com.chen.baseextend.widget.image_view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chen.baseextend.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.toastError
import com.chen.basemodule.extend.toastWarn
import com.chen.basemodule.util.GlideApp
import com.chen.basemodule.util.ImageUtil_
import com.davemorrissey.labs.subscaleview.ImageSource
import kotlinx.android.synthetic.main.fragment_image_item.*
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo

class ImageItemFragment : BaseSimpleFragment() {

    companion object {

        fun newInstance(uri: String): ImageItemFragment {
            val fragment = ImageItemFragment()
            val bundle = Bundle()
            bundle.putString("uri", uri)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val contentLayoutId = R.layout.fragment_image_item

    private var mImageUrl: String? = null

    internal var resource: Bitmap? = null

    private var l: ProgressListener? = object : ProgressListener {
        override fun onProgress(progressInfo: ProgressInfo) {
            if (_progressbar == null) return
            _progressbar.setDonut_progress(progressInfo.percent.toString())
            if (progressInfo.percent >= 100) {
                _progressbar.visibility = View.GONE
            } else {
                _progressbar.visibility = View.VISIBLE
            }
        }

        override fun onError(id: Long, e: Exception) {

        }
    }

    override fun initAndObserve() {

        mImageUrl = arguments!!.getString("uri")

        if (mImageUrl == null) {
            "the image url is none".toastError()
        } else {
            _image.setOnClickListener {
                if (activity != null && !activity!!.isFinishing) {
                    activity!!.finish()
                }
            }

            _image.setMaxTileSize(1024)


            ProgressManager.getInstance().addResponseListener(mImageUrl, l)

            GlideApp.with(context!!)
                    .asBitmap()
                    .load(mImageUrl)
                    .error(R.drawable.ic_placeholder_black)
                    .placeholder(R.drawable.ic_placeholder_black)
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                            resource = bitmap
                            if (bitmap.height > 4096) {
                                val key = mImageUrl!!.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                                val url = ImageUtil_.saveBitmapToFile(context!!, bitmap, key[key.size - 1], false)
                                _image.setImage(ImageSource.uri(url.orEmpty()), null, null)
                            } else {
                                _image.setImage(ImageSource.bitmap(bitmap))
                            }
                            _progressbar.visibility = View.GONE
                        }
                    })

        }
    }

    fun save() {
        if (resource != null) {
            ImageUtil_.saveBitmapToFile(context!!, resource, true)
        } else {
            "图片还未加载完成，请稍后".toastWarn()
        }
    }

    override fun onDestroyView() {
        l = null
        super.onDestroyView()
    }

}
