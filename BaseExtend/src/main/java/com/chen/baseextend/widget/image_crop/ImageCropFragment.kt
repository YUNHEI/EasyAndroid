package com.chen.baseextend.widget.image_crop

import android.app.Activity.RESULT_OK
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.baseextend.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.databinding.FragmentCropImageBinding
import com.chen.basemodule.basem.argument.ArgFloat
import com.chen.basemodule.basem.argument.ArgString
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.listenClick
import com.chen.basemodule.extend.loadBitmap
import com.chen.basemodule.util.ImageUtil_
import kotlinx.android.synthetic.main.fragment_crop_image.*

@Launch
class ImageCropFragment : BaseSimpleFragment() {

    val uri by ArgString()

    val radio by ArgFloat(1f)

//    override val contentLayoutId = R.layout.fragment_crop_image

    override val binding by doBinding(FragmentCropImageBinding::inflate)

    override fun initAndObserve() {

        _img.viewportRatio = radio

        _img.loadBitmap(uri)

        listenClick(_cancel, _confirm) {
            when (it) {
                _cancel -> {
                    requireActivity().finish()
                }
                _confirm -> {
                    cropImage()
                }
                else -> {
                }
            }
        }
    }

    private fun cropImage() {

        val bitmap = _img.crop(500)

        val path = ImageUtil_.saveBitmapToFile(requireContext(), bitmap, false)

        activity?.run {

            val intent = Intent()

            intent.putExtra("path", path)

            setResult(RESULT_OK, intent)

            finish()
        }
    }
}
