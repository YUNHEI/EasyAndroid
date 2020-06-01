package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BaseRequest

/**
 * @author alan
 * @date 2018/11/21
 */
class UpdateUserExtend(
        /**自动分享开关 1-开 */
        var autoPublish: Int,
        var id: String? = null,
        /**是否有运营人员权限 0-无  1-有 */
        var operator: Int = 0,
        var shareCount: Int = 0,
        var publishCount: Int = 0,
        /**不等于null 代表已设置过 推荐 */
        var companyTypeId: String? = null,
        var companyPostId: String? = null,
        var crtTime: String? = null,
        var updTime: String? = null,
        var communityCount: Int = 0,

        /**EaseAndroid号 */
        var userCode: String? = null,

        /**自动分享选择的社区名字 */
        var autoPublishCommunityName: String? = null,
        /**自动分享选择的社区id */
        var autoPublishCommunityId: String? = null,

        /**分享到 微信 权限  0-无  1-有 */
        var shareWeixin: Int = 0,

        /**加入社区 引导  0-未完成  1-已完成 */
        var initialized: Int = 0,

        /**孵化中心是否显示  0-不显示  1-显示 */
        var incubationShowFlag: Int = 0,

        /**创新学院是否显示  0-不显示  1-显示 */
        var collegeShowFlag: Int = 0,

        /**是否入驻用户   1-未入驻  2-已入驻 */
        val locatedStatus : Int ?= 1
) : BaseRequest()
