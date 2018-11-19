package com.example.liuwen.two.Bean;

import com.example.liuwen.two.engine.ISite;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 11:33
 * desc   :
 */
@Entity
public class Book implements Serializable {
    @Id(autoincrement = true)
    private Long id;
    private String bookName;
    private String author = "未知";
    private String url;
    private String chapterSize = "未知";
    private String lastUpdateTime = "未知";
    private String lastChapterName = "未知";
    @Transient
    private ISite site;
    private String source;


    public Book(String bookName, String author, String url, String chapterSize, String lastUpdateTime, String lastChapterName, ISite site, String source) {
        this.bookName = bookName;
        this.author = author;
        this.url = url;
        this.chapterSize = chapterSize;
        this.lastUpdateTime = lastUpdateTime;
        this.lastChapterName = lastChapterName;
        this.site = site;
        this.source = source;
    }

    @Generated(hash = 1493694612)
    public Book(Long id, String bookName, String author, String url, String chapterSize, String lastUpdateTime, String lastChapterName, String source) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.url = url;
        this.chapterSize = chapterSize;
        this.lastUpdateTime = lastUpdateTime;
        this.lastChapterName = lastChapterName;
        this.source = source;
    }

    @Generated(hash = 1839243756)
    public Book() {
    }

    public ISite getSite() {
        return site;
    }

    public void setSite(ISite site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "Book{" +
                " bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", chapterSize='" + chapterSize + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", lastChapterName='" + lastChapterName + '\'' +
                ", site=" + site +
                '}';
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
}
