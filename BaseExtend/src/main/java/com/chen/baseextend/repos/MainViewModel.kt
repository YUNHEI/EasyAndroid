package com.chen.baseextend.repos

import android.content.Context
import androidx.lifecycle.LiveData
import com.chen.baseextend.BaseExtendApplication
import com.chen.baseextend.extend.commonFailInterrupt
import com.chen.basemodule.basem.BaseViewModel
import com.chen.basemodule.network.base.BaseResponse
import kotlinx.coroutines.CoroutineScope

/**
 *  Created by chen on 2019/6/6
 **/
open class MainViewModel : BaseViewModel() {

    val database by lazy { BaseExtendApplication.database }

    //service

//    ###################################################################################


    open val userService by lazy { UserRepos.service }

    val projectService by lazy { ProjectRepos.service }

    //广告相关
    val adService by lazy { ADRepos.service }

    //系统相关
    val systemService by lazy { SystemRepos.service }

    val appealService by lazy { AppealRepos.service }

    val activityService by lazy { ActivityRepos.service }


    //repos

    val userRepos by lazy { UserRepos }

    override fun <T> requestData(block: suspend CoroutineScope.() -> BaseResponse<T>,
                                 success: ((response: BaseResponse<T>) -> Unit)?,
                                 fail: ((response: BaseResponse<T>) -> Unit)?,
                                 preHandle: ((response: BaseResponse<T>) -> Unit)?,
                                 successInterrupt: ((context: Context, response: BaseResponse<T>) -> Boolean)?,
                                 failInterrupt: ((context: Context, response: BaseResponse<T>) -> Boolean)?): LiveData<BaseResponse<T>> {

        return super.requestData(block, success, fail, preHandle, successInterrupt, failInterrupt = failInterrupt
                ?: commonFailInterrupt)
    }
}