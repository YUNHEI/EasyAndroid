package com.chen.baseextend.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.chen.baseextend.BaseExtendApplication
import com.chen.basemodule.extend.toastSuc

/**
 *  Created by 86152 on 2020-04-18
 **/
object TextUtil {

    fun copy(content: String) {

        //获取剪贴板管理器：
        val cm = BaseExtendApplication.app?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
        val mClipData = ClipData.newPlainText(content, content)
        // 将ClipData内容放到系统剪贴板里。
        cm.primaryClip = mClipData

        "已复制".toastSuc()
    }
}