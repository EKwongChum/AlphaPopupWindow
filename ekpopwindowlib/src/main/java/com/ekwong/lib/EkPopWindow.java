package com.ekwong.lib;

import android.content.Context;
import android.view.View;

import com.ekwong.ekpopwindowlib.R;

import java.util.HashSet;
import java.util.Set;

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

    private boolean isShow;

    private Set<BackPressedListener> backPressedListeners = new HashSet<>();

    private Set<BackInterruption> backInterruptions = new HashSet<>();

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

    public boolean addBackPressedListener(BackPressedListener listener) {
        return backPressedListeners.add(listener);
    }

    public boolean removeBackPressedListener(BackPressedListener listener) {
        return backPressedListeners.remove(listener);
    }

    public boolean addBackInterruption(BackInterruption interruption) {
        return backInterruptions.add(interruption);
    }

    public boolean removeBackInterruption(BackInterruption interruption) {
        return backInterruptions.remove(interruption);
    }

    /**
     * 设置弹窗的配置
     *
     * @param builder 构造者对象
     */
    protected void setBuilderConfig(CustomPopWindow.Builder builder) {
        builder.setDuration(mContext.getResources().getInteger(R.integer.default_duration))
                .setOutSideColor(mContext.getResources().getColor(R.color.transparent_fifty_percent))
                .setOutSideTouchable(true)
                .setAnimationType(PopAnimation.FADE_OUT_CENTER);
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
    public void setOnDismissListener(PopWindowDismissListener listener) {
        mCustomPopWindow.setOnDismissListener(listener);
    }

    /**
     * 展示
     */
    public void show() {
        mCustomPopWindow.show(mDecorView);
        isShow = true;
    }

    /**
     * 关闭
     */
    public void dismiss() {
        mCustomPopWindow.dismiss();
        isShow = false;
    }

    public boolean isShow() {
        return isShow;
    }

    /**
     * 当返回按钮被点击的时候调用
     */
    public void onBackPressed() {
        if (!backInterruptions.isEmpty()) {
            for (BackInterruption listener : backInterruptions) {
                listener.interrupt();
            }
        } else {
            if (!backPressedListeners.isEmpty()) {
                for (BackPressedListener listener : backPressedListeners) {
                    listener.onBackPressed();
                }
            }
            dismiss();
        }
    }

}
