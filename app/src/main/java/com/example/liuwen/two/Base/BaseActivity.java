package com.example.liuwen.two.Base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liuwen.two.EventBus.BindEventBus;
import com.example.liuwen.two.EventBus.EventBusUtil;
import com.example.liuwen.two.R;
import com.example.liuwen.two.View.TipDialog;
import com.example.liuwen.two.View.promptlibrary.PromptDialog;
import com.example.liuwen.two.listener.OnCommonBarListener;
import com.example.liuwen.two.listener.onRightListener;
import com.example.liuwen.two.utils.ActivityKiller;


/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/08/09 17:25
 * desc   :
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ImageView mImgRight;
    private LinearLayout lyCommonBar, lyRightBar;
    private TextView mTvCenter, mTvRight;//toobar中间文字
    private AppInfo mApp;
    private Context mActivityContext, mAppContext;//尽量地采用 Application Context 避免内存泄漏
    private PromptDialog promptDialog;
    private TipDialog mTipDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        setContentView(setLayoutRes());
        initView();//初始化视图
        initData();
        setListener();
        promptDialog = new PromptDialog(this);
        //设置自定义属性
        promptDialog.getDefaultBuilder().touchAble(true).round(3).withAnim(true);
        mActivityContext = this;
        mAppContext = getApplicationContext();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtil.register(this);
        }
        ActivityKiller.getInstance().addActivity(this);
    }

    public AppInfo getApp() {
        if (null == mApp) {
            mApp = (AppInfo) getApplication();
        }
        return mApp;
    }

    protected Context getActivityContext() {
        if (null == mActivityContext) {
            mActivityContext = this;
        }
        return mActivityContext;
    }

    protected Context getAppContext() {
        if (null == mAppContext) {
            mAppContext = getApplicationContext();
        }
        return mAppContext;
    }

    protected abstract int setLayoutRes();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();


    public <T extends View> T getView(int viewId) {
        return (T) this.findViewById(viewId);
    }

    protected void openActivity(Class toActivity) {
        Intent intent = new Intent(this, toActivity);
        startActivity(intent);
    }

    protected void openActivity(Class toActivity, Bundle bundle) {
        Intent intent = new Intent(this, toActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void openActivity(Class toActivity, int reqCode, Bundle bundle) {
        Intent intent = new Intent(this, toActivity);
        intent.putExtras(bundle);
        startActivityForResult(intent, reqCode);
    }

    protected void closeActivity() {
        finish();
    }

    protected void openActivity(Class toActivity, int reqCode) {
        Intent intent = new Intent(this, toActivity);
        startActivityForResult(intent, reqCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityKiller.getInstance().removeActivity(this);
        // 若使用BindEventBus注解，则解绑定EventBus
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtil.unregister(this);
        }
    }

    public void showLoadingDialog(String loadingText) {
        promptDialog.showLoading(loadingText);
    }

    public void hideLoadingDialog() {
        promptDialog.dismiss();
    }

    public void showTipDialog(TipDialog.ITipDialogListener mListener) {
        try {
            mTipDialog = new TipDialog(getActivityContext(), getString(R.string.loading_fail));
            mTipDialog.show();
            mTipDialog.setCancelable(false);
            mTipDialog.setRightButtonVisible(true);
            mTipDialog.setCenterPosition();
            mTipDialog.setLeftText(getString(R.string.cancel));
            mTipDialog.setRightText(getString(R.string.loading_again));
            mTipDialog.setListener(mListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void showLeftView() {
        lyCommonBar = getView(R.id.back_view);
        if (lyCommonBar == null) {
            return;
        }
        lyCommonBar.setVisibility(View.VISIBLE);
        lyCommonBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void setRightListener(final OnCommonBarListener listener) {
        lyRightBar = getView(R.id.right_view);
        mImgRight = getView(R.id.toolbar_righ_iv);
        if (lyRightBar == null || mImgRight == null) {
            return;
        }
        lyRightBar.setVisibility(View.VISIBLE);
        mImgRight.setVisibility(View.VISIBLE);
        lyRightBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onRightChoiceListener();
                }
            }
        });
    }

    protected void setRightText(String rightText, final onRightListener listener) {
        lyRightBar = getView(R.id.right_view);
        mTvRight = getView(R.id.toolbar_righ_tv);
        if (lyRightBar == null || mTvRight == null) {
            return;
        }
        lyRightBar.setVisibility(View.VISIBLE);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(rightText);
        lyRightBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.rightTextListener();
            }
        });
    }


    protected void setCenterText(String str) {
        mTvCenter = getView(R.id.title);
        if (mTvCenter == null) {
            return;
        }
        mTvCenter.setText(str);
        mTvCenter.setVisibility(View.VISIBLE);
    }

}
