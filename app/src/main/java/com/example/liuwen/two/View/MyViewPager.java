package com.example.liuwen.two.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/08/22 14:02
 * desc   :
 */
public class MyViewPager  extends ViewPager {
    private boolean isSlipping = true;// 可滑动标志位

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isSlipping) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isSlipping) {
            return false;
        }
        return super.onTouchEvent(ev);
    }


    /**
     * 设置它是否滑动
     *
     * @param isSlipping
     */
    public void setSlipping(boolean isSlipping) {
        this.isSlipping = isSlipping;
    }
}
