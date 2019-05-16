package com.example.liuwen.two.Bean;

import com.example.liuwen.two.engine.Site;
import com.example.liuwen.two.engine.SiteCollection;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 11:33
 * desc   : 当前书籍
 */
@Entity
public class Book implements Serializable {
    @Id(autoincrement = true)
    private Long id;
    private String bookName;
    private String author = "未知";
    private String url;
    private String imageUrl;
    private String chapterSize = "未知";
    private String lastUpdateTime = "未知";
    private String lastChapterName = "未知";
    private String siteName;
    private String addBookTime;//添加到书架的时间 用来排序


    public Site getSite() {
        for (Site site : SiteCollection.getInstance().getAllSites()) {
            if (site.getSiteName().equals(siteName)) {
                return site;
            }
        }
        return null;
    }


    public Book(String bookName, String author, String url, String chapterSize, String lastUpdateTime, String lastChapterName, String siteName) {
        this(bookName, author, url, "", chapterSize, lastUpdateTime, lastChapterName, siteName);
    }

    public Book(String bookName, String author, String url, String imageUrl, String chapterSize, String lastUpdateTime, String lastChapterName, String siteName) {
        this.bookName = bookName;
        this.author = author;
        this.url = url;
        this.imageUrl = imageUrl;
        this.chapterSize = chapterSize;
        this.lastUpdateTime = lastUpdateTime;
        this.lastChapterName = lastChapterName;
        this.siteName = siteName;
    }


    @Generated(hash = 1839243756)
    public Book() {
    }


    @Generated(hash = 1368846132)
    public Book(Long id, String bookName, String author, String url, String imageUrl, String chapterSize, String lastUpdateTime, String lastChapterName,
            String siteName, String addBookTime) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.url = url;
        this.imageUrl = imageUrl;
        this.chapterSize = chapterSize;
        this.lastUpdateTime = lastUpdateTime;
        this.lastChapterName = lastChapterName;
        this.siteName = siteName;
        this.addBookTime = addBookTime;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getChapterSize() {
        return chapterSize;
    }

    public void setChapterSize(String chapterSize) {
        this.chapterSize = chapterSize;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastChapterName() {
        return lastChapterName;
    }

    public void setLastChapterName(String lastChapterName) {
        this.lastChapterName = lastChapterName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddBookTime() {
        return addBookTime;
    }

    public void setAddBookTime(String addBookTime) {
        this.addBookTime = addBookTime;
    }

}
