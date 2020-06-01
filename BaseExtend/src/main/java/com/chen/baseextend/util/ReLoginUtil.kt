package com.chen.baseextend.util

import android.content.Context
import com.chen.baseextend.extend.context.startPage
import com.chen.baseextend.extend.startPage
import com.chen.baseextend.route.UserModuleRoute.USER_MODULE_LOGIN
import com.chen.basemodule.allroot.RootFragment
import com.chen.basemodule.constant.BasePreference

/**
 * @author alan
 * @date 2019-07-04
 */
object ReLoginUtil {


    fun reLogin(context: Context?) {

        ExtendPreHelper.logout()

        context?.startPage(route = USER_MODULE_LOGIN)

    }


    fun requestLogin(fragment: RootFragment): Boolean {

        return if (BasePreference.LOGIN_STATE) {
            true
        } else {
            fragment.startPage(route = USER_MODULE_LOGIN)
            false
        }
    }

    fun requestLogin(context: Context): Boolean {

        return if (BasePreference.LOGIN_STATE) {
            true
        } else {
            context.startPage(route = USER_MODULE_LOGIN)
            false
        }
    }

}