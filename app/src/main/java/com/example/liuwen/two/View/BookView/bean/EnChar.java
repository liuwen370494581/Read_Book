package com.example.liuwen.two.View.BookView.bean;

import android.graphics.Color;

/*
* create by bifan-wei
* 2017-11-13
*/
public class EnChar extends TxtChar {
    public static int DefaultTextColor = Color.parseColor("#45a1cd");
    public EnChar(char aChar) {
        super(aChar);
    }
    @Override
    public int getTextColor() {
        return DefaultTextColor;
    }

    @Override
    public int getCharType() {
        return Char_En;
    }
}
