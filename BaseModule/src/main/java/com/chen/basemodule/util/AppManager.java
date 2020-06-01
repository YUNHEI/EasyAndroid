package com.chen.basemodule.util;

import android.app.Activity;
import android.content.Context;

import java.util.Iterator;
import java.util.Stack;


/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 *
 * @version 1.0
 * @created 2014-10-14
 */
public class AppManager {

    private static Stack<Activity> activityStack;

    private static Stack<Activity> cls;

    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        Activity activity = null;
        try {// 捕捉NoSuchElementException异常
            activity = activityStack.lastElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activity;
    }

    public static void addFlow(Activity c) {

        if (cls == null) cls = new Stack<>();

        if (c != null) cls.add(c);
    }

    public static void removeFlow(Activity c) {

        if (cls != null) cls.remove(c);
    }


    public static void endFlowTo(Activity c) {
        if (c != null && cls.contains(c)) {
            for (Activity cl : cls) {
                if (cl != c) {
                    finishActivity(cl);
                    cls.remove(cl);
                } else {
                    break;
                }
            }
        }
    }

    public static boolean isActivityExist(Class<?> c) {

        if (c != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(c)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void endFlow() {
        if (cls != null) {
            for (Activity cl : cls) {
                finishActivity(cl);
            }
            cls.clear();
        }
    }


    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishActivity() {
        try {// 捕捉NoSuchElementException异常
            Activity activity = activityStack.lastElement();
            finishActivity(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 移除当前Activity（堆栈中最后一个压入的）
     */
    public static void removeActivity() {
        Activity activity = activityStack.lastElement();
        removeActivity(activity);
    }

    /**
     * 移除指定的Activity
     */
    public static void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 移除指定类名的Activity
     */
    public static void removeActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                removeActivity(activity);
            }
        }
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity a = iterator.next();
            if (a.getClass().equals(cls)) {
                a.finish();
            }
        }
    }

    /**
     * 结束顶部的所有Activity
     */
    public static void finishAllButOne(Class<?> cls) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                if (activityStack.get(i).getClass().equals(cls)) continue;
                activityStack.get(i).finish();
                // finishActivity(activityStack.get(i));// Note：这种方式会出现数组越界，原因未知
            }
        }
    }

    /**
     * 结束顶部的所有Activity
     */
    public static void finishAllBut(Class<?>... clss) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                for (Class<?> aClass : clss) {
                    if (activityStack.get(i).getClass().equals(aClass)) continue;
                    activityStack.get(i).finish();
                }
            }
        }
    }

    public static Activity finishAllButOneRetain(Class<?> cls) {
        Activity retainActivity = null;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                if (activityStack.get(i).getClass().equals(cls)) {
                    retainActivity = activityStack.get(i);
                    continue;
                }
                activityStack.get(i).finish();
                // finishActivity(activityStack.get(i));// Note：这种方式会出现数组越界，原因未知
            }
        }
        return retainActivity;
    }


    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public static void AppExit(Context context) {
        try {
            finishAllActivity();
            //ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            //activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}