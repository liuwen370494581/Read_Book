package com.example.liuwen.two.engine.parser;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.Rx.Disposable;
import com.example.liuwen.two.Rx.Observer;
import com.example.liuwen.two.Rx.Subscriber;
import com.example.liuwen.two.engine.Platform;
import com.example.liuwen.two.engine.Site;
import com.example.liuwen.two.utils.NetUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2019/04/16 17:09
 * desc   :
 */
public class ContentObserver implements Observer<List<String>>, Disposable {

    private Book book;
    private Catalog catalog;

    private Platform platform = Platform.get();
    volatile private boolean attachView = true;
    private ExecutorService service = Executors.newCachedThreadPool();

    public ContentObserver(Book book, Catalog catalog) {
        this.book = book;
        this.catalog = catalog;
    }

    @Override
    public void dispose() {
        attachView = false;
    }

    @Override
    public Disposable subscribe(final Subscriber<List<String>> subscriber) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Site site = book.getSite();
                    String html = NetUtil.getHtml(catalog.getUrl(), site.getEncodeType());
                    final List<String> list = site.parseContent(html);
                    post(new Runnable() {
                        @Override
                        public void run() {
                            subscriber.onFinish(list);
                        }
                    });
                } catch (final Exception e) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            subscriber.onError(e);
                        }
                    });
                }
            }
        });
        return this;
    }

    private void post(Runnable runnable) {
        service.shutdownNow();
        if (attachView) {
            platform.defaultCallbackExecutor().execute(runnable);
        }
    }
}
