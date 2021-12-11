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

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LoadDataActivity extends AppCompatActivity {


    @SuppressLint("HandlerLeak")
    Handler handlerLoadData = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (onLoadListener != null) onLoadListener.onLoadFailed();
                    loadingDialog.dismiss();
                    break;
                case 1:
                    if (onLoadListener != null) onLoadListener.onLoaded();
                    loadingDialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private OnLoadListener onLoadListener;
    private AlertLoadingDialog loadingDialog;

    protected void loadData(OnLoadListener onLoadListener, Object... objects) {
        this.onLoadListener = onLoadListener;
        if (onLoadListener != null) onLoadListener.onPreLoad();
        loadingDialog = new AlertLoadingDialog.Builder(this)
                .setLoadingMessage("正在加载...").create();
        loadingDialog.show();
        new Thread(() -> {
            Looper.prepare();//增加部分
            Message message = new Message();
            try {
                if (onLoadListener != null) onLoadListener.onLoading();

            } catch (Exception e) {
                e.printStackTrace();
                message.what = 0;
                handlerLoadData.sendMessage(message);
            }
            message.what = 1;
            handlerLoadData.sendMessage(message);
        }).start();
    }

    public interface OnLoadListener {
        void onPreLoad(Object... objects);

        void onLoading(Object... objects);

        void onLoadFailed(Object... objects);

        void onLoaded(Object... objects);
    }
}
