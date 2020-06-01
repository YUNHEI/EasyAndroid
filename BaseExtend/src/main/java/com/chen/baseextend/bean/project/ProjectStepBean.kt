package com.chen.baseextend.bean.project

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/7/26
 **/
data class ProjectStepBean(
        var taskId: String? = null, //任务id
        val stepType: Int, //步骤类型 1.网址 2.二维码 3.数据复制 4.图文说明 5.验证图 6.收集信息
        var imageUrl: String? = null, //图片地址
        var imageLocalUrl: String? = null, //图片本地地址
        var imageVerifyUrl: String? = null, //验证图片地址
        var imageVerifyLocalUrl: String? = null, //验证图片本地地址
        var isVerifyMode: Boolean = false, //是否验证模式
        var stepDesc: String? = null, //步骤描述
        var commonField: String? = null, //公共字段 存网址 数据等内容
        var index: Int? = null //排序
) : BaseBean()