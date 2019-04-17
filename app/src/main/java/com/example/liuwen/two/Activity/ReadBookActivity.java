package com.example.liuwen.two.Activity;

import android.os.Message;
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
import com.example.liuwen.two.R;
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
    private TextView mTvContent, mTvNextChapter, mTvControlPreviousChapter, mTvControlNextChapter;
    private FrameLayout mFrameLayoutControl;
    private boolean isShowControl = true;
    private TextView mTvTryAgain, mTvChapterTitle;
    private ImageView imgBack;

    private MyReadHandler myReadHandler = new MyReadHandler(getActivityContext(), (message, reference) -> {
        ReadBookActivity activity = (ReadBookActivity) reference.get();
        if (activity != null) {
            switch (message.what) {
                case 0:
                    PromptDialogUtils.getInstance().hidePromptDialog();
                    String content = (String) message.obj;
                    activity.mTvContent.setText(content);
                    activity.mTvTryAgain.setVisibility(View.GONE);
                    break;
                case 1:
                    PromptDialogUtils.getInstance().hidePromptDialog();
                    activity.mTvContent.setText("");
                    activity.mTvTryAgain.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    });


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
        mTvNextChapter = getView(R.id.preview_tv_next);
        mFrameLayoutControl = getView(R.id.preview_controlLayout);
        mTvControlPreviousChapter = getView(R.id.preview_previous);
        mTvControlNextChapter = getView(R.id.preview_next);
        mTvTryAgain = getView(R.id.tv_try_again);
        imgBack = getView(R.id.preview_back);
        mTvChapterTitle = getView(R.id.preview_title);
    }

    private void loadCatalog() {
//        PromptDialogUtils.getInstance().showPromptDialog("正在加载内容中");
//        Catalog catalog = catalogs.get(position);
//        ThreadPoolUtils.getInstance().getThreadPool().execute(() -> {
//            try {
//                String html = NetUtil.getHtml(catalog.getUrl(), mSite.getEncodeType());
//                List<String> content = mSite.parseContent(html);
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append(catalog.getChapterName()).append("\n\n");
//                for (String line : content) {
//                    if (!line.isEmpty()) {
//                        stringBuilder.append("      ").append(TextUtil.cleanContent(line)).append("\n\n");
//                    }
//                }
//                Message message = Message.obtain();
//                message.what = 0;
//                message.obj = stringBuilder.toString();
//                myReadHandler.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//                myReadHandler.sendEmptyMessage(1);
//            } finally {
//                //关闭线程池
//                ThreadPoolUtils.getInstance().getThreadPool().shutdown();
//            }
//        });
    }

    /**
     * 加载下一章
     */
    private void toNextChapter() {
        int p = position - 1;
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
        int p = position + 1;
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
        mTvContent.setOnClickListener(v -> {
            if (isShowControl) {
                mFrameLayoutControl.setVisibility(View.VISIBLE);
            } else {
                mFrameLayoutControl.setVisibility(View.GONE);
            }
            isShowControl = !isShowControl;
        });

        mTvNextChapter.setOnClickListener(v -> toNextChapter());

        mTvControlNextChapter.setOnClickListener(v -> toNextChapter());

        mTvControlPreviousChapter.setOnClickListener(v -> {
            toPreviousChapter();
        });

        imgBack.setOnClickListener(v -> {
            closeActivity();
        });


    }
}
