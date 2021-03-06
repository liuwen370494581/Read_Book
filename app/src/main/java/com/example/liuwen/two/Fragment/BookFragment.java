package com.example.liuwen.two.Fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liuwen.two.Activity.BookInfoActivity;
import com.example.liuwen.two.Adapter.BookShelfAdapter;
import com.example.liuwen.two.Base.BaseFragment;
import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.EventBus.BindEventBus;
import com.example.liuwen.two.EventBus.C;
import com.example.liuwen.two.EventBus.Event;
import com.example.liuwen.two.R;
import com.example.liuwen.two.utils.GridSpacingItemDecoration;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import GreenDao3.BookDaoHolder;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/12 15:20
 * desc   : 书架
 */
@BindEventBus
public class BookFragment extends BaseFragment {
    private List<Book> mBookList = new ArrayList<>();
    private SpringView mSpringView;
    private RecyclerView mRecyclerView;
    private BookShelfAdapter mAdapter;

    @Override
    public void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((TextView) view.findViewById(R.id.title)).setText("书架");
        ((LinearLayout) view.findViewById(R.id.right_view)).setVisibility(View.VISIBLE);
        mSpringView = (SpringView) view.findViewById(R.id.id_spring_view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_tv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getFragmentContext(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new BookShelfAdapter(mRecyclerView);
        getBookShelfDate();
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 20, true));
        mRecyclerView.setAdapter(mAdapter);
        setListener();
    }

    /**
     * 获取书架的数据
     */
    private void getBookShelfDate() {
        mBookList = BookDaoHolder.query();
        if (mBookList.size() > 0) {
            mAdapter.setData(mBookList);
        }
    }

    private void setListener() {
        mSpringView.setFooter(new DefaultFooter(getFragmentContext()));
        mSpringView.setHeader(new DefaultHeader(getFragmentContext()));
        mSpringView.setType(SpringView.Type.FOLLOW);
        mSpringView.setEnableFooter(true);//禁用foot
        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mSpringView.onFinishFreshAndLoad();
                getBookShelfDate();
            }

            @Override
            public void onLoadmore() {
                mSpringView.onFinishFreshAndLoad();
            }
        });

        mAdapter.setOnRVItemClickListener((parent, itemView, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("bookInfo", mAdapter.getItem(position));
            openActivity(BookInfoActivity.class, bundle);
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(Event event) {
        switch (event.getCode()) {
            case C.EventCode.AddBookShelf:
                getBookShelfDate();
                break;
        }
    }
}
