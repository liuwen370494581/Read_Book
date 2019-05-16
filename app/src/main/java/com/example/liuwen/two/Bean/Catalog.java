package com.example.liuwen.two.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 11:36
 * desc   : 章节目录
 */
@Entity
public class Catalog implements Serializable {
    @Id(autoincrement = true)
    private Long id;
    private String chapterName;
    private String url;
    private int index;

    public Catalog(String chapterName, String url) {
        this.chapterName = chapterName;
        this.url = url;
    }

    public Catalog() {
    }

    @Generated(hash = 2110256265)
    public Catalog(Long id, String chapterName, String url, int index) {
        this.id = id;
        this.chapterName = chapterName;
        this.url = url;
        this.index = index;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "chapterName='" + chapterName + '\'' +
                ", url='" + url + '\'' +
                ", index=" + index +
                '}';
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
