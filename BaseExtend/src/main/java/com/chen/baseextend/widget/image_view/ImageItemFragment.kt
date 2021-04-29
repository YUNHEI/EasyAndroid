package com.chen.baseextend.widget.image_view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chen.baseextend.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.databinding.FragmentImageItemBinding
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.toastError
import com.chen.basemodule.extend.toastWarn
import com.chen.basemodule.util.GlideApp
import com.chen.basemodule.util.ImageUtil_
import com.davemorrissey.labs.subscaleview.ImageSource
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

    override val binding by doBinding(FragmentImageItemBinding::inflate)

    private var mImageUrl: String? = null

    internal var resource: Bitmap? = null

    private var l: ProgressListener? = object : ProgressListener {
        override fun onProgress(progressInfo: ProgressInfo) {
            binding.run {
                if (Progressbar == null) return
                Progressbar.setDonut_progress(progressInfo.percent.toString())
                if (progressInfo.percent >= 100) {
                    Progressbar.visibility = View.GONE
                } else {
                    Progressbar.visibility = View.VISIBLE
                }
            }
        }

        override fun onError(id: Long, e: Exception) {

        }
    }

    override fun initAndObserve() {

        mImageUrl = requireArguments().getString("uri")

        binding.run {
            if (mImageUrl == null) {
                "the image url is none".toastError()
            } else {
                Image.setOnClickListener {
                    if (activity != null && !requireActivity().isFinishing) {
                        requireActivity().finish()
                    }
                }

                Image.setMaxTileSize(1024)


                ProgressManager.getInstance().addResponseListener(mImageUrl, l)

                GlideApp.with(requireContext())
                    .asBitmap()
                    .load(mImageUrl)
                    .error(R.drawable.ic_placeholder_black)
                    .placeholder(R.drawable.ic_placeholder_black)
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(
                            bitmap: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            resource = bitmap
                            if (bitmap.height > 4096) {
                                val key =
                                    mImageUrl!!.split("/".toRegex()).dropLastWhile { it.isEmpty() }
                                        .toTypedArray()
                                val url = ImageUtil_.saveBitmapToFile(
                                    context!!,
                                    bitmap,
                                    key[key.size - 1],
                                    false
                                )
                                Image.setImage(ImageSource.uri(url.orEmpty()), null, null)
                            } else {
                                Image.setImage(ImageSource.bitmap(bitmap))
                            }
                            Progressbar.visibility = View.GONE
                        }
                    })

            }

        }
    }

    fun save() {
        if (resource != null) {
            ImageUtil_.saveBitmapToFile(requireContext(), resource, true)
        } else {
            "图片还未加载完成，请稍后".toastWarn()
        }
    }

    override fun onDestroyView() {
        l = null
        super.onDestroyView()
    }

}
