package com.chen.baseextend.ui

import android.content.Context
import android.content.Intent
import com.chen.basemodule.basem.BaseFragment
import com.chen.basemodule.basem.BaseSimActivity
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSessionConfig


class WebActivity : BaseSimActivity() {

    override val fragment: BaseFragment? = WebFragment()

    companion object {

        fun toWebView(context: Context, url: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("url", url)
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
