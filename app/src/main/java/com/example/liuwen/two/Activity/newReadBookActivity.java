package com.example.liuwen.two.Activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.liuwen.two.Action.CatalogsHolder;
import com.example.liuwen.two.Base.BaseActivity;
import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;
import com.example.liuwen.two.Bean.Chapter;
import com.example.liuwen.two.R;
import com.example.liuwen.two.Rx.Disposable;
import com.example.liuwen.two.Rx.Subscriber;
import com.example.liuwen.two.View.BookView.bean.TxtChar;
import com.example.liuwen.two.View.BookView.bean.TxtMsg;
import com.example.liuwen.two.View.BookView.interfaces.ICenterAreaClickListener;
import com.example.liuwen.two.View.BookView.interfaces.IChapter;
import com.example.liuwen.two.View.BookView.interfaces.ILoadListener;
import com.example.liuwen.two.View.BookView.interfaces.IPageChangeListener;
import com.example.liuwen.two.View.BookView.interfaces.IPageEdgeListener;
import com.example.liuwen.two.View.BookView.interfaces.ISliderListener;
import com.example.liuwen.two.View.BookView.interfaces.ITextSelectListener;
import com.example.liuwen.two.View.BookView.main.TxtConfig;
import com.example.liuwen.two.View.BookView.main.TxtReaderView;
import com.example.liuwen.two.View.ChapterList;
import com.example.liuwen.two.engine.EasyBook;
import com.example.liuwen.two.engine.TxtParser;
import com.example.liuwen.two.utils.PromptDialogUtils;
import com.example.liuwen.two.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2019/04/28 11:43
 * desc   :
 */
public class newReadBookActivity extends BaseActivity {

    private TxtReaderView mTxtReadView;
    private List<Catalog> catalogs = new ArrayList<>();
    private Book mCurrentBook;
    private int position = 0;

    private Disposable mDisposable;
    private TxtParser mTxtParser = new TxtParser();//解析
    protected MenuHolder mMenuHolder = new MenuHolder();


    protected Handler mHandler;
    protected boolean FileExist = true;
    protected ChapterList mChapterListPop;


    protected View mTopDecoration, mBottomDecoration;
    protected View mChapterMsgView;
    protected TextView mChapterMsgName;
    protected TextView mChapterMsgProgress;
    protected TextView mChapterNameText;
    protected TextView mChapterMenuText;
    protected TextView mProgressText;
    protected TextView mSettingText;
    protected TextView mSelectedText;
    protected TxtReaderView mTxtReaderView;
    protected View mTopMenu;
    protected View mBottomMenu;
    protected View mCoverView;
    protected View ClipboardView;
    protected String CurrentSelectedText;

