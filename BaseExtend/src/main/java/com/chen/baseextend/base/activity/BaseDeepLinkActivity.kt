package com.chen.baseextend.base.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.chen.baseextend.repos.viewmodel.MainViewModel
import com.chen.baseextend.route.StaffRoute.MEETING_ROOM
import com.chen.basemodule.basem.BaseActivity
import com.chen.basemodule.basem.BaseFragment
import com.chen.basemodule.constant.BasePreference
import com.chen.basemodule.extend.toastError

/**
 * deep link activity  添加新的跳转 直接增加相应的键值对 保证只有添加的页面才允许跳转
 *  Created by chen on 2019/6/10
 **/
class BaseDeepLinkActivity : BaseActivity() {

    override val fragment: BaseFragment? = null

    override val contentLayoutId = - 1

    val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java).apply { owner = this@BaseDeepLinkActivity } }

    companion object {

        /*添加对应deep link跳转页Route*/
        val map: MutableMap<String, String> = mutableMapOf(
                /**
                 * 会议室
                 */
                Pair("meeting_room", MEETING_ROOM)

        )


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BasePreference.LOGIN_STATE) {
            intent.data?.getQueryParameter("type")?.run {

                intent.data?.getQueryParameter("id")?.apply {

                } ?: apply {
                    "此页面不支持跳转".toastError()
                    finish()
                }

            } ?: run {
                "此页面不支持跳转".toastError()
                finish()
            }

        } else {

//            ARouter.getInstance()
//                    .build(PATH_LOGIN_ACCOUNT_FRAGMENT)
//                    .withTransition(R.anim.activity_bottom_enter, 0)
//                    .navigation(this)

        }
    }

    override fun onPause() {
        overridePendingTransition(0, 0)
        super.onPause()
    }

}