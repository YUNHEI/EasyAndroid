package com.chen.baseextend.extend

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.enums.LaunchType
import com.chen.baseextend.extend.context.intent
import kotlin.reflect.KClass

/**
 *  Created by chen on 2019/6/10
 **/
fun Fragment.startPage(fragmentClass: KClass<*>? = null, vararg args: Pair<String, Any?>, bundle: Bundle = Bundle(),
                       route: String? = null, type: LaunchType? = null, requestCode: Int? = null, option: Bundle? = null) {

    activity?.intent(fragmentClass, args = *args, fragmentArg = bundle, route = route, type = type)?.run {
        if (requestCode == null || requestCode == -1) {
            startActivity(this, option)
        } else {
            startActivityForResult(this, requestCode, option)
        }
    }
}

