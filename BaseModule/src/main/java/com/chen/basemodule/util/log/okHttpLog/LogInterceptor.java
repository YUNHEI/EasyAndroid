package com.chen.basemodule.util.log.okHttpLog;

import com.chen.basemodule.util.log.LogUtil;

/**
 * author: baiiu
 * date: on 16/8/31 19:40
 * description:
 */
public class LogInterceptor implements HttpLoggingInterceptor.Logger {

    public static String INTERCEPTOR_TAG_STR = "OkHttp";

    public LogInterceptor() {
    }

    public LogInterceptor(String tag) {
        INTERCEPTOR_TAG_STR = tag;
    }

    @Override public void log(String message, @LogUtil.LogType int type) {
        LogUtil.printLog(false, type, INTERCEPTOR_TAG_STR, message);
    }
}
