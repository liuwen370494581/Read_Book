package com.example.liuwen.two.View.BookView.interfaces;
import com.example.liuwen.two.View.BookView.bean.TxtChar;

/**
 * Created by bifan-wei
 * on 2017/12/5.
 */

public interface ITextSelectListener {
    void onTextChanging(TxtChar firstSelectedChar, TxtChar lastSelectedChar);
    void onTextChanging(String selectText);
    void onTextSelected(String selectText);
}
