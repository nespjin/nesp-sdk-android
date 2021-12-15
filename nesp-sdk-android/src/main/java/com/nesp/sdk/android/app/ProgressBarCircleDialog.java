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

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;

import com.nesp.sdk.android.R;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/7/12 13:37
 * @Project Assistant
 **/
@Deprecated
public class ProgressBarCircleDialog extends android.app.Dialog {

    private static final String TAG = "ProgressBarCircleDialog";

    private Boolean isCenter;
    private Context context;
    private int layResId;

    private ProgressBarCircleDialog(Builder builder) {
        super(builder.getContext(), R.style.nesp_sdk_loading_dialog);
        this.context = builder.getContext();
        this.isCenter = builder.getCenter();
        this.layResId = builder.getLayResId();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        if (!isCenter && window != null) {
            window.getDecorView().setPadding(0, 0, 0, 20);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.nesp_sdk_bottom_dialog_animation);
        } else if (isCenter && window != null) {
            window.setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.nesp_sdk_center_dialog_animation);
        }

        setContentView(layResId);

        setCanceledOnTouchOutside(false);
     }

    public static class Builder {

        private final Context context;

        private final Boolean isCenter = true;
        private int layResId = R.layout.progressbar_circle_loading;

        public Builder(Context context) {
            this.context = context;
        }

        private int getLayResId() {
            return layResId;
        }

        public Builder setLayResId(int layResId) {
            this.layResId = layResId;
            return this;
        }

        public Boolean getCenter() {
            return isCenter;
        }

//        public Builder setCenter(Boolean center) {
//            isCenter = center;
//            return this;
//        }

        public Context getContext() {
            return context;
        }

        public ProgressBarCircleDialog create() {
            return new ProgressBarCircleDialog(this);
        }
    }

}
