package com.example.liuwen.two.engine;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;

import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 11:34
 * desc   :
 */
public  abstract class Site {

    public abstract List<Book> search(String bookName) throws Exception;

    public abstract List<Catalog> parseCatalog(String catalogHtml, String rootUrl) throws Exception;

    public abstract List<String> parseContent(String chapterHtml) throws Exception;

    public abstract String getSiteName();

    public String getEncodeType() {
        return "gbk";
    }


}
