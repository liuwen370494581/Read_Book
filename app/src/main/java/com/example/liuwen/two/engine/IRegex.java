package com.example.liuwen.two.engine;

import com.example.liuwen.two.Bean.Catalog;

import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 11:40
 * desc   :
 */
public interface IRegex {
    public List<Catalog> parseCatalog(String catalogHtml, String url);

    public List<String> parseContent(String chapterHtml);
}
