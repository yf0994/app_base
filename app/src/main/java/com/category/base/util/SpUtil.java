package com.category.base.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.category.base.BaseApplication;

/**
 * Created by fengyin on 16-4-21.
 */
public class SpUtil {

    public static String getString(String spName, String key){
        return getSharedPreferences(spName).getString(key, "");
    }

    public static void putString(String spName, String key, String value){
        getSharedPreferences(spName).edit().putString(key, value).commit();
    }

    public static int getInt(String spName, String key){
        return getSharedPreferences(spName).getInt(key, -1);
    }

    public static void putInt(String spName, String key, int value){
        getSharedPreferences(spName).edit().putInt(key, value).commit();
    }

    public static float getFloat(String spName, String key){
        return getSharedPreferences(spName).getFloat(key, -1);
    }

    public static void putFloat(String spName, String key, float value){
        getSharedPreferences(spName).edit().putFloat(key, value).commit();
    }

    public static long getLong(String spName, String key){
        return getSharedPreferences(spName).getLong(key, -1);
    }

    public static void putLong(String spName, String key, long value){
        getSharedPreferences(spName).edit().putLong(key, value).commit();
    }

    public static boolean getBoolean(String spName, String key){
        return getSharedPreferences(spName).getBoolean(key, true);
    }

    public static void putBoolean(String spName, String key, boolean value){
        getSharedPreferences(spName).edit().putBoolean(key, value).commit();
    }

    public static void remove(String spName, String key){
        getSharedPreferences(spName).edit().remove(key).commit();
    }

    public static void removeAll(String spName){
        getSharedPreferences(spName).edit().clear().commit();
    }

    private static SharedPreferences getSharedPreferences(String spName){
        return BaseApplication.getContext()
                .getSharedPreferences(spName, Context.MODE_PRIVATE);
    }
}
