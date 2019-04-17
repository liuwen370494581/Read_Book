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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 13:55
 * desc   : 笔神阁
 */
public class BiShenGe extends Site {
    @Override
    public String getSiteName() {
        return "笔神阁";
    }

    @Override
    public List<Book> search(String bookName) throws Exception {
        String url = "http://www.bishenge.com/pc/so.php";
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("searchkey", URLEncoder.encode(bookName, "gbk"))
                .add("searchtype", "articlename")
                .build();
        String html = NetUtil.getHtml(url, requestBody, getEncodeType());
        Elements elements = Jsoup.parse(html).getElementById("main").getElementsByTag("tr");
        //移除表格标题
        elements.remove(0);
        List<Book> books = new ArrayList<>();
        for (Element element : elements) {
            Elements tds = element.getElementsByTag("td");
            Element a = tds.first().getElementsByTag("a").first();
            String bkName = a.text();
            String bkUrl = "http://www.bishenge.com" + a.attr("href");
            String author = tds.get(1).text();
            String updateTime = tds.get(2).text().split(" ")[0];
            books.add(new Book(bkName, author, bkUrl, "", "", updateTime, "", getSiteName()));
        }
        return books;
    }

    @Override
    public List<Catalog> parseCatalog(String catalogHtml, String rootUrl) {
        return BookGriper.parseBqgCatalogs(catalogHtml, rootUrl);
    }

    @Override
    public List<String> parseContent(String chapterHtml) {
        String content = RegexUtil.regexExcept("<div id=\"content\">", "</div>", chapterHtml).get(0);
        return BookGriper.getContentsByBR(content);
    }
}
