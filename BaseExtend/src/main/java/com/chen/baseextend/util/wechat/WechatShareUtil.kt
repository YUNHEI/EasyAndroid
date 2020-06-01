package com.chen.baseextend.util.wechat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.chen.baseextend.BaseExtendApplication
import com.chen.baseextend.R
import com.chen.basemodule.util.ImageUtil
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *  Created by chen on 2019/12/13
 **/
object WechatShareUtil {

    fun share(shareScene: Int = SendMessageToWX.Req.WXSceneSession, url: String, msgTitle: String = "测试title", msgDes: String = "测试description") {
        GlobalScope.launch {
            SendMessageToWX.Req().run {
                transaction = "webpage${System.currentTimeMillis()}"
                message = WXMediaMessage(WXWebpageObject().apply { webpageUrl = url }).apply {
                    title = msgTitle
                    description = msgDes

                    val b = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(BaseExtendApplication.app!!.resources, R.mipmap.logo), 200, 200, true)

                    thumbData = ImageUtil.bmpToByteArray(b, true)
                }
                scene = shareScene
                BaseExtendApplication.wxapi.sendReq(this)
            }
        }
    }
}