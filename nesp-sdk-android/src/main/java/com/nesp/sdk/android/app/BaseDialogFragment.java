package com.nesp.sdk.android.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.nesp.sdk.android.R;
import com.nesp.sdk.android.app.utils.DialogUtil;
import com.nesp.sdk.android.utils.DisplayUtil;


/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/9/1 9:56
 **/
public abstract class BaseDialogFragment extends BasicDialogFragment implements IComponentJava {
    private static final String TAG = "BaseDialogFragment";

    private boolean mCancelable = false;
    private boolean mIsEnabledBgColor = false;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private DialogInterface.OnShowListener mOnShowListener;
    @StyleRes
    private int mDialogStyle = R.style.NespAppDialog;

    @Nullable
    @Override
    public Activity getAct() {
        return getActivity();
    }

    @NonNull
    @Override
    public Context getCtx() {
        return requireContext();
    }

    public BaseDialogFragment() {
    }

    public BaseDialogFragment(final boolean cancelable) {
        mCancelable = cancelable;
    }

    public BaseDialogFragment(final boolean cancelable, final boolean isEnabledBgColor) {
        mCancelable = cancelable;
        mIsEnabledBgColor = isEnabledBgColor;
    }

    public boolean isEnabledBgColor() {
        return mIsEnabledBgColor;
    }

    public BaseDialogFragment setEnabledBgColor(final boolean enabledBgColor) {
        mIsEnabledBgColor = enabledBgColor;
        return this;
    }

    public BaseDialogFragment setOnDismissListener(final DialogInterface.OnDismissListener onDismissListener) {
        if (getDialog() != null) {
            getDialog().setOnDismissListener(onDismissListener);
        }
        mOnDismissListener = onDismissListener;
        return this;
    }

    public BaseDialogFragment setOnShowListener(final DialogInterface.OnShowListener onShowListener) {
        if (getDialog() != null) {
            getDialog().setOnShowListener(onShowListener);
        }
        mOnShowListener = onShowListener;
        return this;
    }

    public void setDialogStyle(@StyleRes final int dialogStyle) {
        mDialogStyle = dialogStyle;
    }

    public int getDialogStyle() {
        return mDialogStyle;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(requireContext(), getDialogStyle()) {

            @Override
            public void onWindowFocusChanged(final boolean hasFocus) {
                super.onWindowFocusChanged(hasFocus);
                BaseDialogFragment.this.onWindowFocusChanged(hasFocus);
            }

            @Override
            public boolean dispatchTouchEvent(@NonNull final MotionEvent ev) {
                if (!BaseDialogFragment.this.dispatchTouchEvent(ev)) {
                    return super.dispatchTouchEvent(ev);
                }
                return true;
            }

            @Override
            public boolean onKeyDown(final int keyCode, @NonNull final KeyEvent event) {
                if (!BaseDialogFragment.this.onKeyDown(keyCode, event)) {
                    return super.onKeyDown(keyCode, event);
                }
                return true;
            }
        };
        setCancelable(mCancelable);
        DialogUtil.hideBottomNav(dialog);

        // 设置窗口大小
        WindowManager windowManager = dialog.getWindow().getWindowManager();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();

        // 设置窗口背景透明度
        if (mIsEnabledBgColor) {
            attributes.alpha = 0.3f;
        } else {
            attributes.alpha = 1.0f;
        }
//        attributes.width = screenWidth / 2 + DensityUtil.dip2px(getContext(), 60);
        attributes.width = dialogWidth(screenWidth);
        attributes.height = dialogHeight(screenHeight);
        dialog.getWindow().setAttributes(attributes);
        dialog.setCancelable(mCancelable);
        dialog.setCanceledOnTouchOutside(false);
        if (mOnDismissListener != null) {
            dialog.setOnDismissListener(mOnDismissListener);
        }
        if (mOnShowListener != null) {
            dialog.setOnShowListener(mOnShowListener);
        }
        return dialog;
    }

    @Override
    public void onDismiss(@NonNull final DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mOnDismissListener != null && getDialog() != null) {
            getDialog().setOnDismissListener(mOnDismissListener);
        }
    }

    protected int dialogWidth(int screenWidth) {
        return screenWidth * 3 / 4;
    }


    protected int dialogHeight(int screenHeight) {
        return screenHeight - DisplayUtil.dp2px(getContext(), 30);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    protected void initializeView() {

    }

    protected boolean dispatchTouchEvent(@NonNull final MotionEvent ev) {
        return false;
    }

    public boolean onKeyDown(final int keyCode, @NonNull final KeyEvent event) {
        return false;
    }

    protected void onWindowFocusChanged(final boolean hasFocus) {

    }
}
