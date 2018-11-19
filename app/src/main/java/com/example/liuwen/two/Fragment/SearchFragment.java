package com.example.liuwen.two.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liuwen.two.Action.CatalogsHolder;
import com.example.liuwen.two.Activity.SearchResultActivity;
import com.example.liuwen.two.Adapter.ChapterAdapter;
import com.example.liuwen.two.Base.BaseFragment;
import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.EventBus.BindEventBus;
import com.example.liuwen.two.EventBus.C;
import com.example.liuwen.two.EventBus.Event;
import com.example.liuwen.two.R;
import com.example.liuwen.two.View.SearchBar;
import com.example.liuwen.two.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import GreenDao3.BookDaoHolder;
import GreenDao3.CatalogDaoHolder;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/12 15:20
 * desc   :
 */
@BindEventBus
public class SearchFragment extends BaseFragment {
    private SearchBar mSearchBar;
    private RecyclerView mRecyclerView;
    private TextView mTvClean;
    private ChapterAdapter mAdapter;
    private List<Catalog> mCatalogList = new ArrayList<>();

    @Override
    public void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        setListener();
        return view;
    }


    private void initView(View view) {
        mSearchBar = view.findViewById(R.id.id_search_bar);
        mRecyclerView = view.findViewById(R.id.id__recycler_view);
        mTvClean = view.findViewById(R.id.id_search_book_clean);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ChapterAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mCatalogList = CatalogDaoHolder.query();
        if (mCatalogList != null && mCatalogList.size() > 0) {
            mAdapter.setData(mCatalogList);
        }
    }

    private void setListener() {
        mTvClean.setOnClickListener(v -> {
            mAdapter.clear();
            CatalogDaoHolder.deleteAllData();
            mAdapter.notifyDataSetChanged();
        });
        mAdapter.setOnRVItemClickListener((parent, itemView, position) -> {
            Intent intent = new Intent(getContext(), SearchResultActivity.class);
            intent.putExtra("text", mCatalogList.get(position).getChapterName());
            getContext().startActivity(intent);
            mSearchBar.setEtSearchText(mCatalogList.get(position).getChapterName());
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(Event event) {
        switch (event.getCode()) {
            case C.EventCode.BookHistory:
                mCatalogList = CatalogDaoHolder.query();
                if (mCatalogList != null && mCatalogList.size() > 0) {
                    mAdapter.setData(mCatalogList);
                }
                break;
        }
    }


}
