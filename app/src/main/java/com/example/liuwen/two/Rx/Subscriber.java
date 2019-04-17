package com.example.liuwen.two.Rx;

import android.support.annotation.NonNull;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2019/04/16 16:27
 * desc   :
 */
public interface Subscriber<T> {

    void onFinish(@NonNull T t);

    void onError(@NonNull Exception e);

    void onMessage(@NonNull String message);

    void onProgress(int progress);
}
