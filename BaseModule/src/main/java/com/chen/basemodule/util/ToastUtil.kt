package com.chen.basemodule.util

import android.view.Gravity
import android.widget.Toast
import com.chen.basemodule.BaseModuleLoad

object ToastUtil {

    fun show(msg: String?) {
        val toast = Toast.makeText(BaseModuleLoad.context, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 100)
        toast.show()
    }

}
