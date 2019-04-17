package com.example.liuwen.two.engine;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Chapter;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2019/04/16 17:13
 * desc   :
 */
public interface ParseStrategy {

    //生成文本
    String parseContent(Chapter chapter);

    //保存到指定目录
    File save(List<Chapter> chapters, Book book, String savePath) throws IOException;
}
