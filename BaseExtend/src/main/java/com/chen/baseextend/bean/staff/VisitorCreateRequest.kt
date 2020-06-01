package com.chen.baseextend.bean.staff

import com.chen.basemodule.network.base.BaseRequest

/**
 * @author alan
 * @date 2019-05-16
 */
class VisitorCreateRequest(
        var visitPlace: String? = null,
        var name: String? = null,
        var cardNum: String? = null,
        var mobile: String? = null,
        var visitDate: String? = null,
        var visitCompany: String? = null,
        var visitorsCount: String? = null,
        var receiver: String? = null,
        var visitReason: String? = null
) : BaseRequest()