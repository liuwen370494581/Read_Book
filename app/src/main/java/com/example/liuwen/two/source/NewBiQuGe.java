package com.example.liuwen.two.source;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.engine.ChapterSite;
import com.example.liuwen.two.utils.BookGriper;
import com.example.liuwen.two.utils.NetUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 13:51
 * desc   : 新笔趣阁
 */
public class NewBiQuGe extends ChapterSite {

    private static final String root = "https://www.xbiquge6.com";

    @Override
    public String getSiteName() {
        return "新笔趣阁";
    }

    @Override
    public String getEncodeType() {
        return "utf-8";
    }

    @Override
    public List<Book> search(String bookName) throws Exception {
        String html = NetUtil.getHtml("https://www.xbiquge6.com/search.php?keyword="
                + URLEncoder.encode(bookName, getEncodeType()), getEncodeType());

        Elements items = Jsoup.parse(html).getElementsByClass("result-game-item-detail");
        if (items == null || items.size() <= 0) {
            throw new IOException();
        }

        List<Book> bookList = new ArrayList<>();
        for (Element item : items) {
            String bkName = item.getElementsByTag("a").first().attr("title");
            String url = item.getElementsByTag("a").first().attr("href");
            Elements tags = item.getElementsByClass("result-game-item-info-tag");
            String author = tags.get(0).getElementsByTag("span").get(1).text();
            String lastUpdateTime = tags.get(2).getElementsByTag("span").get(1).text();
            String lastChapterName = tags.get(3).getElementsByTag("a").text();
            bookList.add(new Book(bkName, author, url, "未知", lastUpdateTime, lastChapterName, this,getSiteName()));
        }
        return bookList;
    }

    @Override
    public List<Catalog> parseCatalog(String catalogHtml, String url) {
        Element listElement = Jsoup.parse(catalogHtml).getElementById("list");
        List<Catalog> catalogs = new ArrayList<>();
        for (Element a : listElement.getElementsByTag("a")) {
            String bkUrl = root + a.attr("href");
            String name = a.text();
            catalogs.add(new Catalog(name, bkUrl));
        }
        return catalogs;
    }

    @Override
    public List<String> parseContent(String chapterHtml) {
        List<TextNode> textNodes = Jsoup.parse(chapterHtml).getElementById("content").textNodes();
        return BookGriper.getContentsByTextNodes(textNodes);
    }
}
