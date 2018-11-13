package com.example.liuwen.two.listener;

import android.content.Context;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 14:47
 * desc   :
 */
public interface OnHandlerListener {
    void handlerMessage(Message message, WeakReference<Context> reference);
}
