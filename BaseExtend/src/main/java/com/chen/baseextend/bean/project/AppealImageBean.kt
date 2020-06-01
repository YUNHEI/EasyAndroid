package com.chen.baseextend.bean.project

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/8/23
 **/
data class AppealImageBean(
        val index: Int? = null,//排序
        val image : String? = null//步骤类型 1.网址 2.二维码 3.数据复制 4.图文说明 5.验证图 6.收集信息 ,
):BaseBean()