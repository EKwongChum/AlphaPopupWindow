package com.ekwong;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ekwong.lib.EkPopWindow;
import com.ekwong.lib.CustomPopWindow;
import com.ekwong.lib.PopAnimation;

/**
 * @author erkang
 * <p>
 * 选择图片弹窗
 */
public class SelectImgPopWindow extends EkPopWindow {

    public interface WindowClickListener {
        void onGalleryClick();

        void onCameraClick();

        void onCancelClick();
    }

    private WindowClickListener mWindowClickListener;

    private TextView mTvGallery, mTvCamera, mTvCancel;

    public SelectImgPopWindow(Context context, View decorView) {
        super(context, R.layout.popwindow_select_photo, decorView);
    }

    public void setWindowClickListener(WindowClickListener windowClickListener) {
        mWindowClickListener = windowClickListener;
    }

    @Override
    protected void setBuilderConfig(CustomPopWindow.Builder builder) {
        // 设置动画周期
        builder.setDuration(mContext.getResources().getInteger(R.integer.anim_duration_short))
                //设置外层颜色
                .setOutSideColor(mContext.getResources().getColor(R.color.transparent_fifty_percent))
                //外层是否可点击
                .setOutSideTouchable(true)
                //设置动画类型
                .setAnimationType(PopAnimation.POP_FROM_BOTTOM);
    }

    @Override
    protected void findView(CustomPopWindow customPopWindow) {
        mTvGallery = customPopWindow.findViewById(R.id.tvGallery);
        mTvCamera = customPopWindow.findViewById(R.id.tvCamera);
        mTvCancel = customPopWindow.findViewById(R.id.tvCancel);
        mTvGallery.setText("Chose Photo");
        mTvCamera.setText("Take Photo");
    }

    @Override
    protected void addListener() {
        mTvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowClickListener.onGalleryClick();
                dismiss();
            }
        });

        mTvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowClickListener.onCameraClick();
                dismiss();
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowClickListener.onCancelClick();
                dismiss();
            }
        });
    }

}
