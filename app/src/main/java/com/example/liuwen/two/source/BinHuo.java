package com.example.liuwen.two.source;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.engine.Site;
import com.example.liuwen.two.utils.BookGriper;
import com.example.liuwen.two.utils.NetUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 13:57
 * desc   : 冰火中文网数据源
 */
public class BinHuo  extends Site {

    private static final String root = "https://www.bhzw.cc/";

    @Override
    public List<Catalog> parseCatalog(String catalogHtml, String rootUrl) {
        Elements as = Jsoup.parse(catalogHtml).getElementsByClass("float-list fill-block").first().getElementsByTag("a");
        List<Catalog> catalogs = new ArrayList<>();
        for (Element a : as) {
            String href = rootUrl + a.attr("href");
            String name = a.text();
            catalogs.add(new Catalog(name, href));
        }
        return catalogs;
    }

    @Override
    public List<String> parseContent(String chapterHtml) {
        List<TextNode> textNodes = Jsoup.parse(chapterHtml).getElementById("ChapterContents").textNodes();
        return BookGriper.getContentsByTextNodes(textNodes);
    }

    @Override
    public List<Book> search(String bookName) throws Exception {
        String url = "https://www.bhzw.cc/modules/article/search.php";
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("searchkey", URLEncoder.encode(bookName, getEncodeType()))
                .build();
        String html = NetUtil.getHtml(url, requestBody, getEncodeType());
        Elements trs = Jsoup.parse(html).getElementsByClass("bd").first()
                .getElementsByTag("tbody").first().getElementsByTag("tr");

        List<Book> bookList = new ArrayList<>();
        for (Element tr : trs) {
            Elements as = tr.getElementsByTag("td").get(2).getElementsByTag("a");
            String bkName = as.get(0).text();
            String href = root + as.get(0).attr("href");
            String lastChapterName = as.get(1).text();
            String author = tr.getElementsByTag("td").get(3).text();
            String size = tr.getElementsByTag("td").get(4).text();
            String lastUpdateTime = tr.getElementsByTag("td").get(5).text();
            bookList.add(new Book(bkName, author, href, size, lastUpdateTime, lastChapterName, getSiteName()));
        }
        return bookList;
    }

    @Override
    public String getSiteName() {
        return "冰火中文";
    }

}
