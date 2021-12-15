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
 * @Time: Created 2018/7/12 14:03
 * @Project Assistant
 **/
@Deprecated
public class SingleButtonDialog extends android.app.Dialog implements View.OnClickListener {

    private static final String TAG = "SingleButtonDialog";
    private Context context;
    private Boolean isBottom;
    private int[] listenerItems;

    public SingleButtonDialog(@NonNull Context context, Boolean isBottom) {
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

        setContentView(R.layout.dialog_single_btn);
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        lp.width = dm.widthPixels * 95 / 100;
        window.setAttributes(lp);
        setCanceledOnTouchOutside(true);

//        listenerItems = new int[]{R.id.single_btn_dg_btn_yes};
//        for (int i = 0; i < listenerItems.length; i++) {
//            int id = listenerItems[i];
//            findViewById(id).setOnClickListener(this);
//        }

        findViewById(R.id.single_btn_dg_btn_yes).setOnClickListener(this);
        findViewById(R.id.single_btn_dg_message).setOnClickListener(this);

    }

    public interface OnSingleButtonDialogClickListener {
        void OnSingleButtonDialogClick(SingleButtonDialog singleButtonDialog, View view);
    }

    private OnSingleButtonDialogClickListener onSingleButtonDialogClickListener;

    public void setOnSingleButtonDialogClickListener(OnSingleButtonDialogClickListener onSingleButtonDialogClickListener) {
        this.onSingleButtonDialogClickListener = onSingleButtonDialogClickListener;
    }

    @Override
    public void onClick(View view) {
        dismiss();
        onSingleButtonDialogClickListener.OnSingleButtonDialogClick(this, view);
    }

    public void show(Context context, String title, String message, String btnYes, @Nullable int titleTextColor) {
        show();
        TextView singleTitle = this.findViewById(R.id.single_btn_dg_title);
        TextView singleMessage = this.findViewById(R.id.single_btn_dg_message);
        Button singleBtnYes = this.findViewById(R.id.single_btn_dg_btn_yes);
        singleTitle.setText(title);
        if (titleTextColor != 0)
            singleTitle.setTextColor(context.getResources().getColor(titleTextColor));
        singleMessage.setText(message);
        singleBtnYes.setText(btnYes);
    }
}
