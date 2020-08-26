package com.chen.baseextend.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chen.basemodule.basem.BaseFragment
import com.chen.basemodule.basem.BaseSimActivity
import com.chen.basemodule.basem.fragmentQueue
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSessionConfig


class WebActivity : BaseSimActivity() {

    companion object {

        fun toWebView(context: Context, url: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("url", url)
            fragmentQueue.offer(WebFragment().apply {
                arguments = Bundle().apply { putString("url", url) }
            })
            context.startActivity(intent)
        }

        fun preload(url: String?) {
            if (!url.isNullOrEmpty()) {
                // preload session
                SonicEngine.getInstance().preCreateSession(url, SonicSessionConfig.Builder().setSupportLocalServer(true).build())
            }
        }
    }

}
