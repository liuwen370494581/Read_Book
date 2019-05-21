package com.example.liuwen.two.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.liuwen.two.Base.AppInfo;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2019/05/16 15:03
 * desc   :
 */
public class SharedPreUtils {

    private static final String SHARED_NAME = "MY_NOVEL";
    private static SharedPreUtils sInstance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedWritable;


    private SharedPreUtils() {
        sharedPreferences = AppInfo.getContext().getSharedPreferences(SHARED_NAME, Context.MODE_MULTI_PROCESS);
        sharedWritable = sharedPreferences.edit();
    }

    public static SharedPreUtils getInstance() {
        if (sInstance == null) {
            synchronized (SharedPreUtils.class) {
                if (sInstance == null) {
                    sInstance = new SharedPreUtils();
                }
            }
        }
        return sInstance;
    }


    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public int getInt(String key, int def) {
        return sharedPreferences.getInt(key, def);
    }

    public boolean getBoolean(String key, boolean def) {
        return sharedPreferences.getBoolean(key, def);
    }


    public void putString(String key, String value) {
        sharedWritable.putString(key, value);
        sharedWritable.commit();
    }

    public void putInt(String key, int value) {
        sharedWritable.putInt(key, value);
        sharedWritable.commit();
    }

    public void putBoolean(String key, boolean value) {
        sharedWritable.putBoolean(key, value);
        sharedWritable.commit();
    }

}
