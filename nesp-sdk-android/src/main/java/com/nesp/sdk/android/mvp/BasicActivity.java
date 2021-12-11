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

package com.nesp.sdk.android.mvp;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-8-13 上午7:48
 * @project FishMovie
 **/
public abstract class BasicActivity<V extends IBasicView, P extends BasicPresenter<V>> extends AppCompatActivity {

    protected P presenter;

    protected Context context = BasicActivity.this;

    protected Boolean isActivityRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建Presenter
        presenter = createPresenter();
        //关联View
        presenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    protected abstract P createPresenter();

    protected void showLongToast(String msg) {
        if (msg == null) return;
        final Toast toast = Toast.makeText(context, null, Toast.LENGTH_LONG);
        toast.setText(msg);
        toast.show();
    }

    protected void showShortToast(String msg) {
        if (msg == null) return;
        final Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
}
