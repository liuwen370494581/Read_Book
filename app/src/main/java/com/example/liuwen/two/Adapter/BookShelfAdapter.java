package com.example.liuwen.two.Adapter;

import android.support.v7.widget.RecyclerView;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/23 15:24
 * desc   : 适配器
 */
public class BookShelfAdapter extends BGARecyclerViewAdapter<Book> {
    public BookShelfAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_book_shelf);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Book model) {
        helper.setText(R.id.txt_book_name, model.getBookName());
    }
}
