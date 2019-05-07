package com.example.liuwen.two.View.BookView.interfaces;


import com.example.liuwen.two.View.BookView.main.TxtReaderContext;

/*
* create by bifan-wei
* 2017-11-13
*/
public interface ITxtTask {
    void Run(ILoadListener callBack, TxtReaderContext readerContext);
}
