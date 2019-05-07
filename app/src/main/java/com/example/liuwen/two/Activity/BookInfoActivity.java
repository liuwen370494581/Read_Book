package com.example.liuwen.two.Activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.liuwen.two.Action.BookAction;
import com.example.liuwen.two.Action.CatalogsHolder;
import com.example.liuwen.two.Action.MyReadHandler;
import com.example.liuwen.two.Adapter.ChapterAdapter;
import com.example.liuwen.two.Base.AppInfo;
import com.example.liuwen.two.Base.BaseActivity;
import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.Bean.Chapter;
import com.example.liuwen.two.EventBus.C;
import com.example.liuwen.two.EventBus.Event;
import com.example.liuwen.two.EventBus.EventBusUtil;
import com.example.liuwen.two.R;
import com.example.liuwen.two.Rx.Disposable;
import com.example.liuwen.two.Rx.Subscriber;
import com.example.liuwen.two.engine.EasyBook;
import com.example.liuwen.two.engine.TxtParser;
import com.example.liuwen.two.utils.DateTimeUtils;
import com.example.liuwen.two.utils.GlideUtils;
import com.example.liuwen.two.utils.PromptDialogUtils;
import com.example.liuwen.two.utils.SneakerUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import GreenDao3.BookDaoHolder;

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
    private TextView tvBookTitle, tvBookAuthor, tvBookNewChapter, tvBookUpdateTime, tvBookSource, tvOrderBy;
    private Button btnBookRead, btnAddBook;
    private String mStrDesc = "倒序";
    private String mStrAsc = "正序";
    private ChapterAdapter mAdapter;
    private List<Catalog> mCatalogList = new ArrayList<>();
    private Disposable CatalogDisposable;
    private TxtParser mTxtParser = new TxtParser();//解析

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
        tvOrderBy = getView(R.id.tv_order_by);
    }

    @Override
    protected void initData() {
        mCurrentBook = (Book) getIntent().getSerializableExtra("bookInfo");
        if (mCurrentBook != null) {
            GlideUtils.loadImage(ivBookCover, mCurrentBook.getImageUrl(), R.mipmap.ic_default_cover, R.mipmap.ic_default_cover);
            tvBookTitle.setText(mCurrentBook.getBookName());
            tvBookAuthor.setText(mCurrentBook.getAuthor());
            tvBookUpdateTime.setText("最后更新：" + mCurrentBook.getLastUpdateTime());
            tvBookNewChapter.setText("最新章节：" + mCurrentBook.getLastChapterName());
            tvBookSource.setText("来源：" + mCurrentBook.getSiteName());
            if (BookAction.isSameBookFormBookBean(mCurrentBook.getBookName())) {
                btnAddBook.setText("已添加");
            } else {
                btnAddBook.setText("添加书架");
            }
        }
        mAdapter = new ChapterAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivityContext()));
        mRecyclerView.setAdapter(mAdapter);
        searchBookChapter();
    }

    private void searchBookChapter() {
        PromptDialogUtils.getInstance().showPromptDialog("加载目录中");
        CatalogDisposable = EasyBook.getCatalog(mCurrentBook).subscribe(new Subscriber<List<Catalog>>() {
            @Override
            public void onFinish(@NonNull List<Catalog> catalogs) {
                PromptDialogUtils.getInstance().hidePromptDialog();
                mCatalogList = catalogs;
                mAdapter.setData(mCatalogList);
            }

            @Override
            public void onError(@NonNull Exception e) {
                PromptDialogUtils.getInstance().hidePromptDialog();
                SneakerUtils.setOtherMessage(BookInfoActivity.this, "获取结果", "获取目录失败", R.color.red, R.drawable.ic_error);
            }

            @Override
            public void onMessage(@NonNull String msg) {


            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }

    @Override
    protected void setListener() {
        tvOrderBy.setOnClickListener(v -> {
            String str = tvOrderBy.getText().toString();
            if (str.equals(mStrAsc)) {
                //倒序
                mAdapter.orderByDesc();
                tvOrderBy.setText(mStrDesc);
            } else if (str.equals(mStrDesc)) {
                //正序
                mAdapter.orderByAsc();
                tvOrderBy.setText(mStrAsc);
            }
        });

        mAdapter.setOnRVItemClickListener((parent, itemView, position) -> {
            if (mAdapter.getData() != null) {
                CatalogsHolder.getInstance().setCatalogs((ArrayList<Catalog>) mAdapter.getData(), mCurrentBook);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", mCurrentBook);
                bundle.putInt("position", position);
                openActivity(ReadBookActivity.class, bundle);
            }
        });

//        //添加到书架
//        btnAddBook.setOnClickListener(v -> {
//            BookDaoHolder.insert(new Book(BookDaoHolder.getCount(), mCurrentBook.getBookName(),
//                    mCurrentBook.getAuthor(), mCurrentBook.getUrl(), mCurrentBook.getChapterSize(),
//                    mCurrentBook.getLastUpdateTime(), mCurrentBook.getLastChapterName(),
//                    mCurrentBook.getSource(), DateTimeUtils.getCurrentTimeExactToSecond()));
//            btnAddBook.setText("已添加");
//            EventBusUtil.sendEvent(new Event(C.EventCode.AddBookShelf));
//        });

        //开始阅读
        btnBookRead.setOnClickListener(v -> {
            CatalogsHolder.getInstance().setCatalogs((ArrayList<Catalog>) mCatalogList, mCurrentBook);
            Bundle bundle = new Bundle();
            bundle.putSerializable("book", mCurrentBook);
            bundle.putInt("position", 0);//从第一章开始阅读
            openActivity(ReadBookActivity.class, bundle);

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CatalogDisposable.dispose();
    }
}
