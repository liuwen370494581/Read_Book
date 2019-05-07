package com.example.liuwen.two.View.BookView.utils;

import android.util.Log;
import com.example.liuwen.two.View.BookView.interfaces.ITxtReaderLoggerListener;

/**
 * Created by HP on 2017/11/15.
 */

public class ELogger {
    private static ITxtReaderLoggerListener l;
    public static void setLoggerListener(ITxtReaderLoggerListener l) {
        ELogger.l = l;
    }
    public static void log(String tag, String msg) {
        Log.e(tag, msg + "");
        if (l != null) {
            l.onLog(tag, msg + "");
        }
    }

}
