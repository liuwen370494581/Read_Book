package com.example.liuwen.two.Action;

import com.example.liuwen.two.Bean.Catalog;

import java.util.ArrayList;
import java.util.List;

import GreenDao3.BookDaoHolder;
import GreenDao3.CatalogDaoHolder;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/15 14:07
 * desc   :
 */
public class BookAction {


    /**
     * 判断数据库中是否是同一本书
     *
     * @param bookName
     * @return
     */
    public static boolean isSameBooK(String bookName) {
        boolean isAlikeBook = false;
        for (Catalog catalog : CatalogDaoHolder.query()) {
            if (catalog.getChapterName().equals(bookName)) {
                isAlikeBook = true;
                break;
            }
        }
        return isAlikeBook;
    }


    public static Catalog backBookHistory(String bookName) {
        Catalog catalog = new Catalog();
        for (Catalog ca : CatalogDaoHolder.query()) {
            if (ca.getChapterName().equals(bookName)) {
                catalog = ca;
                break;
            }
        }
        return catalog;
    }
}
