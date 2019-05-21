package com.example.liuwen.two.engine;

import com.example.liuwen.two.utils.SharedPreUtils;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2019/05/16 15:02
 * desc   :  记录阅读页面的配置信息
 */
public class ReadSettingManager {

    private static volatile ReadSettingManager sInstance;
    private SharedPreUtils sharedPreUtils;

    public static final String READ_BG = "read_bg";
    public static final String READ_TEXT_SIZE = "read_text_size";

    public static ReadSettingManager getInstance() {
        if (sInstance == null) {
            synchronized (ReadSettingManager.class) {
                if (sInstance == null) {
                    sInstance = new ReadSettingManager();
                }
            }
        }
        return sInstance;
    }

    private ReadSettingManager() {
        sharedPreUtils = SharedPreUtils.getInstance();
    }

}
