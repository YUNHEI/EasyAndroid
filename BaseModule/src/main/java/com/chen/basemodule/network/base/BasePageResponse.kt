package com.chen.basemodule.network.base

/**
 *  Created by chen on 2019/6/14
 **/
class BasePageResponse<K>(val total: Int = 0, val count: Int = 0) :BaseResponse<List<K>>()