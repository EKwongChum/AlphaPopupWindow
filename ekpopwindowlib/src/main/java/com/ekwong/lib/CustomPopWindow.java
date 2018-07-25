package com.ekwong.lib;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.ekwong.ekpopwindowlib.R;

/**
 * @author erkang
 * <p>
 * 自定义的PopWindow类
 */
public class CustomPopWindow {

    private PopupWindow mBgPopupWindow;

    private FrameLayout mBgView;

    private View mContentView;

    private int mAnimDuration;

    private CustomPopWindowDismissListener mDismissListener;

    private OutSideClickListener mOutSideClickListener;

    private PopAnimation mPopAnimation;

    private boolean mIsShowing;

    private Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mBgPopupWindow.dismiss();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    /**
     * 构造方法
     *
     * @param context   上下文
     * @param layoutRes 布局资源
     * @param builder   构造者对象
     */
    private CustomPopWindow(Context context, int layoutRes, Builder builder) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mBgView = new FrameLayout(context);
        mBgView.setBackgroundColor(context.getResources().getColor(R.color.transparent_fifty_percent));
        mBgPopupWindow = new PopupWindow(mBgView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView = layoutInflater.inflate(layoutRes, mBgView, false);
        mBgView.setBackgroundColor(builder.mColorOutside);
        mBgView.setClickable(builder.mIsOutsideTouchable);
        mPopAnimation = builder.mAnimation;
        mAnimDuration = builder.mDuration;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mContentView.getLayoutParams();
        if (mPopAnimation.equals(PopAnimation.FADE_OUT_CENTER)) {
            layoutParams.gravity = Gravity.CENTER;
        } else if (mPopAnimation.equals(PopAnimation.POP_FROM_BOTTOM)) {
            layoutParams.gravity = Gravity.BOTTOM;
        }
        mContentView.setLayoutParams(layoutParams);
        mContentView.setVisibility(View.GONE);
        mBgView.addView(mContentView);
        mBgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOutSideClickListener != null) {
                    mOutSideClickListener.onOutSideClick();
                }
                dismiss();
            }
        });
        mBgPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void setOutSideClickListener(OutSideClickListener outSideClickListener) {
        mOutSideClickListener = outSideClickListener;
    }

    public void setOnDismissListener(CustomPopWindowDismissListener dismissListener) {
        mDismissListener = dismissListener;
    }

    public void show(View anchor) {
        if (!mIsShowing) {
            mBgPopupWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
            mContentView.setVisibility(View.VISIBLE);
            if (mPopAnimation.equals(PopAnimation.FADE_OUT_CENTER)) {
                startFadeOutCenterEnter(mContentView);
            } else if (mPopAnimation.equals(PopAnimation.POP_FROM_BOTTOM)) {
                startPopFromBottom(mContentView);
            }
            mIsShowing = true;
        }
    }

    public void dismiss() {
        if (mIsShowing) {
            mIsShowing = false;
            if (mDismissListener != null) {
                mDismissListener.onDismiss();
            }
            if (mPopAnimation.equals(PopAnimation.FADE_OUT_CENTER)) {
                startFadeOutCenterExit(mContentView, mAnimatorListener);
            } else if (mPopAnimation.equals(PopAnimation.POP_FROM_BOTTOM)) {
                closePopToBottom(mContentView, mAnimatorListener);
            }
        }
    }

    public <T extends View> T findViewById(int id) {
        return mContentView.findViewById(id);
    }

    private void startFadeOutCenterEnter(View view) {
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhAlpha, pvhX, pvhY).setDuration(mAnimDuration).start();
    }

    private void startFadeOutCenterExit(View view, Animator.AnimatorListener animatorListener) {
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, pvhAlpha, pvhX, pvhY).setDuration(mAnimDuration);
        objectAnimator.addListener(animatorListener);
        objectAnimator.start();
    }

    private void startPopFromBottom(View view) {
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", 200f, 0);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhAlpha, pvhY).setDuration(mAnimDuration).start();
    }

    private void closePopToBottom(View view, Animator.AnimatorListener animatorListener) {
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", 0, 200f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, pvhAlpha, pvhY).setDuration(mAnimDuration);
        objectAnimator.addListener(animatorListener);
        objectAnimator.start();
    }

    public static class Builder {

        private Context mContext;

        private int mContentLayoutRes;

        private int mColorOutside;

        private boolean mIsOutsideTouchable;

        private int mDuration = 500;

        private PopAnimation mAnimation = PopAnimation.FADE_OUT_CENTER;

        public Builder(Context context, int layoutRes) {
            mContext = context;
            mContentLayoutRes = layoutRes;
        }

        public Builder setOutSideColor(int color) {
            mColorOutside = color;
            return this;
        }

        public Builder setDuration(int duration) {
            mDuration = duration;
            return this;
        }

        public Builder setOutSideTouchable(boolean touchable) {
            mIsOutsideTouchable = touchable;
            return this;
        }

        public Builder setAnimationType(PopAnimation popAnimation) {
            mAnimation = popAnimation;
            return this;
        }

        public CustomPopWindow build() {
            return new CustomPopWindow(mContext, mContentLayoutRes, this);
        }
    }
}
