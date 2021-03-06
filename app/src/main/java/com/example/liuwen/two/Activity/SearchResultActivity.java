package com.example.liuwen.two.Activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.liuwen.two.Action.BookAction;
import com.example.liuwen.two.Action.MyReadHandler;
import com.example.liuwen.two.Adapter.SearchResultAdapter;
import com.example.liuwen.two.Base.BaseActivity;
import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.EventBus.C;
import com.example.liuwen.two.EventBus.Event;
import com.example.liuwen.two.EventBus.EventBusUtil;
import com.example.liuwen.two.R;
import com.example.liuwen.two.Rx.Disposable;
import com.example.liuwen.two.Rx.Subscriber;
import com.example.liuwen.two.View.DividerItemDecoration;
import com.example.liuwen.two.engine.EasyBook;
import com.example.liuwen.two.engine.parser.SearchObserver;
import com.example.liuwen.two.listener.OnHandlerListener;
import com.example.liuwen.two.utils.DateTimeUtils;
import com.example.liuwen.two.utils.PromptDialogUtils;
import com.example.liuwen.two.utils.SneakerUtils;
import com.example.liuwen.two.utils.ThreadPoolUtils;
import com.liaoinstan.springview.widget.SpringView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import GreenDao3.CatalogDaoHolder;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/12 16:45
 * desc   : 书籍搜索结果页面
 */
public class SearchResultActivity extends BaseActivity {

    private String msg = "信息错误";
    private String bookName;
    private List<Book> mSearchBooks = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private SpringView mSpringView;
    private SearchResultAdapter mAdapter;
    private Disposable searchDisposable;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initView() {
        showLeftView();
        setCenterText("搜索结果");
        bookName = getIntent().getStringExtra("text");
        mRecyclerView = findViewById(R.id.recycler_tv);
        mSpringView = findViewById(R.id.id_spring_view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        mAdapter = new SearchResultAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivityContext()));
        mRecyclerView.setAdapter(mAdapter);
        searchBookForName();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void searchBookForName() {
        mAdapter.setTitle(bookName);
        PromptDialogUtils.getInstance().showPromptDialog("正在搜索中");
        searchDisposable = EasyBook.search(bookName).subscribe(new Subscriber<List<Book>>() {
            @Override
            public void onFinish(@NonNull List<Book> books) {
                PromptDialogUtils.getInstance().hidePromptDialog();
                //获取成功
                if (books.size() > 0) {
                    mSearchBooks = books;
                    mAdapter.setData(mSearchBooks);
                    if (BookAction.isSameBooK(bookName)) {
                        Catalog catalog = BookAction.backCatalogHistory(bookName);
                        catalog.setUrl(DateTimeUtils.getCurrentTimeExactToSecond());
                        CatalogDaoHolder.update(catalog);
                    } else {
                        CatalogDaoHolder.insert(new Catalog(CatalogDaoHolder.getCount(), bookName, DateTimeUtils.getCurrentTimeExactToSecond(), 0));
                    }
                    EventBusUtil.sendEvent(new Event(C.EventCode.BookHistory));
                }
            }

            @Override
            public void onError(@NonNull Exception e) {
                SneakerUtils.setOtherMessage(SearchResultActivity.this, "错误信息反馈", e.getMessage(), R.color.red, R.drawable.ic_error);
            }

            @Override
            public void onMessage(@NonNull String msg) {
                SneakerUtils.setCommonMessage(SearchResultActivity.this, "正在搜索书籍来源", msg);
            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRVItemClickListener((parent, itemView, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("bookInfo", mAdapter.getItem(position));
            openActivity(BookInfoActivity.class, bundle);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchDisposable.dispose();
    }
}
