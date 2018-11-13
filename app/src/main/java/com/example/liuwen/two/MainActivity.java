package com.example.liuwen.two;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liuwen.two.Adapter.MainAdapter;
import com.example.liuwen.two.Base.BaseActivity;
import com.example.liuwen.two.Fragment.BookCityFragment;
import com.example.liuwen.two.Fragment.BookFragment;
import com.example.liuwen.two.Fragment.SearchFragment;
import com.example.liuwen.two.Fragment.UserInfoFragment;
import com.example.liuwen.two.View.MyViewPager;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {
    private MyViewPager myViewPager;
    private TabLayout mTableLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTabTextArr;
    private MainAdapter mMainAdapter;
    private final int[] mImgNormalResArr = {R.mipmap.ic_tab_home_normal, R.mipmap.ic_tab_search_normal, R.mipmap.ic_tab_discover_normal, R.mipmap.ic_tab_mine_normal};
    private final int[] mImgSelectedResArr = {R.mipmap.ic_tab_home_pressed, R.mipmap.ic_tab_search_pressed, R.mipmap.ic_tab_discover_pressed, R.mipmap.ic_tab_mine_pressed};


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        myViewPager = getView(R.id.id_viewpager);
        mTableLayout = getView(R.id.id_tab_layout);
        setCenterText(getString(R.string.app_name));


    }

    @Override
    protected void initData() {
        mTabTextArr = getResources().getStringArray(R.array.tabs);
        mFragments.add(new BookFragment());
        mFragments.add(new SearchFragment());
        mFragments.add(new BookCityFragment());
        mFragments.add(new UserInfoFragment());
        mMainAdapter = new MainAdapter(mFragments, getSupportFragmentManager());
        myViewPager.setAdapter(mMainAdapter);
        myViewPager.setOffscreenPageLimit(0);
        myViewPager.setSlipping(false);
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);
        mTableLayout.addTab(mTableLayout.newTab().setCustomView(getTabView(0)));
        mTableLayout.addTab(mTableLayout.newTab().setCustomView(getTabView(1)));
        mTableLayout.addTab(mTableLayout.newTab().setCustomView(getTabView(2)));
        mTableLayout.addTab(mTableLayout.newTab().setCustomView(getTabView(3)));
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelectedTabStyle(mTableLayout, position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setSelectedTabStyle(mTableLayout, mTableLayout.getSelectedTabPosition());
                myViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setSelectedTabStyle(mTableLayout, mTableLayout.getSelectedTabPosition());
    }

    @Override
    protected void setListener() {

    }


    private void setSelectedTabStyle(TabLayout tabLayout, int position) {
        try {
            TextView tv = null;
            ImageView img = null;
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tv = (TextView) tab.getCustomView().findViewById(R.id.id_tab_tv);
                tv.setTextColor(ContextCompat.getColor(this, R.color.black));
                img = (ImageView) tab.getCustomView().findViewById(R.id.id_tab_img);
                img.setImageResource(mImgNormalResArr[i]);
            }
            TabLayout.Tab selectedTab = tabLayout.getTabAt(position);
            tv = (TextView) selectedTab.getCustomView().findViewById(R.id.id_tab_tv);
            tv.setTextColor(ContextCompat.getColor(this, R.color.main_text_color_focus));
            img = (ImageView) selectedTab.getCustomView().findViewById(R.id.id_tab_img);
            img.setImageResource(mImgSelectedResArr[position]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private View getTabView(int position) {
        View view = View.inflate(this, R.layout.main_tab_layout, null);
        TextView tv = (TextView) view.findViewById(R.id.id_tab_tv);
        ImageView img = (ImageView) view.findViewById(R.id.id_tab_img);
        tv.setText(mTabTextArr[position]);
        img.setImageResource(mImgNormalResArr[position]);
        return view;
    }
}
