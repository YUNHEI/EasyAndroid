package com.chen.basemodule.widget.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.chen.basemodule.R
import kotlinx.android.synthetic.main.dialog_warning.*

class WarningDialog(context: Context) : Dialog(context, R.style.confirm_dialog) {


    init {
        setCancelable(false)

        setCanceledOnTouchOutside(false)

        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_warning, null))

        _confirm.setOnClickListener { dismiss() }

        _cancel.setOnClickListener { dismiss() }
    }

    fun setMsg(msg: String?): WarningDialog {
        _msg.text = msg
        return this
    }

    fun setConfirm(confirm: String = "确定", @ColorRes color: Int = R.color.blue_text, onClick: ((v: WarningDialog) -> Unit)? = null): WarningDialog {
        _confirm.run {
            setTextColor(ContextCompat.getColor(context, color))
            text = confirm
            setOnClickListener { onClick?.invoke(this@WarningDialog) ?: dismiss() }
        }
        return this
    }

    fun setCancel(cancel: String = "取消", @ColorRes color: Int = R.color.gray_99, onClick: ((v: WarningDialog) -> Unit)? = null): WarningDialog {
        _cancel.run {
            visibility = View.VISIBLE
            setTextColor(ContextCompat.getColor(context, color))
            text = cancel
            setOnClickListener { onClick?.invoke(this@WarningDialog) ?: dismiss() }
        }
        return this
    }
}
