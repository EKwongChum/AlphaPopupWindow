package com.ekwong;

import android.content.Context;
import android.view.View;

import com.ekwong.alphapopupwindowlib.AlphaPopWindow;
import com.ekwong.alphapopupwindowlib.CustomPopWindow;
import com.ekwong.alphapopupwindowlib.PopAnimation;

public class CenterPopWindow extends AlphaPopWindow {


    public CenterPopWindow(Context context, View decorView) {
        super(context, R.layout.popwindow_center, decorView);
    }

    @Override
    protected void setBuilderConfig(CustomPopWindow.Builder builder) {
        builder.setDuration(mContext.getResources().getInteger(R.integer.anim_duration_short))
                .setOutSideColor(mContext.getResources().getColor(R.color.transparent_fifty_percent))
                .setOutSideTouchable(true)
                .setAnimationType(PopAnimation.FADE_OUT_CENTER);
    }

    @Override
    protected void findView(CustomPopWindow customPopWindow) {

    }
}
