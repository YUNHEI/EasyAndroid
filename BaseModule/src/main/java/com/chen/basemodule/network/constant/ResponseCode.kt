package com.chen.basemodule.network.constant

/**
 * Created by chen on 2019/4/29
 */
object ResponseCode {

    /**
     * 200<= code && code < 300,操作成功
     */
    const val SUC = 200


    /**
     * 300<= code && code < 400,操作失败，可修改后重试
     */
    const val FAIL = 300


    /**
     * 400<= code && code < 500, 操作异常，需要修复程序
     */
    const val ERROR = 400

}
