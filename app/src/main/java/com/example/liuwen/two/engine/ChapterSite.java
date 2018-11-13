package com.example.liuwen.two.engine;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Chapter;
import com.example.liuwen.two.listener.EventListener;
import com.example.liuwen.two.utils.FoxEpubWriter;
import com.example.liuwen.two.utils.TextUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 11:39
 * desc   :
 */
public abstract class ChapterSite implements ISite, IRegex {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public File download(Book book, Type type, String savePath, EventListener eventListener) {
        List<Chapter> chapters = new BookFucker().download(book.getUrl(), getEncodeType(), eventListener, getThreadCount(), this);
        try {
            File file = save(chapters, book.getBookName() + "-" + getSiteName(), savePath, type);
            eventListener.onEnd("文件保存成功，位置：" + file.getPath(), file);
            return file;
        } catch (IOException e) {
            eventListener.onError("文件保存失败", e);
            return null;
        }
    }

    private File save(List<Chapter> chapters, String bookName, String savePath, Type type) throws IOException {
        if (type == Type.EPUB) {
            String name = bookName + ".epub";
            savePath += File.separator + name;
            File file = new File(savePath);
            FoxEpubWriter foxEpubWriter = new FoxEpubWriter(file, bookName);
            for (Chapter chapter : chapters) {
                StringBuilder content = new StringBuilder();
                for (String line : chapter.getContents()) {
                    line = TextUtil.cleanContent(line);
                    content.append("<p>");
                    content.append("    ");
                    content.append(line);
                    content.append("</p>");
                }
                foxEpubWriter.addChapter(chapter.getChapterName(), content.toString());
            }
            foxEpubWriter.saveAll();
            return file;
        } else {
            savePath += File.separator + bookName + ".txt";
            File file = new File(savePath);
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file)));
            for (Chapter chapter : chapters) {
                bufferedWriter.write(chapter.getChapterName());
                bufferedWriter.write("\n\n");
                for (String line : chapter.getContents()) {
                    line = TextUtil.cleanContent(line);
                    //4个空格+正文+换行+空行
                    bufferedWriter.write("    ");
                    bufferedWriter.write(line);
                    bufferedWriter.write("\n\n");
                }
                //章节结束空三行，用来分割下一章节
                bufferedWriter.write("\n\n\n");
            }
            return file;
        }
    }

    @Override
    public int getThreadCount() {
        return 300;
    }

    @Override
    public String getEncodeType() {
        return "gbk";
    }
}
