package com.example.liuwen.two.Activity;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;

import com.example.liuwen.two.Base.BaseActivity;
import com.example.liuwen.two.R;
import com.irozon.sneaker.Sneaker;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/12 16:40
 * desc   :
 */
public class SourceSettingActivity extends BaseActivity {
    @Override
    protected int setLayoutRes() {
        return R.layout.activity_source_setting;
    }

    @Override
    protected void initView() {
        showLeftView();
        setCenterText("书源");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    public void onClick(View view) {
        Sneaker.with(this)
                .setTitle("Title", R.color.white) // Title and title color
                .setMessage("This is the message.", R.color.white) // Message and message color
                .setDuration(4000) // Time duration to show
                .autoHide(true) // Auto hide Sneaker view
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
                .setIcon(R.drawable.ic_success, R.color.white, false) // Icon, icon tint color and circular icon view
                .sneak(R.color.colorAccent); // Sneak with background color
    }
}
