package com.ekwong.alphapopupwindowlib;

import android.content.Context;
import android.view.View;

/**
 * @author erkang
 * <p>
 * 自定义弹窗的继承式版本
 */
public abstract class AlphaPopWindow {

    protected Context mContext;

    private int mResLayout;

    private View mDecorView;

    private CustomPopWindow mCustomPopWindow;

    private CustomPopWindow.Builder mBuilder;

    public AlphaPopWindow(Context context, int resLayout, View decorView) {
        mContext = context;
        mResLayout = resLayout;
        mDecorView = decorView;
        initBuilder();
        mCustomPopWindow = mBuilder.build();
        findView(mCustomPopWindow);
        addListener();
    }

    private void initBuilder() {
        mBuilder = new CustomPopWindow.Builder(mContext, mResLayout);
        setBuilderConfig(mBuilder);
    }

    protected abstract void setBuilderConfig(CustomPopWindow.Builder builder);

    protected abstract void findView(CustomPopWindow customPopWindow);

    protected void addListener() {
    }

    public void setOnDismissListener(CustomPopWindowDismissListener listener) {
        mCustomPopWindow.setOnDismissListener(listener);
    }

    public void show() {
        mCustomPopWindow.show(mDecorView);
    }

    public void dismiss() {
        mCustomPopWindow.dismiss();
    }
}
