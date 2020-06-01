package com.chen.baseextend.widget.image_view

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.chen.baseextend.R

import com.chen.basemodule.basem.BaseSimActivity

class ImageViewerActivity : BaseSimActivity() {

    override val fragment = ImageViewerFragment()

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.viewer_alpha_exit)
    }

    companion object {

        fun toImageViewerActivity(context: Context, urls: ArrayList<String>, currentUrl: String) {
            val intent = Intent(context, ImageViewerActivity::class.java)
            intent.putStringArrayListExtra("urls", urls)
            intent.putExtra("url", currentUrl)
            intent.putExtra("position", urls.indexOf(currentUrl))
            context.startActivity(intent)
            if (context is Activity) {
                context.overridePendingTransition(R.anim.viewer_alpha_in, 0)
            }
        }
    }
}
