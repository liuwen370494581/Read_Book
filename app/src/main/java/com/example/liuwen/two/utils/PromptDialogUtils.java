package com.example.liuwen.two.utils;

import android.app.Activity;
import android.content.Context;

import com.example.liuwen.two.Base.AppInfo;
import com.example.liuwen.two.View.promptlibrary.PromptDialog;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/13 09:41
 * desc   : 对话框的单列类
 */
public class PromptDialogUtils {

    private static PromptDialogUtils instance;
    private  PromptDialog promptDialog;


    public PromptDialogUtils() {
    }

    public static PromptDialogUtils getInstance() {
        if (null == instance) {
            synchronized (PromptDialogUtils.class) {
                if (null == instance) {
                    instance = new PromptDialogUtils();
                }
            }
        }
        return instance;
    }

    public void init(Activity activity) {
        promptDialog = new PromptDialog(activity);
        promptDialog.getDefaultBuilder().touchAble(true).round(3).withAnim(true);
    }

    public  void showPromptDialog(String message) {
        promptDialog.showLoading(message);
    }

    public  void hidePromptDialog() {
        promptDialog.dismiss();
    }
}
