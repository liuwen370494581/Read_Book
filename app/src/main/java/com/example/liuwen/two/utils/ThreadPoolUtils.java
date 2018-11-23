package com.example.liuwen.two.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/23 09:29
 * desc   : 线程池的单列类 如果要做延时操作可以使用这个类
 */
public class ThreadPoolUtils {


    private static ThreadPoolUtils instance;
    private static ThreadPoolExecutor threadPool;

    private ThreadPoolUtils() {

    }

    public static ThreadPoolUtils getInstance() {
        if (null == instance) {
            synchronized (ThreadPoolUtils.class) {
                if (null == instance) {
                    instance = new ThreadPoolUtils();
                }
            }
        }
        return instance;
    }


    public static void init() {
        threadPool = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                new ThreadPoolExecutor.DiscardOldestPolicy());

    }

    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }
}
