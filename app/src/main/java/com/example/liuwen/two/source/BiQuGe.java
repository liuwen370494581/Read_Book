package com.example.liuwen.two.source;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.engine.ChapterSite;
import com.example.liuwen.two.utils.BookGriper;
import com.example.liuwen.two.utils.NetUtil;
import com.example.liuwen.two.utils.RegexUtil;
import com.example.liuwen.two.utils.TextUtil;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 13:48
 * desc   :笔趣阁数据源
 */
public class BiQuGe extends ChapterSite {
    private final static String root = "http://www.biquge.com.tw";

    @Override
    public String getSiteName() {
        return "笔趣阁";
    }

    @Override
    public List<Book> search(String bookName) throws Exception {
        String url = "http://www.biquge.com.tw/modules/article/soshu.php?searchkey="
                + URLEncoder.encode(bookName, "gbk");
        String html = NetUtil.getHtml(url, getEncodeType());
        String title = RegexUtil.regexExcept("<title>", "</title>", html).get(0);
        if (!title.equals("笔趣阁_书友最值得收藏的网络小说阅读网")) {
            String info = RegexUtil.regexExcept("<div id=\"info\">", "</div>", html).get(0);
            info = TextUtil.cleanContent(info);
            List<String> ps = RegexUtil.regexInclude("<p>", "</p>", info);
            String author = new RegexUtil.Tag(ps.get(0)).getText().replaceAll("作者|：", "");
            String lastChapterName = RegexUtil.regexExcept("\">", "</a>", ps.get(3)).get(0);
            String lastUpdateTime = new RegexUtil.Tag(ps.get(2)).getText();
            Book book = new Book(bookName, author, url, "未知", lastUpdateTime, lastChapterName, this);
            return Collections.singletonList(book);
        } else {
            List<String> trs = RegexUtil.regexExcept("<tr", "</tr>", html);
            if (trs.size() == 0) {
                throw new IOException();
            }
            trs.remove(0);
            List<Book> bookList = new ArrayList<>(trs.size());
            for (String tr : trs) {
                List<String> tds = RegexUtil.regexInclude("<td", "</td>", tr);
                List<String> as = RegexUtil.regexInclude("<a", "</a>", tr);
                if (tds.size() == 0 || as.size() == 0) throw new IOException();
                String bkName = new RegexUtil.Tag(as.get(0)).getText();
                String bkUrl = new RegexUtil.Tag(as.get(0)).getValue("href");
                String lastChapterName = new RegexUtil.Tag(as.get(1)).getText();
                String author = new RegexUtil.Tag(tds.get(2)).getText();
                String size = new RegexUtil.Tag(tds.get(3)).getText();
                String lastUpdateTime = new RegexUtil.Tag(tds.get(4)).getText();
                Book book = new Book(bkName, author, bkUrl, size, lastUpdateTime, lastChapterName, this);
                bookList.add(book);
            }
            return bookList;
        }
    }

    @Override
    public List<Catalog> parseCatalog(String catalogHtml, String url) {
        List<String> as = RegexUtil.regexExcept("<dd>", "</dd>", catalogHtml);
        List<Catalog> catalogs = new ArrayList<>();
        for (String a : as) {
            String name = RegexUtil.regexExcept("\">", "</a>", a).get(0);
            String href = root + RegexUtil.regexExcept("<a href=\"", "\">", a).get(0);
            catalogs.add(new Catalog(name, href));
        }
        return catalogs;
    }

    @Override
    public List<String> parseContent(String chapterHtml) {
        String content = RegexUtil.regexExcept("<div id=\"content\">", "</div>", chapterHtml).get(0);
        return BookGriper.getContentsByBR(content);
    }
}
