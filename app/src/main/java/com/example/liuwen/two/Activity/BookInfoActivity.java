package com.example.liuwen.two.Activity;

import android.content.Context;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liuwen.two.Action.MyReadHandler;
import com.example.liuwen.two.Adapter.ChapterAdapter;
import com.example.liuwen.two.Base.BaseActivity;
import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.R;
import com.example.liuwen.two.engine.ChapterSite;
import com.example.liuwen.two.listener.EventListener;
import com.example.liuwen.two.listener.OnHandlerListener;
import com.example.liuwen.two.utils.GlideUtils;
import com.example.liuwen.two.utils.NetUtil;
import com.example.liuwen.two.utils.PromptDialogUtils;
import com.example.liuwen.two.utils.SneakerUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/13 13:45
 * desc   :
 */
public class BookInfoActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private Book mCurrentBook;
    private ImageView ivBookCover;
    private TextView tvBookTitle, tvBookAuthor, tvBookNewChapter, tvBookUpdateTime, tvBookSource;
    private TextView btnBookRead, btnAddBook;
    private ChapterAdapter mAdapter;
    private MyReadHandler myReadHandler = new MyReadHandler(getActivityContext(), new OnHandlerListener() {
        @Override
        public void handlerMessage(Message message, WeakReference<Context> reference) {
            BookInfoActivity activity = (BookInfoActivity) reference.get();
            if (activity != null) {
                switch (message.what) {
                    case 0:
                        PromptDialogUtils.getInstance().hidePromptDialog();
                        List<Catalog> mTemList = (List<Catalog>) message.obj;
                        activity.mAdapter.setData(mTemList);
                        break;
                    case 1:
                        PromptDialogUtils.getInstance().hidePromptDialog();
                        SneakerUtils.setOtherMessage(activity, "获取结果", "获取目录失败", R.color.red, R.drawable.ic_error);
                    default:
                        break;
                }
            }
        }
    });

    public BookInfoActivity() {
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_book_ino;
    }

    @Override
    protected void initView() {

        showLeftView();
        setCenterText("详细信息");
        ivBookCover = getView(R.id.iv_book_cover);
        tvBookTitle = getView(R.id.tv_book_title);
        tvBookAuthor = getView(R.id.tv_book_author);
        tvBookNewChapter = getView(R.id.tv_book_newest_chapter);
        tvBookUpdateTime = getView(R.id.tv_book_update_time);
        btnBookRead = getView(R.id.id_click_read);
        btnAddBook = getView(R.id.id_click_add_book);
        tvBookSource = getView(R.id.tv_search_item_source);
        mRecyclerView = getView(R.id.rv_search_list);

    }

    @Override
    protected void initData() {
        mCurrentBook = (Book) getIntent().getSerializableExtra("bookInfo");
        if (mCurrentBook != null) {
            GlideUtils.loadImage(ivBookCover, mCurrentBook.getUrl(), R.mipmap.ic_default_cover, R.mipmap.ic_default_cover);
            tvBookTitle.setText(mCurrentBook.getBookName());
            tvBookAuthor.setText(mCurrentBook.getAuthor());
            tvBookUpdateTime.setText("最后更新：" + mCurrentBook.getLastUpdateTime());
            tvBookNewChapter.setText("最新章节：" + mCurrentBook.getLastChapterName());
            tvBookSource.setText("来源：" + mCurrentBook.getSource());
        }
        mAdapter = new ChapterAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivityContext()));
        mRecyclerView.setAdapter(mAdapter);
        searchBookChapter();
    }

    private void searchBookChapter() {
        PromptDialogUtils.getInstance().showPromptDialog("加载目录中");
        Message message = Message.obtain();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ChapterSite site = (ChapterSite) mCurrentBook.getSite();
                    String html = NetUtil.getHtml(mCurrentBook.getUrl(), site.getEncodeType());

                    message.what = 0;
                    message.obj = site.parseCatalog(html, mCurrentBook.getUrl());
                    myReadHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = 1;
                    myReadHandler.sendMessage(message);
                }

            }
        }).start();

    }

    @Override
    protected void setListener() {


    }

}
