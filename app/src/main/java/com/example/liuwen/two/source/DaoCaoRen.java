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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 13:59
 * desc   : 稻草人
 */
public class DaoCaoRen extends ChapterSite {

    private static final String root = "http://www.daocaorenshuwu.com";

    @Override
    public String getSiteName() {
        return "稻草人书屋";
    }

    @Override
    public String getEncodeType() {
        return "utf-8";
    }


    @Override
    public List<Catalog> parseCatalog(String catalogHtml, String url) {
        Elements as = Jsoup.parse(catalogHtml).getElementById("all-chapter").getElementsByTag("a");
        List<Catalog> catalogs = new ArrayList<>();
        for (Element a : as) {
            String name = a.text();
            String href = a.attr("href");
            catalogs.add(new Catalog(name, href));
        }
        return catalogs;
    }

    @Override
    public List<String> parseContent(String chapterHtml) {
        List<TextNode> textNodes = Jsoup.parse(chapterHtml).getElementById("cont-text").textNodes();
        System.out.println(textNodes);
        return BookGriper.getContentsByTextNodes(textNodes);
    }

    @Override
    public List<Book> search(String bookName) throws Exception {
        String url = "http://www.daocaorenshuwu.com/plus/search.php?q=" + URLEncoder.encode(bookName, getEncodeType());
        String html = NetUtil.getHtml(url, getEncodeType());
        Elements trs = Jsoup.parse(html).getElementsByClass("table table-condensed").first()
                .getElementsByTag("tbody").first().getElementsByTag("tr");

        List<Book> bookList = new ArrayList<>();
        for (Element tr : trs) {
            String bkUrl = root + tr.getElementsByTag("a").first().attr("href");
            String bkName = tr.getElementsByTag("a").first().text();
            String author = tr.getElementsByTag("td").get(1).text();
            bookList.add(new Book(bkName, author, bkUrl, "未知", "未知", "未知", this));
        }
        return bookList;
    }


}
