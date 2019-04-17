package com.example.liuwen.two.engine;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import java.util.concurrent.Executor;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2019/04/16 16:30
 * desc   :不同平台的线程切换工具，java不知道怎么弄，目前只有Android有用
 */
public class Platform {

    private static final Platform PLATFORM=findPlatform();


    public static Platform get() {
        return PLATFORM;
    }

    private static Platform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return new Android();
            }
        } catch (ClassNotFoundException ignored) {
        }
        try {
            Class.forName("java.util.Optional");
            return new Java8();
        } catch (ClassNotFoundException ignored) {
        }
        return new Platform();
    }

    @Nullable
    public Executor defaultCallbackExecutor() {
        return command -> command.run();
    }

    static class Java8 extends Platform {

    }

    static class Android extends Platform {

        @Override
        public Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        static class MainThreadExecutor implements Executor {
            private final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable r) {
                handler.post(r);
            }
        }
    }
}
