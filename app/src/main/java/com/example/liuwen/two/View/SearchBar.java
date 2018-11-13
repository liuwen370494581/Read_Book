package com.example.liuwen.two.View;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liuwen.two.Activity.SearchResultActivity;
import com.example.liuwen.two.Activity.SourceSettingActivity;
import com.example.liuwen.two.R;
import com.example.liuwen.two.utils.ToastUtils;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/09/25 14:48
 * desc   :
 */
public class SearchBar extends LinearLayout {
    private EditText mEtSearch;
    private ImageView mIvDelete;
    private TextView mTvSearch;
    private ImageView mIvSource;

    public SearchBar(Context context) {
        super(context);
        initView(context);
    }

    public SearchBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SearchBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.head_search, this, true);
        mEtSearch = (EditText) contentView.findViewById(R.id.et_search_bar_view);
        mIvDelete = (ImageView) contentView.findViewById(R.id.iv_search_bar_delete);
        mTvSearch = (TextView) contentView.findViewById(R.id.tv_search_bar_search);
        mIvSource = (ImageView) contentView.findViewById(R.id.iv_search_bar_source);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(HORIZONTAL);
        mEtSearch.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    search();
                    return true;
                }
                return false;
            }
        });

        mIvDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearch.setText("");
            }
        });

        mTvSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        mIvSource.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent intent = new Intent(context, SourceSettingActivity.class);
                 context.startActivity(intent);
            }
        });
    }

    private void search() {
        String text = mEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.showCenterToast(getContext(), getContext().getString(R.string.book_is_not_empty));
            return;
        }
         Intent intent = new Intent(getContext(), SearchResultActivity.class);
        intent.putExtra("text", text);
        getContext().startActivity(intent);

    }
}
