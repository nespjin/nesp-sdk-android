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
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.nesp.sdk.android.R;

import androidx.annotation.NonNull;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/7/12 14:20
 * @Project Assistant
 **/
@Deprecated
public class WebViewDialog extends android.app.Dialog implements View.OnClickListener {
    private static final String TAG = "WebViewDialog";
    private Context context;
    private String title;
    private String url;
    private String positiveText;
    private String negativeText;
    private OnButtonClickListener onNegativeButtonClickListener;
    private OnButtonClickListener onPositiveButtonClickListener;
    private Boolean canCancelEnable;
    private WebView webView;

    public static class Builder {

        private Context context;
        private String url;
        private String title;
        private String positiveText;
        private String negativeText;
        private OnButtonClickListener onNegativeButtonClickListener;
        private OnButtonClickListener onPositiveButtonClickListener;
        private Boolean canCancelEnable = true;

        public Builder(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPositiveButton(String text, OnButtonClickListener onButtonClickListener) {
            this.positiveText = text;
            this.onPositiveButtonClickListener = onButtonClickListener;
            return this;
        }

        public Builder setNegativeButton(String text, OnButtonClickListener onButtonClickListener) {
            this.negativeText = text;
            this.onNegativeButtonClickListener = onButtonClickListener;
            return this;
        }

        public String getPositiveText() {
            return positiveText;
        }

        public String getNegativeText() {
            return negativeText;
        }

        public OnButtonClickListener getOnNegativeButtonClickListener() {
            return onNegativeButtonClickListener;
        }

        public OnButtonClickListener getOnPositiveButtonClickListener() {
            return onPositiveButtonClickListener;
        }

        public Context getContext() {
            return context;
        }

        public String getUrl() {
            return url;
        }

        public Boolean getCanCancelEnable() {
            return canCancelEnable;
        }

        public Builder setCanCancelEnable(Boolean canCancelEnable) {
            this.canCancelEnable = canCancelEnable;
            return this;
        }

        public WebViewDialog create() {
            return new WebViewDialog(this);
        }
    }

    private WebViewDialog(@NonNull Builder builder) {
        super(builder.getContext(), R.style.dialog_custom);
        this.context = builder.getContext();
        this.url = builder.getUrl();
        this.onPositiveButtonClickListener = builder.getOnPositiveButtonClickListener();
        this.onNegativeButtonClickListener = builder.getOnNegativeButtonClickListener();
        this.positiveText = builder.getPositiveText();
        this.negativeText = builder.getNegativeText();
        this.title = builder.getTitle();
        this.canCancelEnable = builder.getCanCancelEnable();

        webView = findViewById(R.id.nesp_sdk_webview_dialog_wv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.nesp_sdk_center_dialog_animation);

        setContentView(R.layout.webview_dialog);

        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        lp.width = dm.widthPixels * 95 / 100;
        window.setAttributes(lp);
        setCancelable(canCancelEnable);

        ((TextView) findViewById(R.id.nesp_sdk_webview_dialog_textTitle)).setText(title);
        Button buttonCancel = findViewById(R.id.nesp_sdk_webview_dialog_btn_cancel);
        buttonCancel.setText(negativeText);
        buttonCancel.setOnClickListener(this);
        Button buttonOk = findViewById(R.id.nesp_sdk_webview_dialog_btn_ok);
        buttonOk.setText(positiveText);
        buttonOk.setOnClickListener(this);



        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                show();
            }
        });


    }

    public interface OnButtonClickListener {
        void onClick(WebViewDialog webViewDialog, View view);
    }

    @Override
    public void onClick(View view) {
        dismiss();
        int id = view.getId();
        if (id == R.id.nesp_sdk_webview_dialog_btn_cancel) {
            if (onNegativeButtonClickListener != null)
                onNegativeButtonClickListener.onClick(this, view);
        } else if (id == R.id.nesp_sdk_webview_dialog_btn_ok) {
            if (onPositiveButtonClickListener != null)
                onPositiveButtonClickListener.onClick(this, view);
        }
    }

     public void display() {
         webView.loadUrl(url);
    }
}
