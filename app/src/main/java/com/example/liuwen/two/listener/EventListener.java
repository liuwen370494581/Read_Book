package com.example.liuwen.two.listener;

import com.example.liuwen.two.Bean.Book;

import java.io.File;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 11:35
 * desc   :
 */
public interface EventListener {

    void onChooseBook(List<Book> books);

    void pushMessage(String msg);

    void onDownload(int progress, String msg);

    void onEnd(String msg, File file);

    void onError(String msg, Exception e);
}
