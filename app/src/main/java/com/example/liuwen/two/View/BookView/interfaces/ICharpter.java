package com.example.liuwen.two.View.BookView.interfaces;

/**
 * Created by HP on 2017/11/15.
 */

public interface ICharpter {
    int getParagraphNum();
    int getStartParagraphIndex();
    int getEndParagraphIndex();
    String getTitle();
    void setStartParagraphIndex(int index);
    void setEndParagraphIndex(int index);
}
