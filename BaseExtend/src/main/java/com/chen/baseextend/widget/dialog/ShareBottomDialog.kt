package com.chen.baseextend.widget.dialog

import com.chen.baseextend.R
import com.chen.baseextend.bean.project.ProjectBean
import com.chen.baseextend.util.wechat.WechatShareUtil
import com.chen.basemodule.constant.BasePreference
import com.chen.basemodule.extend.listenClick
import com.chen.basemodule.widget.dialog.BaseBottomDialogFragment
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import kotlinx.android.synthetic.main.dialog_share.*

class ShareBottomDialog(val data: ProjectBean) : BaseBottomDialogFragment() {

    override val contentLayoutId = R.layout.dialog_share

    override fun initAndObserve() {
        listenClick(_wechat, _circle, _cancel) {

            when (it) {
                _wechat, _circle -> {
                    WechatShareUtil.share(if (it == _wechat) SendMessageToWX.Req.WXSceneSession else SendMessageToWX.Req.WXSceneTimeline,
                            "${BasePreference._PROJECT_SHARE_URL}?code=${BasePreference._INVITE_CODE}",
                            data.title.orEmpty(),
                            "${data.typeDesc} | ${data.itemDesc}")
                }
                else -> {
                }
            }

            dismiss()
        }
    }

}