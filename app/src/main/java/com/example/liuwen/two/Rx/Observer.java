package com.example.liuwen.two.Rx;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2019/04/16 16:27
 * desc   :
 */
public interface Observer<T>{

    Disposable subscribe(Subscriber<T> subscriber);
}
