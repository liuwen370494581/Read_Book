package com.example.liuwen.two.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liuwen.two.Base.BaseFragment;
import com.example.liuwen.two.R;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/12 15:20
 * desc   :
 */
public class UserInfoFragment extends BaseFragment {
    @Override
    public void initData() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((TextView) view.findViewById(R.id.title)).setText("用户中心");
        ((LinearLayout) view.findViewById(R.id.right_view)).setVisibility(View.VISIBLE);

    }
}
