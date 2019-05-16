package com.example.liuwen.two.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.example.liuwen.two.Base.AppInfo;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/09/25 17:07
 * desc   :
 */
public class ScreenUtils {


    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    //=============================================================
    public static int dpToPx(int dp) {
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
    public static int spToPx(int sp){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,metrics);
    }


    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = AppInfo
                .getContext()
                .getResources()
                .getDisplayMetrics();
        return metrics;
    }
}
