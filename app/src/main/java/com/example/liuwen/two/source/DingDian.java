package com.example.liuwen.two.source;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.engine.Site;
import com.example.liuwen.two.utils.BookGriper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.TextNode;

import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 13:50
 * desc   : 顶点小说 https://www.booktxt.net/
 */
public class DingDian extends Site {
    @Override
    public String getSiteName() {
        return "顶点小说";
    }

    @Override
    public List<Book> search(String bookName) throws Exception {
        return BookGriper.baidu(bookName, getSiteName(), "5334330359795686106");
    }

    @Override
    public List<Catalog> parseCatalog(String catalogHtml, String rootUrl) {
        return BookGriper.parseBqgCatalogs(catalogHtml, rootUrl);
    }

    @Override
    public List<String> parseContent(String chapterHtml) {
        List<TextNode> textNodes = Jsoup.parse(chapterHtml).getElementById("content").textNodes();
        return BookGriper.getContentsByTextNodes(textNodes);
    }
}
