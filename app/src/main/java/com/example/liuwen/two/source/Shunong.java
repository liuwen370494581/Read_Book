package com.example.liuwen.two.source;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.engine.Site;
import com.example.liuwen.two.utils.NetUtil;
import com.example.liuwen.two.utils.TextUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2019/04/16 17:02
 * desc   :
 */
public class Shunong extends Site {

    private static final String root = "http://www.qxswk.com/";

    @Override
    public List<Catalog> parseCatalog(String catalogHtml, String rootUrl) {
        Elements lis = Jsoup.parse(catalogHtml).getElementsByClass("book_list").first().getElementsByTag("li");

        List<Catalog> catalogs = new ArrayList<>();
        for (Element li : lis) {
            String title = li.getElementsByTag("a").first().text();
            String href = root + li.getElementsByTag("a").first().attr("href");
            catalogs.add(new Catalog(title, href));
        }
        return catalogs;
    }

    @Override
    public List<String> parseContent(String chapterHtml) {
        Elements ps = Jsoup.parse(chapterHtml).getElementById("htmlContent").getElementsByTag("p");
        if (ps.first().text().contains("shunong.com")) {
            ps.remove(0);
        }
        List<String> contents = new ArrayList<>();
        for (Element p : ps) {
            String s = p.text().trim();
            if (!s.isEmpty()) {
                contents.add(TextUtil.cleanContent(s));
            }
        }
        return contents;
    }

    @Override
    public List<Book> search(String bookName) throws Exception {
        RequestBody requestBody = new FormBody.Builder()
                .add("keyboard", bookName)
                .add("show", "title")
                .build();
        String html = NetUtil.getHtml("http://www.qxswk.com/e/search/index.php", requestBody, getEncodeType());

        List<Book> books = new ArrayList<>();

        Elements lis = Jsoup.parse(html).getElementsByClass("listbox").first().getElementsByTag("li");

        for (Element li : lis) {
            String bkName = li.getElementsByTag("font").first().text().replace("在线阅读", "");
            String href = root + li.getElementsByTag("a").first().attr("href");
            String author = li.getElementsByTag("div").first().getElementsByTag("a").first().text();
            String imageUrl = root + li.getElementsByTag("img").first().attr("src");
            books.add(new Book(bkName, author, href, imageUrl, "未知", "未知", "未知", getSiteName()));
        }
        return books;
    }

    @Override
    public String getEncodeType() {
        return "utf-8";
    }

    @Override
    public String getSiteName() {
        return "书农小说";
    }
}
