package com.chen.baseextend.util

import com.chen.baseextend.bean.mz.UserInfoBean
import com.chen.basemodule.util.preference.PreferenceHolder

/**
 *  Created by 86152 on 2020-03-14
 **/
object ExtendPreference  : PreferenceHolder() {

    var USER_INFO : UserInfoBean? by bindToPreferenceFieldNullable()

    var LAST_USER_INFO : UserInfoBean? by bindToPreferenceFieldNullable()
}