package com.example.liuwen.two.Action;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.liuwen.two.listener.OnHandlerListener;

import java.lang.ref.WeakReference;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 14:48
 * desc   :
 */
public class MyReadHandler extends Handler {
    private WeakReference<Context> reference;
    private OnHandlerListener mOnHandlerListener;

    public MyReadHandler(Context context, OnHandlerListener onHandlerListener) {
        reference = new WeakReference<>(context);
        mOnHandlerListener = onHandlerListener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        mOnHandlerListener.handlerMessage(msg,reference);
    }
}
