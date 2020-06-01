package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

/**
 * @author alan
 * @date 2018/11/8
 */
class OperationDataBean(
        var appCommentCount: Long = 0,
        var appCommentCountCompareLastWeek: Long = 0,
        var appCommentCountCompareYesterday: Long = 0,
        var appRegCount: Long = 0,
        var appRegCountCompareLastWeek: Long = 0,
        var appRegCountCompareYesterday: Long = 0,
        var appVerifyCount: Long = 0,
        var appVerifyCountCompareLastWeek: Long = 0,
        var appVerifyCountCompareYesterday: Long = 0,
        var appViews: Long = 0,
        var appViewsCompareLastWeek: Long = 0,
        var appViewsCompareYesterday: Long = 0,
        var articleCount: Long = 0,
        var articleCountCompareLastWeek: Long = 0,
        var articleCountCompareYesterday: Long = 0,
        var articleViewCount: Long = 0,
        var articleViewCountCompareLastWeek: Long = 0,
        var articleViewCountCompareYesterday: Long = 0,
        var crtDate: String? = null,
        var userLoginCount: Long = 0,
        var userLoginCountCompareLastWeek: Long = 0,
        var userLoginCountCompareYesterday: Long = 0,
        var totalShareCount: Long = 0,
        var totalShareCountCompareYesterday: Long = 0,
        var totalShareCountCompareLastWeek: Long = 0,
        var collectCount: Long = 0,
        var collectCountCompareYesterday: Long = 0,
        var collectCountCompareLastWeek: Long = 0
) : BaseBean()