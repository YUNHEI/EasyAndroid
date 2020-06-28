package com.chen.baseextend.extend.context

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.exception.NoRouteFoundException
import com.alibaba.android.arouter.facade.annotation.Launch
import com.alibaba.android.arouter.facade.enums.LaunchType
import com.alibaba.android.arouter.facade.enums.SwipeType
import com.alibaba.android.arouter.launcher.ARouter
import com.chen.baseextend.base.activity.*
import com.chen.basemodule.basem.BaseFragment
import com.chen.basemodule.basem.fragmentQueue
import com.chen.basemodule.extend.FRAGMENT_SWIPE_TYPE
import com.google.gson.Gson
import java.io.Serializable
import kotlin.reflect.KClass

/**
 *  Created by chen on 2019/6/10
 **/
fun Context.intent(fragmentClass: KClass<*>? = null, vararg args: Pair<String, Any?>, fragmentArg: Bundle = Bundle(), route: String? = null,
                   type: LaunchType? = null, flags: Int? = null): Intent {

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
                        is Byte -> putByte(it.first, this)
                        is Char -> putChar(it.first, this)
                        is Size -> putSize(it.first, this)
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

        fragmentClass?.java?.run {

            getAnnotation(Launch::class.java)?.let {

                if (launchType == null) {
                    launchType = it.launchType
                }

                fragmentArg.putSerializable(FRAGMENT_SWIPE_TYPE, it.swipeType)

            }

            (getConstructor().newInstance() as? BaseFragment)?.run {
                arguments = fragmentArg
                fragmentQueue.offer(this)
            } ?: throw NullPointerException("页面：${fragmentClass} 不存在")

        }

        route?.run {
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

                (destination.getConstructor().newInstance() as? BaseFragment)?.run {
                    arguments = fragmentArg
                    fragmentQueue.offer(this)
                } ?: throw NullPointerException("页面：${path} 未添加 @Launch 注解")

            }
        }

        component = when (launchType ?: LaunchType.STANDARD) {
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
    }
}

fun Context.startPage(fragmentClass: KClass<*>? = null, vararg args: Pair<String, Any?>, bundle: Bundle = Bundle(), route: String? = null, type: LaunchType? = null, requestCode: Int? = null, flags: Int? = null, option: Bundle? = null)

        = intent(fragmentClass, args = *args, fragmentArg = bundle, route = route, type = type, flags = flags).run {

            if (this@startPage is FragmentActivity) {
                if (requestCode == null || requestCode == -1) {
                    startActivity(this, option)
                } else {
                    startActivityForResult(this, requestCode, option)
                }
            } else {
                startActivity(this, option)
            }
        }
