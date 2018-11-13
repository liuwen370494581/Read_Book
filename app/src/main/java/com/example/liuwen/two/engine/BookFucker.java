package com.example.liuwen.two.engine;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.Bean.Chapter;
import com.example.liuwen.two.listener.EventListener;
import com.example.liuwen.two.utils.NetUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 11:41
 * desc   :
 */
public class BookFucker {

    private ArrayList<Chapter> chapters;
    private LinkedList<Catalog> catalogQueue;
    private final Object bufferLock = new Object();
    private final Object queueLock = new Object();

    volatile private boolean needFreshProcess = true;
    volatile private int tempProgress = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    ArrayList<Chapter> download(String url, String encodeType, EventListener eventListener, int threadCount, IRegex regex) {
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
        eventListener.onDownload(0, "正在解析目录...");

        //从目录页获取有序章节
        String catalogHtml = null;
        try {
            catalogHtml = NetUtil.getHtml(url, encodeType);
        } catch (IOException e) {
            eventListener.onError("获取目录页面失败", e);
            return new ArrayList<>();
        }
        List<Catalog> catalogs;
        try {
            catalogs = regex.parseCatalog(catalogHtml, url);
            //添加序号
            for (int i = 0; i < catalogs.size(); i++) {
                catalogs.get(i).setIndex(i + 1);
            }
        } catch (Exception e) {
            eventListener.onError("解析目录失败，请联系作者修复", e);
            return new ArrayList<>();
        }

        if (catalogs.size() == 0) {
            System.err.println(catalogHtml);
            eventListener.onError("没有目录...", new IOException());
            return new ArrayList<>();
        }

        chapters = new ArrayList<>(catalogs.size() + 1);
        catalogQueue = new LinkedList<>(catalogs);

        eventListener.onDownload(0, "一共" + catalogs.size() + "张，开始下载...");

        final int catalogSize = catalogs.size();
        CountDownLatch leftBook = new CountDownLatch(catalogSize);
        CountDownLatch errorBook = new CountDownLatch(catalogSize);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                needFreshProcess = true;
            }
        }, 0, 1000);

        synchronized (queueLock) {
            //自动探测是否全部下载
            while (getBufferSize() < catalogs.size()) {
                Catalog catalog = catalogQueue.poll();
                if (catalog == null) {//如果队列为空，释放锁，等待唤醒或者超时后继续探测
                    try {
                        queueLock.wait(1000);
                    } catch (InterruptedException e) {
                        eventListener.onError("下载时发生并发错误", e);
                    }
                } else {//队列有章节，下载所有
                    while (catalog != null) {
                        Catalog finalCatalog = catalog;
                        threadPool.execute(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });

                        threadPool.execute(() -> {
                            try {
                                String chapterHtml = NetUtil.getHtml(finalCatalog.getUrl(), encodeType);
                                List<String> contents = regex.parseContent(chapterHtml);
                                if (needFreshProcess) {
                                    tempProgress = (int) (((errorBook.getCount() - leftBook.getCount()) / (float) (2 * catalogSize - errorBook.getCount())) * 100);
                                    needFreshProcess = false;
                                }
                                eventListener.onDownload(tempProgress, finalCatalog.getChapterName());
                                Chapter chapter = new Chapter(finalCatalog.getChapterName(), finalCatalog.getIndex(), contents);
                                addChapter(chapter);
                                leftBook.countDown();
                            } catch (IOException e) {
                                if (needFreshProcess) {
                                    tempProgress = (int) (((errorBook.getCount() - leftBook.getCount()) / (float) (2 * catalogSize - errorBook.getCount())) * 100);
                                    needFreshProcess = false;
                                }
                                eventListener.onDownload(tempProgress, e.getMessage() + "  重试章节 ： " + finalCatalog.getChapterName());
                                errorBook.countDown();
                                addQueue(finalCatalog);//重新加入队列，等待下载
                            }
                        });
                        catalog = catalogQueue.poll();
                    }
                }
            }
        }
        threadPool.shutdown();
        timer.cancel();
        chapters.sort(Comparator.comparingInt(Chapter::getIndex));
        eventListener.onDownload(100, "下载完成(" + chapters.size() + "章)，等待保存");
        return chapters;
    }

    private void addChapter(Chapter chapter) {
        synchronized (bufferLock) {
            chapters.add(chapter);
        }
    }

    private int getBufferSize() {
        synchronized (bufferLock) {
            return chapters.size();
        }
    }

    private void addQueue(Catalog catalog) {
        synchronized (queueLock) {
            catalogQueue.offer(catalog);
            //唤醒线程，添加所有章节到下载队列
            queueLock.notify();
        }
    }
}
