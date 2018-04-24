package com.rt.sm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.rt.sm.common.MyApp;


public class SharedPreferenceHelper {
    private static final String TAG = "SharedPreferenceHelper";
    private static final String SP_NAME = "App_Config";
    private static SharedPreferences sp = MyApp.APP_CONTEXT
            .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    private static Editor editor = sp.edit();
    /**
     * 查询用户账号
     */
    private static final String USERNUM = "usernum";
    /**
     * 查询用户密码
     */
    private static final String USERPWD = "userpwd";

    /**
     * 是否记录账号密码
     */
    private static final String REMEMBER = "remember";
    /**
     * 是否启动引导页
     */
    private static final String IS_FIRST_START_APP = "isFirstStartApp";
    /**
     * 查询登陆的token
     */
    private static final String TOKEN = "token";
    /**
     * 查询登陆的token time
     */
    private static final String TIME = "time";
    /**
     * 记录当前的ble address
     */
    private static final String ADDRESS = "address";
    /**
     * 存储token
     */
    public static void setAddress(String value) {
        commit(ADDRESS, value);
    }

    /**
     * 查询token
     */
    public static String getAddress() {
        return getStringValue(ADDRESS);
    }


    /**
     * 存储token time
     */
    public static void setTime(long value) {
        commit(TIME, value);
    }

    /**
     * 查询token time
     */
    public static long getTime() {
        return getLongValue(TIME);
    }

    /**
     * 存储token
     */
    public static void setToken(String value) {
        commit(TOKEN, value);
    }

    /**
     * 查询token
     */
    public static String getToken() {
        return getStringValue(TOKEN);
    }

    /**
     * 是否记录账号密码
     */
    public static void setRemember(boolean value) {
        commit(REMEMBER, value);
    }

    /**
     * 是否记录账号密码
     */
    public static boolean getRemember() {
        return getBooleanValue(REMEMBER);
    }

    /**
     * 是否启动引导页
     */
    public static void setIsFirstStartApp(boolean value) {
        commit(IS_FIRST_START_APP, value);
    }

    /**
     * 是否启动引导页
     */
    public static boolean getIsFirstStartApp() {
        return getBooleanValue(IS_FIRST_START_APP);
    }

    /**
     * 存储用户账号
     */
    public static void setUsernum(String value) {
        commit(USERNUM, value);
    }

    /**
     * 查询用户账号
     */
    public static String getUsernum() {
        return getStringValue(USERNUM);
    }

    /**
     * 存储用户密码
     */
    public static void setUserpwd(String value) {
        commit(USERPWD, value);
    }

    /**
     * 查询用户密码
     */
    public static String getUserpwd() {
        return getStringValue(USERPWD);
    }

    private static void commit(String key, String value) {
        editor.putString(key, value);
        editor.commit();
        Logger.d(TAG, "commit : " + key + " value : " + value);
    }

    private static void commit(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
        Logger.d(TAG, "commit : " + key + " value : " + value);
    }

    private static void commit(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
        Logger.d(TAG, "commit : " + key + " value : " + value);
    }

    private static long getLongValue(String key) {
        long enabled = sp.getLong(key, 0);
        Logger.d(TAG, "key : " + key + " enabled : " + enabled);
        return enabled;
    }

    private static String getStringValue(String key) {
        String enabled = sp.getString(key, "");
        Logger.d(TAG, "key : " + key + " enabled : " + enabled);
        return enabled;
    }

    private static boolean getBooleanValue(String key) {
        boolean enabled = sp.getBoolean(key, false);
        Logger.d(TAG, "key : " + key + " enabled : " + enabled);
        return enabled;
    }

}
