package com.chen.baseextend.widget.dialog

import android.app.Dialog
import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import com.chen.baseextend.R
import com.chen.baseextend.ui.WebActivity
import com.chen.basemodule.constant.BasePreference
import com.chen.basemodule.extend.color
import kotlinx.android.synthetic.main.dialog_private.*

class PrivateDialog(context: Context) : Dialog(context, R.style.confirm_dialog) {

    init {
        setCancelable(false)

        setCanceledOnTouchOutside(false)

        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_private, null))

        _confirm.setOnClickListener { dismiss() }

        _cancel.setOnClickListener { dismiss() }

        WebActivity.preload(BasePreference._PRIVATE_RULE_URL)

        _title.text = "EaseAndroid私隐协议"

        _msg.movementMethod = LinkMovementMethod.getInstance()

        _msg.text = SpannableString("        欢迎使用“EaseAndroid”！我们非常重视您的个人信息和死因保护，在您使用“EaseAndroid”服务之前，请仔细阅读《EaseAndroid私隐协议》和《用户协议》，我们将严格按照您同意的各项条款使用您的个人信息，以便为您提供更好的服务。\n" +
                "如果您同意此政策，请点击“同意”并开始使用我们的产品和服务，我们将尽全力保护您的个人信息安全。").apply {

            setSpan(ForegroundColorSpan(context.color(R.color.blue_text)), 56, 63, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(context.color(R.color.blue_text)), 66, 70, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

            WebActivity.preload(BasePreference._PRIVATE_RULE_URL)
            WebActivity.preload(BasePreference._USER_RULE_URL)

            setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    WebActivity.toWebView(context, BasePreference._PRIVATE_RULE_URL)
                }
            }, 56, 63, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    WebActivity.toWebView(context, BasePreference._USER_RULE_URL)
                }
            }, 66, 70, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        }
    }

    fun setConfirm(onClick: ((v: PrivateDialog) -> Unit)? = null): PrivateDialog {
        _confirm.run {
            setOnClickListener { onClick?.invoke(this@PrivateDialog) ?: dismiss() }
        }
        return this
    }

    fun setCancel(onClick: ((v: PrivateDialog) -> Unit)? = null): PrivateDialog {
        _cancel.run {
            setOnClickListener { onClick?.invoke(this@PrivateDialog) ?: dismiss() }
        }
        return this
    }
}
