package com.example.liuwen.two.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/15 10:37
 * desc   :
 */
public class DateTimeUtils {


    /**
     * 得到今天的日期
     *
     * @return
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 获取当前时间的Date对象
     *
     * @return
     */
    public static Date getCurrentDateObject() {
        return new Date(System.currentTimeMillis());
    }

    public static String getCurrentTimeExactToSecond() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
