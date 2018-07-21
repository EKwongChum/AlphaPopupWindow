package com.ekwong.alphapopupwindowlib;

import android.content.Context;
import android.view.View;

/**
 * @author erkang
 * <p>
 * 自定义弹窗的继承式版本
 */
public abstract class EkPopWindow {

    protected Context mContext;

    private int mResLayout;

    private View mDecorView;

    private CustomPopWindow mCustomPopWindow;

    private CustomPopWindow.Builder mBuilder;

    public EkPopWindow(Context context, int resLayout, View decorView) {
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

    /**
     * 设置弹窗的配置
     *
     * @param builder 构造者对象
     */
    protected void setBuilderConfig(CustomPopWindow.Builder builder) {
    }

    /**
     * 在此方法对你的布局的view进行设置吧
     *
     * @param customPopWindow customPopWindow对象
     */
    protected void findView(CustomPopWindow customPopWindow) {
    }

    protected void addListener() {
    }

    /**
     * 设置关闭监听
     *
     * @param listener 监听器对象
     */
    public void setOnDismissListener(CustomPopWindowDismissListener listener) {
        mCustomPopWindow.setOnDismissListener(listener);
    }

    /**
     * 展示
     */
    public void show() {
        mCustomPopWindow.show(mDecorView);
    }

    /**
     * 关闭
     */
    public void dismiss() {
        mCustomPopWindow.dismiss();
    }
}
