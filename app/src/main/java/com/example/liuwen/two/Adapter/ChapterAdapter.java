package com.example.liuwen.two.Adapter;

import android.support.v7.widget.RecyclerView;

import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.Bean.Chapter;
import com.example.liuwen.two.R;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/13 15:15
 * desc   :
 */
public class ChapterAdapter extends BGARecyclerViewAdapter<Catalog> {

    private boolean mIsAsc = true;

    public ChapterAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_chapter);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Catalog model) {
        helper.setText(R.id.tv_content, model.getChapterName());
    }

    //正序
    public void orderByDesc() {
        if (mIsAsc) {
            transformData();
            mIsAsc = false;
        }
    }

    //倒序
    public void orderByAsc() {
        if (!mIsAsc) {
            transformData();
            mIsAsc = true;
        }

    }

    private void transformData() {
        if (getData() != null && getData().size() != 0) {
            List<Catalog> catalogList = new ArrayList<>(getData().size());
            for (int i = getData().size() - 1; i >= 0; i--) {
                catalogList.add(getData().get(i));
            }
            getData().clear();
            getData().addAll(catalogList);
            notifyDataSetChanged();
        }
    }

    public boolean isAsc() {
        return mIsAsc;
    }


    @Override
    public void clear() {
        mIsAsc = true;
        super.clear();

    }
}
