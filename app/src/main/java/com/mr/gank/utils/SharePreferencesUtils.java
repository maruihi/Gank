package com.mr.gank.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mr.gank.App;


/**
 * SharedPreferences 数据保存
 * Author： by MR on 2017/3/15.
 */
public class SharePreferencesUtils {

    private static final String NAME = "FSManage";
    private static SharedPreferences sp = null;
    private static Context context = App.appContext;

    /**
     * 存布尔值
     */
    public static void putBoolean(String key, boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 取布尔值
     */
    public static boolean getBoolean(String key,
                                     boolean defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * 存Strings
     */
    public static void putString(String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * 取String
     */
    public static String getString(String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    /**
     * 存int
     */
    public static void putInt(String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 取int
     */
    public static int getInt(String key, int defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    /**
     * 存Float
     */
    public static void putFloat(String key, Float value) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putFloat(key, value).commit();
    }

    /**
     * 取Float
     */
    public static Float getFloat(String key, Float defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return sp.getFloat(key, defValue);
    }
}
