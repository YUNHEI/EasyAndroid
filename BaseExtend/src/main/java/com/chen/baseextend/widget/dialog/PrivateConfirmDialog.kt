package com.chen.baseextend.widget.dialog

import android.app.Dialog
import android.content.Context
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import com.chen.baseextend.R
import com.chen.baseextend.ui.WebActivity
import com.chen.basemodule.constant.BasePreference
import kotlinx.android.synthetic.main.dialog_private.*

class PrivateConfirmDialog(context: Context) : Dialog(context, R.style.confirm_dialog) {

    init {
        setCancelable(false)

        setCanceledOnTouchOutside(false)

        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_private, null))

        _confirm.setOnClickListener { dismiss() }

        _cancel.setOnClickListener { dismiss() }

        WebActivity.preload(BasePreference._PRIVATE_RULE_URL)

        _title.text = "私隐保护提示"

        _msg.movementMethod = LinkMovementMethod.getInstance()

        _msg.text = "请放心，EaseAndroid坚决保障您的私隐信息安全，您的信息仅用于为您提供服务或改善服务体验。\n" +
                "如果您确实无法认同此政策，可点击“不同意”并退出应用。"
    }

    fun setConfirm(onClick: ((v: PrivateConfirmDialog) -> Unit)? = null): PrivateConfirmDialog {
        _confirm.text = "同意"
        _confirm.run {
            setOnClickListener { onClick?.invoke(this@PrivateConfirmDialog) ?: dismiss() }
        }
        return this
    }

    fun setCancel(onClick: ((v: PrivateConfirmDialog) -> Unit)? = null): PrivateConfirmDialog {
        _cancel.text = "不同意并退出"
        _cancel.run {
            setOnClickListener { onClick?.invoke(this@PrivateConfirmDialog) ?: dismiss() }
        }
        return this
    }
}
