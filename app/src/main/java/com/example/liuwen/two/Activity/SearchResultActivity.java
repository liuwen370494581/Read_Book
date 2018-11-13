package com.example.liuwen.two.Activity;

import android.content.Context;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.liuwen.two.Action.MyReadHandler;
import com.example.liuwen.two.Adapter.SearchResultAdapter;
import com.example.liuwen.two.Base.BaseActivity;
import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.R;
import com.example.liuwen.two.View.DividerItemDecoration;
import com.example.liuwen.two.View.promptlibrary.PromptDialog;
import com.example.liuwen.two.engine.Downloader;
import com.example.liuwen.two.listener.EventListener;
import com.example.liuwen.two.listener.OnHandlerListener;
import com.example.liuwen.two.utils.ToastUtils;
import com.irozon.sneaker.Sneaker;
import com.liaoinstan.springview.widget.SpringView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/12 16:45
 * desc   :
 */
public class SearchResultActivity extends BaseActivity implements EventListener {

    private String msg = "信息错误";
    private String bookName;
    private List<Book> mSearchBooks = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private SpringView mSpringView;
    private SearchResultAdapter mAdapter;
    private Downloader mDownLoader;
    private PromptDialog promptDialog;
    private MyReadHandler myReadHandler = new MyReadHandler(this, new OnHandlerListener() {
        @Override
        public void handlerMessage(Message message, WeakReference<Context> reference) {
            SearchResultActivity activity = (SearchResultActivity) reference.get();
            if (activity != null) {
                switch (message.what) {
                    case 0:
                        activity.promptDialog.dismiss();
                        //获取成功
                        mSearchBooks = (List<Book>) message.obj;
                        ToastUtils.showCenterToast(getActivityContext(), mSearchBooks.toString());
                        break;
                    case 1:
                        String msg = (String) message.obj;
                        Sneaker.with(activity)
                                .setTitle("正在查找书源中")
                                .setMessage(msg != null ? msg : "返回信息有误")
                                .sneakSuccess();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        break;
                }
            }
        }
    });

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
        promptDialog = new PromptDialog(this);
        promptDialog.getDefaultBuilder().touchAble(true).round(3).withAnim(true);
        mDownLoader = new Downloader(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        mAdapter = new SearchResultAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivityContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivityContext()));
        mRecyclerView.setAdapter(mAdapter);
        searchBookForName();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void searchBookForName() {
        mAdapter.setTitle(bookName);
        promptDialog.showLoading("正在搜索中");
        new Thread(() -> {
            mDownLoader.search(bookName);
        }).start();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onChooseBook(List<Book> books) {
        Message message = Message.obtain();
        message.what = 0;
        message.obj = books;
        myReadHandler.sendMessage(message);

    }

    @Override
    public void pushMessage(String msg) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = msg;
        myReadHandler.sendMessage(message);
    }

    @Override
    public void onDownload(int progress, String msg) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = msg;
        myReadHandler.sendMessage(message);
    }

    @Override
    public void onEnd(String msg, File file) {
        Message message = Message.obtain();
        message.what = 3;
        message.obj = msg;
        myReadHandler.sendMessage(message);
    }

    @Override
    public void onError(String msg, Exception e) {
        Message message = Message.obtain();
        message.what = 4;
        message.obj = msg;
        myReadHandler.sendMessage(message);
    }
}
