/*
 *
 *   Copyright (c) 2020  NESP Technology Corporation. All rights reserved.
 *
 *   This program is not free software; you can't redistribute it and/or modify it
 *   without the permit of team manager.
 *
 *   Unless required by applicable law or agreed to in writing.
 *
 *   If you have any questions or if you find a bug,
 *   please contact the author by email or ask for Issues.
 *
 *   Author:JinZhaolu <1756404649@qq.com>
 */

package com.nesp.sdk.android.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nesp.sdk.android.R;

/**
 * Team: NESP Technology
 *
 * @author 靳兆鲁
 * Email: 1756404649@qq.com
 * Time: Created 2018/7/12 13:37
 **/
public final class AlertLoadingDialog extends android.app.Dialog {

    private final Boolean mIsCenter;
    private final Context mContext;
    private final int mLayResId;
    private final int mTextViewLoadMessageId;
    private final String mLoadingMessage;

    private AlertLoadingDialog(Builder builder) {
        super(builder.getContext(), R.style.nesp_sdk_loading_dialog);
        this.mContext = builder.getContext();
        this.mIsCenter = builder.getCenter();
        this.mLayResId = builder.getLayoutResId();
        this.mTextViewLoadMessageId = builder.getTextViewLoadMessageId();
        this.mLoadingMessage = builder.getLoadingMessage();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        if (!mIsCenter && window != null) {
            window.getDecorView().setPadding(0, 0, 0, 20);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.nesp_sdk_bottom_dialog_animation);
        } else if (mIsCenter && window != null) {
            window.setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.nesp_sdk_center_dialog_animation);
        }

        setContentView(mLayResId);
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
//        Display display = windowManager.getDefaultDisplay();

        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            DisplayMetrics dm = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(dm);
            layoutParams.width = dm.widthPixels * 95 / 100;
            window.setAttributes(layoutParams);
        }

        setCanceledOnTouchOutside(false);

        ((TextView) this.findViewById(mTextViewLoadMessageId)).setText(mLoadingMessage);
    }

    public static class Builder {

        private final Context context;

        private Boolean mIsCenter = true;
        private int mLayoutResId = R.layout.nesp_sdk_alertdialog_loading;
        private int mTextViewLoadMessageId = R.id.alert_dialog_loading_dg_message;
        private String mLoadingMessage;

        public Builder(Context context) {
            this.context = context;
        }

        private String getLoadingMessage() {
            return mLoadingMessage;
        }

        public Builder setLoadingMessage(String loadingMessage) {
            this.mLoadingMessage = loadingMessage;
            return this;
        }

        private int getTextViewLoadMessageId() {
            return mTextViewLoadMessageId;
        }

        public Builder setTextViewLoadMessageId(int textViewLoadMessageId) {
            this.mTextViewLoadMessageId = textViewLoadMessageId;
            return this;
        }

        private int getLayoutResId() {
            return mLayoutResId;
        }

        public Builder setLayoutResId(int layoutResId) {
            this.mLayoutResId = layoutResId;
            return this;
        }

        public Boolean getCenter() {
            return mIsCenter;
        }

        public Builder setCenter(Boolean center) {
            mIsCenter = center;
            return this;
        }

        public Context getContext() {
            return context;
        }

        public AlertLoadingDialog create() {
            return new AlertLoadingDialog(this);
        }
    }

}
