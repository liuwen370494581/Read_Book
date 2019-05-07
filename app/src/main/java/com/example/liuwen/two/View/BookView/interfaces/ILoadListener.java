package com.example.liuwen.two.View.BookView.interfaces;


import com.example.liuwen.two.View.BookView.bean.TxtMsg;

/*
* create by bifan-wei
* 2017-11-13
*/
public interface ILoadListener {
    void onSuccess();
    void onFail(TxtMsg txtMsg);
    void onMessage(String message);
}
