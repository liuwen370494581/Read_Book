package com.example.liuwen.two.Action;

import android.support.annotation.Nullable;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;

import java.util.ArrayList;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/13 15:19
 * desc   :
 */
public class CatalogsHolder {

    private ArrayList<Catalog> catalogs = null;
    private Book book = null;

    public void setCatalogs(ArrayList<Catalog> catalogs, Book book) {
        this.catalogs = catalogs;
        this.book = book;
    }

    public @Nullable
    ArrayList<Catalog> getCatalogs() {
        return catalogs;
    }

    public @Nullable
    Book getNetBook() {
        return book;
    }

    public void clean() {
        catalogs = null;
        book = null;
    }

    private CatalogsHolder() {
    }

    private static class SingletonHolder {
        private final static CatalogsHolder instance = new CatalogsHolder();
    }

    public static CatalogsHolder getInstance() {
        return SingletonHolder.instance;
    }
}
