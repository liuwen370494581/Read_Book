package com.example.liuwen.two.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liuwen.two.Adapter.BookShelfAdapter;
import com.example.liuwen.two.Base.BaseActivity;
import com.example.liuwen.two.Base.BaseFragment;
import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.EventBus.BindEventBus;
import com.example.liuwen.two.EventBus.C;
import com.example.liuwen.two.EventBus.Event;
import com.example.liuwen.two.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import GreenDao3.BookDaoHolder;
import GreenDao3.CatalogDaoHolder;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/12 15:20
 * desc   :
 */

public class BookCityFragment extends BaseFragment {



    @Override
    public void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_city, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((TextView) view.findViewById(R.id.title)).setText("书城");
        ((LinearLayout) view.findViewById(R.id.right_view)).setVisibility(View.VISIBLE);


    }


}
