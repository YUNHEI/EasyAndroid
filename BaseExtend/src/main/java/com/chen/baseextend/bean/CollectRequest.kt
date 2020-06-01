package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BaseRequest

/**
 * @author alan
 * @date 2018/11/6
 */
class CollectRequest(
        var bizId: String,
        var bizType: Int, // 1-资讯 2-视频 3-言论
        var status: Int
) : BaseRequest()
