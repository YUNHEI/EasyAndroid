package com.chen.basemodule.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.chen.basemodule.BaseModuleLoad;

import java.util.HashSet;
import java.util.Set;

import devliving.online.securedpreferencestore.SecuredPreferenceStore;

/**
 * PreferencesUtils, easy to get or put data
 */
public class PrefUtils {

    public static SecuredPreferenceStore.Editor batch() {
        SecuredPreferenceStore ss = SecuredPreferenceStore.getSharedInstance();
        return ss.edit();
    }


    /**
     * put string preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new launchType for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static void putString(String key, String value) {
        SecuredPreferenceStore ss = SecuredPreferenceStore.getSharedInstance();
        SecuredPreferenceStore.Editor editor = ss.edit();
        if (value == null) {
            editor.remove(key);
        } else {
            editor.putString(key, value);
        }
        editor.apply();
    }

    /**
     * get string preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference launchType if it exists, or null. Throws ClassCastException if there is a preference with this
     * name that is not a string
     * @see #getString(String, String)
     */
    public static String getString(String key) {
        return getString(key, null);
    }

    /**
     * get string preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference launchType if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a string
     */
    public static String getString(String key, String defaultValue) {
        SecuredPreferenceStore ss = SecuredPreferenceStore.getSharedInstance();
        return ss.getString(key, defaultValue);
    }

    public static Set<String> getStringSet(String key) {
        SharedPreferences ss = BaseModuleLoad.INSTANCE.getApp().getSharedPreferences(key, Context.MODE_PRIVATE);
        return ss.getStringSet(key, new HashSet<>());
    }

    public static void putStringSet(String key, Set<String> sets) {
        SharedPreferences ss = BaseModuleLoad.INSTANCE.getApp().getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ss.edit();
        edit.putStringSet(key, sets);
        edit.apply();
    }

    /*不加密，避免性能问题*/
    public static void appendStringSet(String key, String set) {
        SharedPreferences ss = BaseModuleLoad.INSTANCE.getApp().getSharedPreferences(key, Context.MODE_PRIVATE);
        Set<String> stringSet = ss.getStringSet(key, new HashSet<>());
        HashSet<String> strings = new HashSet<>(stringSet);
        SharedPreferences.Editor edit = ss.edit();
        strings.add(set);
        edit.putStringSet(key, strings);
        edit.apply();
    }

    /**
     * put int preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new launchType for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static void putInt(String key, int value) {
        SharedPreferences ss = BaseModuleLoad.INSTANCE.getApp().getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ss.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * get int preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference launchType if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a int
     * @see #getInt(String, int)
     */
    public static int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * get int preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference launchType if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a int
     */
    public static int getInt(String key, int defaultValue) {
        SharedPreferences ss = BaseModuleLoad.INSTANCE.getApp().getSharedPreferences(key, Context.MODE_PRIVATE);
        return ss.getInt(key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new launchType for the preference
     * @return True if the new values were successfully written to persistent storage.
     */

    public static boolean putLong(String key, long value) {
        SecuredPreferenceStore ss = SecuredPreferenceStore.getSharedInstance();
        SecuredPreferenceStore.Editor editor = ss.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference launchType if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a long
     * @see #getLong(String, long)
     */
    public static long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * get long preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference launchType if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a long
     */
    public static long getLong(String key, long defaultValue) {
        SecuredPreferenceStore ss = SecuredPreferenceStore.getSharedInstance();
        return ss.getLong(key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new launchType for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(Context context, String key, float value) {
        SecuredPreferenceStore ss = SecuredPreferenceStore.getSharedInstance();
        SecuredPreferenceStore.Editor editor = ss.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference launchType if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a float
     * @see #getFloat(Context, String, float)
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference launchType if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a float
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SecuredPreferenceStore ss = SecuredPreferenceStore.getSharedInstance();
        return ss.getFloat(key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new launchType for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(String key, boolean value) {
        SecuredPreferenceStore ss = SecuredPreferenceStore.getSharedInstance();
        SecuredPreferenceStore.Editor editor = ss.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param key The name of the preference to retrieve
     * @return The preference launchType if it exists, or false. Throws ClassCastException if there is a preference with this
     * name that is not a boolean
     * @see #getBoolean(String, boolean)
     */
    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * get boolean preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference launchType if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a boolean
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        SecuredPreferenceStore ss = SecuredPreferenceStore.getSharedInstance();
        return ss.getBoolean(key, defaultValue);
    }

    public static boolean remove(String... keys) {
        SecuredPreferenceStore ss = SecuredPreferenceStore.getSharedInstance();
        SecuredPreferenceStore.Editor editor = ss.edit();
        for (String key : keys) editor.remove(key);
        return editor.commit();
    }

    public static boolean removeAll() {
        SecuredPreferenceStore ss = SecuredPreferenceStore.getSharedInstance();
        SecuredPreferenceStore.Editor editor = ss.edit();
        return editor.clear().commit();
    }
}
