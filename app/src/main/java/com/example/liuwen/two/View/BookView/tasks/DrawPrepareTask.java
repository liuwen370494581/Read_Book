package com.example.liuwen.two.View.BookView.tasks;

import android.graphics.Color;
import com.example.liuwen.two.View.BookView.interfaces.ILoadListener;
import com.example.liuwen.two.View.BookView.interfaces.ITxtTask;
import com.example.liuwen.two.View.BookView.main.PaintContext;
import com.example.liuwen.two.View.BookView.main.TxtConfig;
import com.example.liuwen.two.View.BookView.main.TxtReaderContext;
import com.example.liuwen.two.View.BookView.utils.ELogger;

/**
 * Created by bifan-wei
 * on 2017/11/27.
 */

public class DrawPrepareTask implements ITxtTask {
    private String tag = "DrawPrepareTask";

    @Override
    public void Run(ILoadListener callBack, TxtReaderContext readerContext) {
        callBack.onMessage("start do DrawPrepare");
        ELogger.log(tag, "do DrawPrepare");
        initPainContext(readerContext.getPaintContext(), readerContext.getTxtConfig());
        readerContext.getPaintContext().textPaint.setColor(Color.WHITE);
        ITxtTask txtTask = new BitmapProduceTask();
        txtTask.Run(callBack, readerContext);
    }

    private void initPainContext(PaintContext paintContext, TxtConfig txtConfig) {
        TxtConfigInitTask.initPainContext(paintContext, txtConfig);
    }
}
