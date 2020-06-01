package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/6/20
 **/
class MsgInformation(
        val analyzeStatus: Int?,
        val coverList: List<String>?,
        val crtWay: Int?,
        val id: String,
        val infoType: Int?,
        val recommend: Int?,
        val status: Int?,
        val title: String?,
        val videoUrl: String?,
        val remark: String?,
        val top: Int?,
        val typeId: String?
): BaseBean()