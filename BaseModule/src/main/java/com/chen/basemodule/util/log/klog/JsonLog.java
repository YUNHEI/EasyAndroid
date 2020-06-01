package com.chen.basemodule.util.log.klog;

import com.chen.basemodule.util.log.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.internal.platform.Platform;

/**
 * Created by zhaokaiqiang on 15/11/18.
 */
public class JsonLog {

    public static void printJson(String tag, String msg, String headString) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(LogUtil.JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(LogUtil.JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        LogUtil.printLine(tag, true);

        message = headString + LogUtil.LINE_SEPARATOR + message;
        String[] lines = message.split(LogUtil.LINE_SEPARATOR);

        StringBuilder builder = new StringBuilder(500);

        for (String line : lines) {
            builder.append(" ");
            builder.append(line);
            builder.append("\n");
        }

        LogUtil.okHttpPrint(Platform.INFO, builder.toString());

        LogUtil.printLine(tag, false);
    }
}
