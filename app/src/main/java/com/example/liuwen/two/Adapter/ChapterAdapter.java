package com.example.liuwen.two.Adapter;

import android.support.v7.widget.RecyclerView;

import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.Bean.Chapter;
import com.example.liuwen.two.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/13 15:15
 * desc   :
 */
public class ChapterAdapter extends BGARecyclerViewAdapter<Catalog> {


    public ChapterAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_chapter);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Catalog model) {
        helper.setText(R.id.tv_content, model.getChapterName());
    }
}
