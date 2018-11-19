package com.example.liuwen.two.engine;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.listener.EventListener;

import org.greenrobot.greendao.annotation.Entity;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 11:34
 * desc   :
 */
public interface ISite extends Serializable {

    public List<Book> search(String bookName) throws Exception;

    public File download(Book book, Type type, String savePath, EventListener eventListener);

    public String getSiteName();

    public String getEncodeType();

    public int getThreadCount();


}
