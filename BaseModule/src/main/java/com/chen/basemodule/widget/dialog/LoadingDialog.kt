package com.chen.basemodule.widget.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.StyleRes
import com.chen.basemodule.R
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialog(activity: Activity, @StyleRes themeResId: Int = R.style.loading_dialog) : Dialog(activity, themeResId) {

    val runnable: Runnable by lazy {
        Runnable {
            try {
                if (!activity.isFinishing) show()
            } catch (e: Exception) {
                Log.e("loadingDialog", "loading显示失败，activity提前退出")
            }
        }
    }

    private val handler = Handler()

    init {
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_loading, null))
    }

    fun show(msg: String?) {
        _msg.text = msg
        handler.postDelayed(runnable, 180)
    }

    override fun dismiss() {
        handler.removeCallbacks(runnable)
        super.dismiss()
    }
}
