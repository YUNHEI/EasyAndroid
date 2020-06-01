package com.chen.basemodule.constant

import com.chen.basemodule.extend.dp2px
import com.chen.basemodule.util.preference.PreferenceHolder

object BasePreference : PreferenceHolder() {

    const val SEED_KEY = "9eb188f476d8c28e6f132743ff9bcd24"

    const val KEY_USER_INFO = "key_user_info"

    const val KEY_WALLET_INFO = "key_wallet_info"


    /**下载记录id*/
    var _DOWNLOAD_ID: Long by bindToPreferenceField(0)

    /**下载包名*/
    var _DOWNLOAD_APK_NAME: String? by bindToPreferenceFieldNullable()

    /**刘海高度*/
    var _NOTCH_HEIGHT: Int by bindToPreferenceField(0)

    /**状态栏高度*/
    var _STATUS_BAR_HEIGHT: Int by bindToPreferenceField(0)

    /**键盘高度*/
    var _KEYBOARD_HEIGHT: Int by bindToPreferenceField(0)


    /**用户令牌*/
    var USER_TOKEN: String? by bindToPreferenceFieldNullable()

    /**用户id*/
    var USER_ID: String by bindToPreferenceField("")

    /**用户登录状态*/
    var LOGIN_STATE: Boolean by bindToPreferenceField(false)

    /**第一次打开*/
    var _FIRST_START: Boolean by bindToPreferenceField(true)

    /**第一次打开教程*/
    var _GUIDE_FIRST_START: Boolean by bindToPreferenceField(true)

    /**安全联盟唯一表示用户令牌*/
    var _OAID: String by bindToPreferenceField("")

    /**下载页地址 1*/
    var _DOWNLOAD_PAGE_URL: String by bindToPreferenceField("")

    /**隐私页 2*/
    var _PRIVATE_RULE_URL: String by bindToPreferenceField("")

    /**任务类型说明 3*/
    var _PROJECT_TYPE_DES_URL: String by bindToPreferenceField("")

    /**发单规则 4*/
    var _PUBLISH_PROJECT_RULE_URL: String by bindToPreferenceField("")

    /**接单规则 5*/
    var _RECEIVED_PROJECT_RULE_URL: String by bindToPreferenceField("")

    /**免责申明 6*/
    var _RIGHT_RULE_URL: String by bindToPreferenceField("")

    /**用户协议 7*/
    var _USER_RULE_URL: String by bindToPreferenceField("")

    /**任务分享 8*/
    var _PROJECT_SHARE_URL: String by bindToPreferenceField("")

    /**邀请码 */
    var _INVITE_CODE: String by bindToPreferenceField("")

    /** 用户授权*/
    var _USER_ACCEPT_RULE: Boolean by bindToPreferenceField(false)

    /** 待处理按钮坐标*/
    var _WAIT_PROCESS_Y: Int by bindToPreferenceField(dp2px(66))
    var _WAIT_PROCESS_X: Int by bindToPreferenceField(dp2px(15))


}
