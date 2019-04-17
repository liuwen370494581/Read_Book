package com.example.liuwen.two.source;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.engine.Site;
import com.example.liuwen.two.utils.BookGriper;
import com.example.liuwen.two.utils.NetUtil;
import com.example.liuwen.two.utils.RegexUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 13:53
 * desc   :看神作 http://www.kanshenzuo.com 书挺全的，更新也比较及时 测试约500k/s
 */
public class KanShenZuo  extends Site {

    private static final String root = "http://www.kanshenzuo.com";

    @Override
    public String getSiteName() {
        return "看神作";
    }

    @Override
    public List<Book> search(String bookName) throws Exception {
        String url = "http://www.kanshenzuo.com/modules/article/search.php";
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("searchkey", URLEncoder.encode(bookName, "gbk"))
                .add("searchtype", "articlename")
                .build();
        String html = NetUtil.getHtml(url, requestBody, getEncodeType());
        Element content = Jsoup.parse(html).getElementById("content");
        Elements trs = content.getElementsByTag("tr");
        if (trs.size() <= 1) {
            throw new IOException();
        }
        trs.remove(0);
        List<Book> bookList = new ArrayList<>(trs.size());
        for (Element tr : trs) {
            Elements tds = tr.getElementsByTag("td");
            if (tds == null) {
                throw new IOException();
            }
            String bkName = tds.get(0).getElementsByTag("a").first().text();
            String bkUrl = tds.get(0).getElementsByTag("a").first().attr("href");
            String lastChapterName = tds.get(1).getElementsByTag("a").first().text();
            String author = tds.get(2).text();
            String size = tds.get(3).text();
            String lastUpdateTime = tds.get(4).text();
            Book book = new Book(bkName, author, bkUrl, size, lastUpdateTime, lastChapterName, getSiteName());
            bookList.add(book);
        }
        return bookList;
    }

    @Override
    public List<Catalog> parseCatalog(String catalogHtml, String rootUrl) {
        String sub = RegexUtil.regexExcept("<div id=\"list\">", "</div>", catalogHtml).get(0);
        String ssub = sub.split("正文</dt>")[1];
        List<String> as = RegexUtil.regexInclude("<a", "</a>", ssub);
        List<Catalog> list = new ArrayList<>();
        for (String s : as) {
            RegexUtil.Tag tag = new RegexUtil.Tag(s);
            String name = tag.getText();
            String href = root + tag.getValue("href");
            list.add(new Catalog(name, href));
        }
        return list;
    }

    @Override
    public List<String> parseContent(String chapterHtml) {
        String content = RegexUtil.regexExcept("<div id=\"content\">", "</div>", chapterHtml).get(0);
        return BookGriper.getContentsByBR(content);
    }
}
