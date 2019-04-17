package com.example.liuwen.two.engine;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.BuildConfig;
import com.example.liuwen.two.Rx.Observer;
import com.example.liuwen.two.engine.parser.CatalogObserver;
import com.example.liuwen.two.engine.parser.ContentObserver;
import com.example.liuwen.two.engine.parser.DownloadObserver;
import com.example.liuwen.two.engine.parser.SearchObserver;

import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2019/04/16 17:24
 * desc   :
 *  * Site包装类
 *  * search: 并发搜索 {@link SearchObserver}
 *  * getCatalog: 通过book获取目录 {@link CatalogObserver}
 *  * getContents: 通过book，catalog获取内容 {@link ContentObserver}
 *  * download: 下载书籍 {@link DownloadObserver}
 *  * <p>
 *  * 使用SiteCollection添加自定义解析站点: {@link SiteCollection#addSite(Site)}
 */
public class EasyBook {

    private EasyBook() {
    }

    public static Observer<List<Book>> search(String bookName) {
        return search(bookName, SiteCollection.getInstance().getAllSites());
    }

    public static Observer<List<Book>> search(final String bookName, final List<Site> sites) {
        return new SearchObserver(bookName, sites);
    }

    public static DownloadObserver download(Book book) {
        return new DownloadObserver(book);
    }

    public static Observer<List<Catalog>> getCatalog(Book book) {
        return new CatalogObserver(book);
    }

    public static Observer<List<String>> getContent(Book book, Catalog catalog) {
        return new ContentObserver(book, catalog);
    }

    public static int getVersion(){
        return BuildConfig.VERSION_CODE;
    }

    public static String getVersionName(){
        return BuildConfig.VERSION_NAME;
    }
}
