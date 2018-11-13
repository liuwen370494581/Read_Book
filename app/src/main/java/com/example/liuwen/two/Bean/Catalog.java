package com.example.liuwen.two.Bean;

import java.io.Serializable;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 11:36
 * desc   :
 */
public class Catalog implements Serializable {
    private String chapterName;
    private String url;
    private int index;

    public Catalog(String chapterName, String url) {
        this.chapterName = chapterName;
        this.url = url;
    }

    public Catalog(){ }

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
}
