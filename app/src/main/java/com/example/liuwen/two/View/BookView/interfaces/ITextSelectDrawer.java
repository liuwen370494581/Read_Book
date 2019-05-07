package com.example.liuwen.two.View.BookView.interfaces;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.example.liuwen.two.View.BookView.bean.TxtChar;

import java.util.List;

/**
 * Created by bifan-wei
 * on 2017/12/4.
 */

public interface ITextSelectDrawer {
    void drawSelectedChar(TxtChar selectedChar, Canvas canvas, Paint paint);

    void drawSelectedLines(List<ITxtLine> selectedLines, Canvas canvas, Paint paint);
}
