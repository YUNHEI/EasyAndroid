package com.chen.baseextend.extend.context

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.exception.NoRouteFoundException
import com.alibaba.android.arouter.facade.enums.LaunchType
import com.alibaba.android.arouter.facade.enums.SwipeType
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.chen.baseextend.base.activity.*
import com.chen.basemodule.basem.BaseFragment
import com.chen.basemodule.basem.FragmentCache.preFragment
import com.chen.basemodule.extend.FRAGMENT_SWIPE_TYPE
import java.io.Serializable
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

/**
 *  Created by chen on 2019/6/10
 **/
fun Context.intent(fragmentClass: KClass<*>? = null, vararg args: Pair<String, Any?>,
                   fragmentArg: Bundle = Bundle(), route: String? = null, type: LaunchType? = null, flags: Int? = null): Pair<Intent, LaunchType> {

    var launchType: LaunchType? = type

    return Intent().apply {

        require(!(fragmentClass == null && route == null)) { "页面跳转错误，fragment的class name 和 route 不能同时为空" }

        if (flags != null) {
            this@apply.flags = flags
        }

        args.forEach {
            fragmentArg.run {
                it.second?.run {
                    when (this) {
                        is Int -> putInt(it.first, this)
                        is Short -> putShort(it.first, this)
                        is Long -> putLong(it.first, this)
                        is Float -> putFloat(it.first, this)
                        is Double -> putDouble(it.first, this)
                        is CharSequence -> putCharSequence(it.first, this)
                        is Parcelable -> putParcelable(it.first, this)
                        is Serializable -> putSerializable(it.first, this)
                        is String -> putString(it.first, this)
                        else -> {
                            putString(it.first, Gson().toJson(this))
                        }
                    }
                }
            }
        }
        //默认启用侧滑
        fragmentArg.putSerializable(FRAGMENT_SWIPE_TYPE, SwipeType.FROM_LEFT)


        var path: String? = null

        fragmentClass?.run {
            path = jvmName.replace("com.chen.", "/").replace('.', '/')
        }

        route?.run {
            path = this
        }

        path?.run {
            ARouter.getInstance().build(this).run {
                try {
                    LogisticsCenter.completion(this)
                } catch (e: NoRouteFoundException) {
                    e.printStackTrace()
                }

                if (launchType == null) {
                    launchType = getLaunchType()
                }

                fragmentArg.putSerializable(FRAGMENT_SWIPE_TYPE, swipeType)
                with(fragmentArg)

                navigation()?.run {
                    preFragment = this as BaseFragment
                } ?: throw NullPointerException("页面：${path} 未添加 @Launch 注解")
            }
        }

        if (launchType == null) {
            launchType = LaunchType.STANDARD
        }

        component = when (launchType!!) {
            LaunchType.STANDARD -> {
                ComponentName(this@intent, BaseStandardActivity::class.java)
            }
            LaunchType.COVER -> {
                ComponentName(this@intent, BaseCoverActivity::class.java)
            }
            LaunchType.INPUT -> {
                ComponentName(this@intent, BaseInputActivity::class.java)
            }
            LaunchType.SINGLE_TOP -> {
                ComponentName(this@intent, BaseSingleTopActivity::class.java)
            }
            LaunchType.FULLSCREEN -> {
                ComponentName(this@intent, BaseFullActivity::class.java)
            }
        }
    } to launchType!!
}

fun Context.startPage(fragmentClass: KClass<*>? = null, vararg args: Pair<String, Any?>, bundle: Bundle = Bundle(),
                      route: String? = null, type: LaunchType? = null, requestCode: Int? = null, flags: Int? = null,
                      option: Bundle? = null) {

    intent(fragmentClass, args = *args, fragmentArg = bundle, route = route, type = type, flags = flags).run {

        if (this is FragmentActivity) {
            if (requestCode == null || requestCode == -1) {
                startActivity(first, option)
            } else {
                startActivityForResult(first, requestCode, option)
            }
        } else {
            startActivity(first, option)
        }
    }
}
