package com.example.liuwen.two.View.BookView.interfaces;


import com.example.liuwen.two.View.BookView.bean.TxtChar;

/**
 * created by ： bifan-wei
 */

public interface ISliderListener {
    void onShowSlider(TxtChar txtChar);
    void onShowSlider(String CurrentSelectedText);
    void onReleaseSlider();
}
