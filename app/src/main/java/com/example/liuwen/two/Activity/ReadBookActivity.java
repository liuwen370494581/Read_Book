package com.example.liuwen.two.Activity;

import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liuwen.two.Action.CatalogsHolder;
import com.example.liuwen.two.Action.MyReadHandler;
import com.example.liuwen.two.Base.BaseActivity;
import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.Bean.Chapter;
import com.example.liuwen.two.R;
import com.example.liuwen.two.Rx.Disposable;
import com.example.liuwen.two.Rx.Subscriber;
import com.example.liuwen.two.View.MyScrollView;
import com.example.liuwen.two.engine.EasyBook;
import com.example.liuwen.two.engine.TxtParser;
import com.example.liuwen.two.utils.NetUtil;
import com.example.liuwen.two.utils.PromptDialogUtils;
import com.example.liuwen.two.utils.TextUtil;
import com.example.liuwen.two.utils.ThreadPoolUtils;
import com.example.liuwen.two.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/19 14:38
 * desc   : 阅读小说的新界面
 */
public class ReadBookActivity extends BaseActivity {
    private List<Catalog> catalogs = new ArrayList<>();
    private String loading = "加载中";
    private Book mCurrentBook;
    private int position = 0;
    private TextView mTvContent, mTvControlPreviousChapter, mTvControlNextChapter;
    private FrameLayout mFrameLayoutControl;
    private boolean isShowControl = true;
    private TextView mTvTryAgain, mTvChapterTitle;
    private ImageView imgBack;
    private Disposable mDisposable;
    private TxtParser mTxtParser = new TxtParser();//解析
    private MyScrollView myScrollView;

    @Override
    protected int setLayoutRes() {
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_read_book;
    }

    @Override
    protected void initView() {
        mTvContent = getView(R.id.preview_tv);
        mFrameLayoutControl = getView(R.id.preview_controlLayout);
        mTvControlPreviousChapter = getView(R.id.preview_previous);
        mTvControlNextChapter = getView(R.id.preview_next);
        mTvTryAgain = getView(R.id.tv_try_again);
        imgBack = getView(R.id.preview_back);
        mTvChapterTitle = getView(R.id.preview_title);
        myScrollView = getView(R.id.preview_scrollView);

    }

    private void loadCatalog() {
        PromptDialogUtils.getInstance().showPromptDialog("正在加载内容中");
        Catalog catalog = catalogs.get(position);
        mDisposable = EasyBook.getContent(mCurrentBook, catalog).subscribe(new Subscriber<List<String>>() {
            @Override
            public void onFinish(@NonNull List<String> strings) {
                PromptDialogUtils.getInstance().hidePromptDialog();
                Chapter character = new Chapter(catalog.getChapterName(), catalog.getIndex(), strings);
                mTvContent.setText(mTxtParser.parseContent(character));
                mTvTryAgain.setVisibility(View.GONE);
            }

            @Override
            public void onError(@NonNull Exception e) {
                PromptDialogUtils.getInstance().hidePromptDialog();
                mTvContent.setText("");
                mTvTryAgain.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMessage(@NonNull String message) {

            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }

    /**
     * 加载下一章
     */
    private void toNextChapter() {
        int p = position + 1;
        if (p >= 0) {
            position = p;
            mTvContent.setText(loading);
            loadCatalog();
        } else {
            ToastUtils.showCenterToast(getActivityContext(), "已经是最后一页了");
        }

    }

    /**
     * 加载上一章
     */
    private void toPreviousChapter() {
        int p = position - 1;
        if (p < catalogs.size()) {
            position = p;
            mTvContent.setText(loading);
            loadCatalog();
        } else {
            ToastUtils.showCenterToast(getActivityContext(), "没有上一章了");
        }
    }

    @Override
    protected void initData() {
        catalogs = CatalogsHolder.getInstance().getCatalogs();
        mCurrentBook = (Book) getIntent().getSerializableExtra("book");
        position = getIntent().getIntExtra("position", 0);
        if (mCurrentBook != null) {
        }
        loadCatalog();
    }

    public void tryAgain(View view) {
        loadCatalog();
    }


    @Override
    protected void setListener() {
        myScrollView.setOnScrollChangeListener(new MyScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollToStart() {
                    // toPreviousChapter();
            }

            @Override
            public void onScrollToEnd() {
                toNextChapter();

            }
        });
        mTvContent.setOnClickListener(v -> {
            if (isShowControl) {
                mFrameLayoutControl.setVisibility(View.VISIBLE);
            } else {
                mFrameLayoutControl.setVisibility(View.GONE);
            }
            isShowControl = !isShowControl;
        });

        mTvControlNextChapter.setOnClickListener(v -> toNextChapter());

        mTvControlPreviousChapter.setOnClickListener(v -> {
            toPreviousChapter();
        });

        imgBack.setOnClickListener(v -> {
            closeActivity();
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }

}
