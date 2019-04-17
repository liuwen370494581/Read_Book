package com.example.liuwen.two.source;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.engine.Site;
import com.example.liuwen.two.utils.BookGriper;
import com.example.liuwen.two.utils.NetUtil;

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
 * time   : 2019/04/16 17:06
 * desc   :
 */
public class Zhuaji extends Site {

    private static final String root = "https://www.zhuaji.org/";

    @Override
    public List<Catalog> parseCatalog(String catalogHtml, String rootUrl) {
        List<Catalog> catalogs = new ArrayList<>();

        Elements dds = Jsoup.parse(catalogHtml).getElementsByTag("dd");
        for (Element dd : dds) {
            String title = dd.getElementsByTag("a").first().text();
            String href = root + dd.getElementsByTag("a").first().attr("href");
            catalogs.add(new Catalog(title, href));
        }
        return catalogs;
    }

    @Override
    public List<String> parseContent(String chapterHtml) throws Exception{
        return BookGriper.getContentsByTextNodes(Jsoup.parse(chapterHtml).getElementById("content").textNodes());
    }

    @Override
    public List<Book> search(String bookName) throws Exception {
        RequestBody requestBody = new FormBody.Builder()
                .add("searchkey", bookName)
                .build();
        String html = NetUtil.getHtml("https://www.zhuaji.org/search.html", requestBody, getEncodeType());
        Elements lis = Jsoup.parse(html).getElementById("sitebox").getElementsByTag("li");

        List<Book> bookList = new ArrayList<>();
        for (Element li : lis) {
            String lastUpdateTime = li.getElementsByTag("h3").first().getElementsByTag("span").first().text();
            String bkName = li.getElementsByTag("h3").first().getElementsByTag("a").first().text();
            String bkUrl = li.getElementsByTag("h3").first().getElementsByTag("a").first().attr("href");
            String author = li.getElementsByTag("p").first().getElementsByTag("span").first().text();
            String lastChapterName = li.getElementsByTag("p").get(1).getElementsByTag("a").first().text();
            String imageUrl = li.getElementsByTag("img").first().attr("src");
            bookList.add(new Book(bkName, author, bkUrl, imageUrl, "未知", lastUpdateTime, lastChapterName, getSiteName()));
        }
        return bookList;
    }

    @Override
    public String getEncodeType() {
        return "utf-8";
    }

    @Override
    public String getSiteName() {
        return "爪机书屋";
    }
}