    @Override
    protected int setLayoutRes() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_new_read_book;
    }

    @Override
    protected void initView() {
        mTxtReadView = (TxtReaderView) findViewById(R.id.activity_hwtxtplay_readerView);
        mChapterMsgView = findViewById(R.id.activity_hwtxtplay_chapter_msg);
        mChapterMsgName = (TextView) findViewById(R.id.chapter_name);
        mChapterMsgProgress = (TextView) findViewById(R.id.charpter_progress);
        mTopDecoration = findViewById(R.id.activity_hwtxtplay_top);
        mBottomDecoration = findViewById(R.id.activity_hwtxtplay_bottom);
        mTxtReaderView = (TxtReaderView) findViewById(R.id.activity_hwtxtplay_readerView);
        mChapterNameText = (TextView) findViewById(R.id.activity_hwtxtplay_chaptername);
        mProgressText = (TextView) findViewById(R.id.activity_hwtxtplay_progress_text);
        mSettingText = (TextView) findViewById(R.id.activity_hwtxtplay_setting_text);
        mTopMenu = findViewById(R.id.activity_hwtxtplay_menu_top);
        mBottomMenu = findViewById(R.id.activity_hwtxtplay_menu_bottom);
        mCoverView = findViewById(R.id.activity_hwtxtplay_cover);
        ClipboardView = findViewById(R.id.activity_hwtxtplay_Clipboar);
        mSelectedText = (TextView) findViewById(R.id.activity_hwtxtplay_selected_text);


        mMenuHolder.mTitle = (TextView) findViewById(R.id.txtreadr_menu_title);
        mMenuHolder.mPreChapter = (TextView) findViewById(R.id.txtreadr_menu_chapter_pre);
        mMenuHolder.mNextChapter = (TextView) findViewById(R.id.txtreadr_menu_chapter_next);
        mMenuHolder.mSeekBar = (SeekBar) findViewById(R.id.txtreadr_menu_seekbar);
        mMenuHolder.mTextSizeDel = findViewById(R.id.txtreadr_menu_textsize_del);
        mMenuHolder.mTextSize = (TextView) findViewById(R.id.txtreadr_menu_textsize);
        mMenuHolder.mTextSizeAdd = findViewById(R.id.txtreadr_menu_textsize_add);
        mMenuHolder.mBoldSelectedLayout = findViewById(R.id.txtreadr_menu_textsetting1_bold);
        mMenuHolder.mNormalSelectedLayout = findViewById(R.id.txtreadr_menu_textsetting1_normal);
        mMenuHolder.mCoverSelectedLayout = findViewById(R.id.txtreadr_menu_textsetting2_cover);
        mMenuHolder.mTranslateSelectedLayout = findViewById(R.id.txtreadr_menu_textsetting2_translate);

        mMenuHolder.mStyle1 = findViewById(R.id.hwtxtreader_menu_style1);
        mMenuHolder.mStyle2 = findViewById(R.id.hwtxtreader_menu_style2);
        mMenuHolder.mStyle3 = findViewById(R.id.hwtxtreader_menu_style3);
        mMenuHolder.mStyle4 = findViewById(R.id.hwtxtreader_menu_style4);
        mMenuHolder.mStyle5 = findViewById(R.id.hwtxtreader_menu_style5);
    }

    @Override
    protected void initData() {
        mHandler = new Handler();
        catalogs = CatalogsHolder.getInstance().getCatalogs();
        mCurrentBook = (Book) getIntent().getSerializableExtra("book");
        position = getIntent().getIntExtra("position", 0);
        if (mCurrentBook != null) {
            PromptDialogUtils.getInstance().showPromptDialog("正在加载内容中");
            loadCatalog();
        }
    }


    private void loadCatalog() {
        Catalog catalog = catalogs.get(position);
        mDisposable = EasyBook.getContent(mCurrentBook, catalog).subscribe(new Subscriber<List<String>>() {
            @Override
            public void onFinish(@NonNull List<String> strings) {
                PromptDialogUtils.getInstance().hidePromptDialog();
                Chapter character = new Chapter(catalog.getChapterName(), catalog.getIndex(), strings);
                mTxtReadView.loadText(mTxtParser.parseContent(character), new ILoadListener() {
                    @Override
                    public void onSuccess() {
                        mChapterNameText.setText(catalog.getChapterName());
                        initWhenLoadDone();
                    }

                    @Override
                    public void onFail(TxtMsg txtMsg) {

                    }

                    @Override
                    public void onMessage(String message) {

                    }
                });
            }

            @Override
            public void onError(@NonNull Exception e) {
                PromptDialogUtils.getInstance().hidePromptDialog();

            }

            @Override
            public void onMessage(@NonNull String message) {

            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }

    protected void initWhenLoadDone() {
//        if (mTxtReaderView.getTxtReaderContext().getFileMsg() != null) {
//            FileName = mTxtReaderView.getTxtReaderContext().getFileMsg().FileName;
//        }
        mMenuHolder.mTextSize.setText(mTxtReaderView.getTextSize() + "");
        mTopDecoration.setBackgroundColor(mTxtReaderView.getBackgroundColor());
        mBottomDecoration.setBackgroundColor(mTxtReaderView.getBackgroundColor());
        //mTxtReaderView.setLeftSlider(new MuiLeftSlider());//修改左滑动条
        //mTxtReaderView.setRightSlider(new MuiRightSlider());//修改右滑动条
        //字体初始化
        onTextSettingUi(mTxtReaderView.getTxtReaderContext().getTxtConfig().Bold);
        //翻页初始化
        onPageSwitchSettingUi(mTxtReaderView.getTxtReaderContext().getTxtConfig().SwitchByTranslate);
        //保存的翻页模式
        if (mTxtReaderView.getTxtReaderContext().getTxtConfig().SwitchByTranslate) {
            mTxtReaderView.setPageSwitchByTranslate();
        } else {
            mTxtReaderView.setPageSwitchByCover();
        }
        //章节初始化
        if (mTxtReaderView.getChapters() != null && mTxtReaderView.getChapters().size() > 0) {
            WindowManager m = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            m.getDefaultDisplay().getMetrics(metrics);
            int ViewHeight = metrics.heightPixels - mTopDecoration.getHeight();
            mChapterListPop = new ChapterList(this, ViewHeight, mTxtReaderView.getChapters(), mTxtReaderView.getTxtReaderContext().getParagraphData().getCharNum());
            mChapterListPop.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    IChapter chapter = (IChapter) mChapterListPop.getAdapter().getItem(i);
                    mChapterListPop.dismiss();
                    mTxtReaderView.loadFromProgress(chapter.getStartParagraphIndex(), 0);
                }
            });
            mChapterListPop.setBackGroundColor(mTxtReaderView.getBackgroundColor());
        }
    }

    private void onTextSettingUi(Boolean isBold) {
        if (isBold) {
            mMenuHolder.mBoldSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
            mMenuHolder.mNormalSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
        } else {
            mMenuHolder.mBoldSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
            mMenuHolder.mNormalSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
        }
    }

    private void onPageSwitchSettingUi(Boolean isTranslate) {
        if (isTranslate) {
            mMenuHolder.mTranslateSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
            mMenuHolder.mCoverSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
        } else {
            mMenuHolder.mTranslateSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
            mMenuHolder.mCoverSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
        }
    }


    @Override
    protected void setListener() {
        mSettingText.setOnClickListener(view -> Show(mTopMenu, mBottomMenu, mCoverView));
        setMenuListener();
        setSeekBarListener();
        setCenterClickListener();
        setPageChangeListener();
        setOnTextSelectListener();
        setStyleChangeListener();
        setExtraListener();
    }


    private void setExtraListener() {
        mMenuHolder.mPreChapter.setOnClickListener(new ChapterChangeClickListener(true));
        mMenuHolder.mNextChapter.setOnClickListener(new ChapterChangeClickListener(false));
        mMenuHolder.mTextSizeAdd.setOnClickListener(new TextChangeClickListener(true));
        mMenuHolder.mTextSizeDel.setOnClickListener(new TextChangeClickListener(false));
        mMenuHolder.mBoldSelectedLayout.setOnClickListener(new TextSettingClickListener(true));
        mMenuHolder.mNormalSelectedLayout.setOnClickListener(new TextSettingClickListener(false));
        mMenuHolder.mTranslateSelectedLayout.setOnClickListener(new SwitchSettingClickListener(true));
        mMenuHolder.mCoverSelectedLayout.setOnClickListener(new SwitchSettingClickListener(false));
    }

    protected void setStyleChangeListener() {
        mMenuHolder.mStyle1.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor1), StyleTextColors[0]));
        mMenuHolder.mStyle2.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor2), StyleTextColors[1]));
        mMenuHolder.mStyle3.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor3), StyleTextColors[2]));
        mMenuHolder.mStyle4.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor4), StyleTextColors[3]));
        mMenuHolder.mStyle5.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor5), StyleTextColors[4]));
    }

    protected void setOnTextSelectListener() {
        mTxtReaderView.setOnPageEdgeListener(new IPageEdgeListener() {
            @Override
            public void onCurrentFirstPage() {


            }

            @Override
            public void onCurrentLastPage() {

            }
        });


        mTxtReaderView.setOnTextSelectListener(new ITextSelectListener() {
            @Override
            public void onTextChanging(TxtChar firstSelectedChar, TxtChar lastSelectedChar) {
                //firstSelectedChar.Top
                //  firstSelectedChar.Bottom
                // 这里可以根据 firstSelectedChar与lastSelectedChar的top与bottom的位置
                //计算显示你要显示的弹窗位置，如果需要的话
            }

            @Override
            public void onTextChanging(String selectText) {
                onCurrentSelectedText(selectText);
            }

            @Override
            public void onTextSelected(String selectText) {
                onCurrentSelectedText(selectText);
            }
        });

        mTxtReaderView.setOnSliderListener(new ISliderListener() {
            @Override
            public void onShowSlider(TxtChar txtChar) {
                //TxtChar 为当前长按选中的字符
                // 这里可以根据 txtChar的top与bottom的位置
                //计算显示你要显示的弹窗位置，如果需要的话
            }

            @Override
            public void onShowSlider(String currentSelectedText) {
                onCurrentSelectedText(currentSelectedText);
                Show(ClipboardView);
            }

            @Override
            public void onReleaseSlider() {
                Gone(ClipboardView);
            }
        });

    }

    protected void setPageChangeListener() {
        mTxtReaderView.setPageChangeListener(progress -> {
            int p = (int) (progress * 1000);
            mProgressText.setText(((float) p / 10) + "%");
            mMenuHolder.mSeekBar.setProgress((int) (progress * 100));

        });
    }

    protected void setCenterClickListener() {
        mTxtReaderView.setOnCenterAreaClickListener(new ICenterAreaClickListener() {
            @Override
            public boolean onCenterClick(float widthPercentInView) {
                mSettingText.performClick();
                return true;
            }

            @Override
            public boolean onOutSideCenterClick(float widthPercentInView) {
                if (mBottomMenu.getVisibility() == View.VISIBLE) {
                    mSettingText.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    protected void setMenuListener() {
        mTopMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        mBottomMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        mCoverView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Gone(mTopMenu, mBottomMenu, mCoverView, mChapterMsgView);
                return true;
            }
        });
//        mChapterMenuText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mChapterListPop != null) {
//                    if (!mChapterListPop.isShowing()) {
//                        mChapterListPop.showAsDropDown(mTopDecoration);
//                        mHandler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                IChapter currentChapter = mTxtReaderView.getCurrentChapter();
//                                if (currentChapter != null) {
//                                    mChapterListPop.setCurrentIndex(currentChapter.getIndex());
//                                    mChapterListPop.notifyDataSetChanged();
//                                }
//                            }
//                        }, 300);
//                    } else {
//                        mChapterListPop.dismiss();
//                    }
//                }
//            }
//        });
        mTopMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mChapterListPop.isShowing()) {
                    mChapterListPop.dismiss();
                }
            }
        });
    }

    protected void setSeekBarListener() {
        mMenuHolder.mSeekBar.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                mTxtReaderView.loadFromProgress(mMenuHolder.mSeekBar.getProgress());
                Gone(mChapterMsgView);
            }
            return false;
        });
        mMenuHolder.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                if (fromUser) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onShowChapterMsg(progress);
                        }
                    });
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Gone(mChapterMsgView);
            }
        });

    }


    private void onShowChapterMsg(int progress) {
        if (mTxtReaderView != null && mChapterListPop != null) {
            IChapter chapter = mTxtReaderView.getChapterFromProgress(progress);
            if (chapter != null) {
                float p = (float) chapter.getStartIndex() / (float) mChapterListPop.getAllCharNum();
                if (p > 1) {
                    p = 1;
                }
                Show(mChapterMsgView);
                mChapterMsgName.setText(chapter.getTitle());
                mChapterMsgProgress.setText((int) (p * 100) + "%");
            }
        }
    }

    private void onCurrentSelectedText(String SelectedText) {
        mSelectedText.setText("选中" + (SelectedText + "").length() + "个文字");
        CurrentSelectedText = SelectedText;
    }


    private class TextSettingClickListener implements View.OnClickListener {
        private Boolean Bold;

        public TextSettingClickListener(Boolean bold) {
            Bold = bold;
        }

        @Override
        public void onClick(View view) {
            if (FileExist) {
                mTxtReaderView.setTextBold(Bold);
                onTextSettingUi(Bold);
            }
        }
    }

    private class SwitchSettingClickListener implements View.OnClickListener {
        private Boolean isSwitchTranslate;

        public SwitchSettingClickListener(Boolean pre) {
            isSwitchTranslate = pre;
        }

        @Override
        public void onClick(View view) {
            if (FileExist) {
                if (!isSwitchTranslate) {
                    mTxtReaderView.setPageSwitchByCover();
                } else {
                    mTxtReaderView.setPageSwitchByTranslate();
                }
                onPageSwitchSettingUi(isSwitchTranslate);
            }
        }
    }


    private class ChapterChangeClickListener implements View.OnClickListener {
        private Boolean Pre;

        public ChapterChangeClickListener(Boolean pre) {
            Pre = pre;
        }

        @Override
        public void onClick(View view) {
            if (Pre) {
                mTxtReaderView.jumpToPreChapter();
            } else {
                mTxtReaderView.jumpToNextChapter();
            }
        }
    }

    private class TextChangeClickListener implements View.OnClickListener {
        private Boolean Add;

        public TextChangeClickListener(Boolean pre) {
            Add = pre;
        }

        @Override
        public void onClick(View view) {
            if (FileExist) {
                int textSize = mTxtReaderView.getTextSize();
                if (Add) {
                    if (textSize + 2 <= TxtConfig.MAX_TEXT_SIZE) {
                        mTxtReaderView.setTextSize(textSize + 2);
                        mMenuHolder.mTextSize.setText(textSize + 2 + "");
                    }
                } else {
                    if (textSize - 2 >= TxtConfig.MIN_TEXT_SIZE) {
                        mTxtReaderView.setTextSize(textSize - 2);
                        mMenuHolder.mTextSize.setText(textSize - 2 + "");
                    }
                }
            }
        }
    }

    private class StyleChangeClickListener implements View.OnClickListener {
        private int BgColor;
        private int TextColor;

        public StyleChangeClickListener(int bgColor, int textColor) {
            BgColor = bgColor;
            TextColor = textColor;
        }

        @Override
        public void onClick(View view) {
            if (FileExist) {
                mTxtReaderView.setStyle(BgColor, TextColor);
                mTopDecoration.setBackgroundColor(BgColor);
                mBottomDecoration.setBackgroundColor(BgColor);
                if (mChapterListPop != null) {
                    mChapterListPop.setBackGroundColor(BgColor);
                }
            }
        }
    }

    protected void setBookName(String name) {
        mMenuHolder.mTitle.setText(name + "");
    }


    public void BackClick(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }


    private final int[] StyleTextColors = new int[]{
            Color.parseColor("#4a453a"),
            Color.parseColor("#505550"),
            Color.parseColor("#453e33"),
            Color.parseColor("#8f8e88"),
            Color.parseColor("#27576c")
    };

    protected void Show(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    protected void Gone(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    protected class MenuHolder {
        public TextView mTitle;
        public TextView mPreChapter;
        public TextView mNextChapter;
        public SeekBar mSeekBar;
        public View mTextSizeDel;
        public View mTextSizeAdd;
        public TextView mTextSize;
        public View mBoldSelectedLayout;
        public View mNormalSelectedLayout;
        public View mCoverSelectedLayout;
        public View mTranslateSelectedLayout;
        public View mStyle1;
        public View mStyle2;
        public View mStyle3;
        public View mStyle4;
        public View mStyle5;
    }
}
