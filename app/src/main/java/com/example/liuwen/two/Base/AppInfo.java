package com.example.liuwen.two.Base;

import android.app.Application;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.utils.ThreadPoolUtils;

import java.io.File;
import java.util.List;

import GreenDao3.DaoManager;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 14:08
 * desc   :
 */
public class AppInfo extends Application {

    private Book selectedBook = null;


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化greenDao3
        DaoManager.init(this);
        ThreadPoolUtils.init();
    }

}



