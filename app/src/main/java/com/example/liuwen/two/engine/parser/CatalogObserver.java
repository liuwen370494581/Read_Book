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
 * time   : 2019/04/16 16:29
 * desc   :
 */
public class CatalogObserver implements Observer<List<Catalog>>, Disposable {

    private Book book;

    private Platform platform;
    volatile private boolean attachView = true;
    private ExecutorService service = Executors.newCachedThreadPool();

    public CatalogObserver(Book book) {
        this.book = book;
    }

    @Override
    public void dispose() {
        attachView = false;
        service.shutdownNow();
    }

    @Override
    public Disposable subscribe(final Subscriber<List<Catalog>> subscriber) {
        platform = Platform.get();
        service.execute(new Runnable() {
            @Override
            public void run() {
                String html = "";
                Site site = book.getSite();
                try {
                    html = NetUtil.getHtml(book.getUrl(), site.getEncodeType());
                    final List<Catalog> list = site.parseCatalog(html, book.getUrl());
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
        if (attachView) {
            platform.defaultCallbackExecutor().execute(runnable);
        }
    }
}
