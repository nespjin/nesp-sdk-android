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
import androidx.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nesp.sdk.android.R;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/7/12 13:37
 * @Project Assistant
 **/
@Deprecated
public class ProgressDialog extends android.app.Dialog implements View.OnClickListener {
    private static final String TAG = "ProgressDialog";
    private Boolean isBottom;
    private Context context;
    private TextView progressMessage, progressProgress;
    private ProgressBar progressBar;
    private Button btnNo;

    public ProgressDialog(@NonNull Context context, Boolean isBottom) {
        super(context, R.style.dialog_custom);
        this.isBottom = isBottom;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        if (isBottom && window != null) {
            window.getDecorView().setPadding(0,0,0,20);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.nesp_sdk_bottom_dialog_animation);
        } else if (!isBottom && window != null) {
            window.setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.nesp_sdk_center_dialog_animation);
        }

        setContentView(R.layout.nesp_sdk_dialog_progress);

        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        lp.width = dm.widthPixels * 95 / 100;
        window.setAttributes(lp);
        setCanceledOnTouchOutside(true);

        progressMessage = ProgressDialog.this.findViewById(R.id.progress_dg_tv_message);
        progressProgress = ProgressDialog.this.findViewById(R.id.progress_dg_tv_progress);
        progressBar = ProgressDialog.this.findViewById(R.id.progress_dg_pb_progress);
        progressBar.setMax(100);
        btnNo = ProgressDialog.this.findViewById(R.id.progress_dg_btn_no);
        btnNo.setOnClickListener(this);
    }

    public interface OnProgressDialogClickListener {
        void OnProgressDialogClick(ProgressDialog progressDialog, View view);
    }

    private OnProgressDialogClickListener onProgressDialogClickListener;

    public void setOnProgressDialogClickListener(OnProgressDialogClickListener onProgressDialogClickListener) {
        this.onProgressDialogClickListener = onProgressDialogClickListener;
    }

    @Override
    public void onClick(View v) {
//        dismiss();
        onProgressDialogClickListener.OnProgressDialogClick(this, v);
    }

    public void show(String message, int progress, String btnNoText) {
        show();
        btnNo.setText(btnNoText);
        progressBar.setProgress(progress);
        progressMessage.setText(message);
        progressProgress.setText(progress + " %");
    }
}
