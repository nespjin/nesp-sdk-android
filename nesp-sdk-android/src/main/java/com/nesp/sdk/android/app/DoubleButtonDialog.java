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
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.nesp.sdk.android.R;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/7/12 14:20
 * @Project Assistant
 **/
@Deprecated
public class DoubleButtonDialog extends android.app.Dialog implements View.OnClickListener {
    private static final String TAG = "DoubleButtonDialog";
    private Context context;
    private Boolean isBottom;

    public DoubleButtonDialog(@NonNull Context context, Boolean isBottom) {
        super(context, R.style.dialog_custom);
        this.context = context;
        this.isBottom = isBottom;
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

        setContentView(R.layout.dialog_double_btn);

        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        lp.width = dm.widthPixels * 95 / 100;
        window.setAttributes(lp);
        setCanceledOnTouchOutside(true);

        findViewById(R.id.double_btn_dg_btn_yes).setOnClickListener(this);
        findViewById(R.id.double_btn_dg_btn_no).setOnClickListener(this);
        findViewById(R.id.double_btn_dg_message).setOnClickListener(this);
    }

    public interface OnDoubleButtonDialogClickListener {
        void OnDoubleButtonDialogClick(DoubleButtonDialog doubleButtonDialog, View view);
    }

    private OnDoubleButtonDialogClickListener onDoubleButtonDialogClickListener;

    public void setOnDoubleButtonDialogClickListener(OnDoubleButtonDialogClickListener onDoubleButtonDialogClickListener) {
        this.onDoubleButtonDialogClickListener = onDoubleButtonDialogClickListener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.double_btn_dg_message) dismiss();
        onDoubleButtonDialogClickListener.OnDoubleButtonDialogClick(this, view);
    }

    public void show(Context context, String title, String message, String btnYes, String btnNo, @Nullable int titleTextColor) {
        show();
        TextView doubleTitle = this.findViewById(R.id.double_btn_dg_title);
        TextView doubleMessage = this.findViewById(R.id.double_btn_dg_message);
        Button doubleBtnYes = this.findViewById(R.id.double_btn_dg_btn_yes);
        Button doubleBtnNo = this.findViewById(R.id.double_btn_dg_btn_no);
        doubleTitle.setText(title);
        if (titleTextColor != 0)
            doubleTitle.setTextColor(context.getResources().getColor(titleTextColor));
        doubleMessage.setText(message);
        doubleBtnYes.setText(btnYes);
        doubleBtnNo.setText(btnNo);
    }
}
