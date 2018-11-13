package com.example.liuwen.two.utils;

import android.app.Activity;
import android.view.ViewGroup;

import com.example.liuwen.two.R;
import com.irozon.sneaker.Sneaker;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/13 09:20
 * desc   :
 */
public class SneakerUtils {

    public static void setCommonMessage(Activity activity, String title, String message) {
        Sneaker.with(activity)
                .setTitle(title, R.color.white) // Title and title color
                .setMessage(message, R.color.white) // Message and message color
                .setDuration(4000) // Time duration to show
                .autoHide(true) // Auto hide Sneaker view
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
                .setIcon(R.drawable.ic_success, R.color.white, false) // Icon, icon tint color and circular icon view
                .sneak(R.color.main_text_color_focus); // Sneak with background color
    }
}
