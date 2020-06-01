package com.chen.basemodule.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

public enum AppStatusUtil {

    INS;

    private boolean isBackground = false;

    private Context context;

    public void init(Context context) {
        this.context = context;
    }

    public boolean isBackToFore() {
        return isBackground && !checkBackground();
    }

    public boolean checkBackground() {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return isBackground = true;
            }
        }
        return isBackground = false;
    }
}
