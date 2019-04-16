package com.example.liuwen.two.Action;

import com.example.liuwen.two.Bean.Book;
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
     * 判断数据库中是否是同一本书才能够Catalog中获取
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


    /**
     * 返回一个历史书籍的对象
     *
     * @param bookName
     * @return
     */
    public static Catalog backCatalogHistory(String bookName) {
        Catalog catalog = new Catalog();
        for (Catalog ca : CatalogDaoHolder.query()) {
            if (ca.getChapterName().equals(bookName)) {
                catalog = ca;
                break;
            }
        }
        return catalog;
    }

    /**
     * 从book中获取是否是同一本书
     *
     * @param bookName
     * @return
     */
    public static boolean isSameBookFormBookBean(String bookName) {
        boolean isAlikeBook = false;
        for (Book book : BookDaoHolder.query()) {
            if (book.getBookName().equals(bookName)) {
                isAlikeBook = true;
                break;
            }
        }
        return isAlikeBook;
    }

    public static Book backBookHistory(String bookName) {
        Book book = new Book();
        for (Book bk : BookDaoHolder.query()) {
            if (bk.getBookName().equals(bookName)) {
                book = bk;
                break;
            }
        }
        return book;
    }


}
