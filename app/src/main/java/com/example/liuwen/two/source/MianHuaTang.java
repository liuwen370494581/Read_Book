package com.example.liuwen.two.source;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.engine.ChapterSite;
import com.example.liuwen.two.utils.BookGriper;
import com.example.liuwen.two.utils.NetUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 13:56
 * desc   : 棉花糖
 */
public class MianHuaTang extends ChapterSite {
    @Override
    public String getSiteName() {
        return "棉花糖小说";
    }

    @Override
    public List<Book> search(String bookName) throws Exception {
        String url = "http://www.mianhuatang520.com/search.aspx?bookname=" + URLEncoder.encode(bookName, getEncodeType());
        String html = NetUtil.getHtml(url, getEncodeType());
        Elements liElements = Jsoup.parse(html).getElementById("newscontent")
                .getElementsByClass("l").first().getElementsByTag("li");
        List<Book> bookList = new ArrayList<>();
        for (Element liElement : liElements) {
            Elements spans = liElement.getElementsByTag("span");
            String bkName = spans.get(1).getElementsByTag("a").first().text();
            String bkUrl = spans.get(1).getElementsByTag("a").first().attr("href");
            String lastChapterName = spans.get(2).getElementsByTag("a").first().text();
            String author = spans.get(3).text();
            String lastUpdateTime = spans.get(4).text();
            bookList.add(new Book(bkName, author, bkUrl, "未知", lastUpdateTime, lastChapterName, this));
        }
        return bookList;
    }


    @Override
    public List<Catalog> parseCatalog(String catalogHtml, String url) {
        Elements dds = Jsoup.parse(catalogHtml).getElementsByTag("dd");
        List<Catalog> catalogs = new ArrayList<>();
        for (Element dd : dds) {
            Element a = dd.getElementsByTag("a").first();
            String href = a.attr("href");
            String name = a.text();
            catalogs.add(new Catalog(name, href));
        }
        return catalogs;
    }

    @Override
    public List<String> parseContent(String chapterHtml) {
        List<TextNode> textNodes = Jsoup.parse(chapterHtml, "", Parser.xmlParser()).getElementById("zjneirong").textNodes();
        return BookGriper.getContentsByTextNodes(textNodes);
    }
}
