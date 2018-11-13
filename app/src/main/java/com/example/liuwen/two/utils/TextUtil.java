package com.example.liuwen.two.utils;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 11:48
 * desc   :
 */
public class TextUtil {
    public static String cleanContent(String content) {
        return content.replaceAll("\n|\t|\r|&nbsp;|<br>|<br/>|<br />|p&gt;|&gt;", "").trim();
    }

}
