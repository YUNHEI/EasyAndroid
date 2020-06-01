package com.chen.basemodule.network.base

/**
 *  Created by chen on 2019/6/3
 **/
open class BasePageRequest(
        var limit: Int = 20,
        var lastId: String? = null,
        var page: Int? = 1,
        var pageSize: Int? = 20
) : BaseRequest() {
    var order: String? = null
}