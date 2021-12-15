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

package com.nesp.sdk.android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nesp.sdk.android.R;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-1-13 上午12:52
 * @project FishMovie
 **/
@Deprecated
public class NespTipbar {

    private Context context;

    private View rootView;
    private PopupWindow popupWindow;
    private Button buttonAction;
    private TextView textViewActionMessage;
    private RelativeLayout rootLayout;

    private View parentView;
    private String message;
    private Action action;
    private long textMessageSize = 0;
    @ColorInt
    private int textMessageColor = 0;
    @ColorInt
    private int backgroundColor = 0;

    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int width = ViewGroup.LayoutParams.MATCH_PARENT;
    private Boolean isBottom = false;

    private Boolean isShown = false;
    private long durationMillisecond;

    private OnShownListener onShownListener;
    private OnDismissListener onDismissListener;

    private Thread threadDismiss = new Thread(new Runnable() {
        @Override
        public void run() {
            Looper.prepare();

            if (durationMillisecond <= 0) return;

            try {
                Thread.sleep(durationMillisecond);

                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Looper.loop();
        }
    });

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    dismiss();
                    break;
            }
        }
    };


    @NonNull
    public static NespTipbar make(@NonNull Context context, View parentView, String message, long durationMillisecond) {
        return new NespTipbar(context, parentView, message, durationMillisecond);
    }

    @NonNull
    public static NespTipbar make(@NonNull Context context, View parentView, String message, long durationMillisecond, Action action) {
        return new NespTipbar(context, parentView, message, durationMillisecond, action);
    }

    @NonNull
    public static NespTipbar make(@NonNull Context context, View parentView, String message, long durationMillisecond, boolean isBottom) {
        return new NespTipbar(context, parentView, message, durationMillisecond, isBottom);
    }

    @NonNull
    public static NespTipbar make(@NonNull Context context, View parentView, String message, long durationMillisecond, boolean isBottom, Action action) {
        return new NespTipbar(context, parentView, message, durationMillisecond, isBottom, action);
    }

    private NespTipbar(Context context, View parentView, String message, long durationMillisecond) {
        this.context = context;
        this.parentView = parentView;
        this.message = message;
        this.durationMillisecond = durationMillisecond;
    }

    private NespTipbar(Context context, View parentView, String message, long durationMillisecond, Action action) {
        this.context = context;
        this.parentView = parentView;
        this.message = message;
        this.durationMillisecond = durationMillisecond;
        this.action = action;
    }

    private NespTipbar(Context context, View parentView, String message, long durationMillisecond, Boolean isBottom) {
        this.context = context;
        this.parentView = parentView;
        this.message = message;
        this.durationMillisecond = durationMillisecond;
        this.isBottom = isBottom;
    }

    private NespTipbar(Context context, View parentView, String message, long durationMillisecond, boolean isBottom, Action action) {
        this.context = context;
        this.parentView = parentView;
        this.message = message;
        this.durationMillisecond = durationMillisecond;
        this.isBottom = isBottom;
        this.action = action;
    }

    public void show() {
        if (isShown) return;
        isShown = true;
        if (onShownListener != null) onShownListener.onShown();
        threadDismiss.start();
        rootView = LayoutInflater.from(context).inflate(R.layout.nesptipbar_content, null);
        buttonAction = rootView.findViewById(R.id.nesp_tip_content_btn_action);
        textViewActionMessage = rootView.findViewById(R.id.nesp_tip_content_tv_message);
        rootLayout = rootView.findViewById(R.id.nesp_tip_content_root_layout);

        if (backgroundColor != 0) rootLayout.setBackgroundColor(backgroundColor);

        if (textMessageColor != 0) textViewActionMessage.setTextColor(textMessageColor);

        if (textMessageSize != 0) textViewActionMessage.setTextSize(textMessageSize);

        if (!message.isEmpty()) textViewActionMessage.setText(message);

        if (action != null) {
            buttonAction.setVisibility(View.VISIBLE);

            if (!action.getName().isEmpty()) buttonAction.setText(action.getName());

            if (action.getBtnBackgroundColor() != 0)
                buttonAction.setBackgroundColor(action.getBtnBackgroundColor());

            if (action.getBtnTextColor() != 0) buttonAction.setTextColor(action.getBtnTextColor());

            buttonAction.setOnClickListener(v -> {
                if (action.getOnClickListener() != null) action.getOnClickListener().onClick(v);
                dismiss();
            });

            if (action.getBtnTextSize() != 0) buttonAction.setTextSize(action.getBtnTextSize());
        } else {
            buttonAction.setVisibility(View.GONE);
        }

        popupWindow = new PopupWindow(rootView, width, height);
        popupWindow.setOnDismissListener(this::dismiss);
        if (isBottom) {
            popupWindow.setAnimationStyle(R.style.NespTipbarAnimBottom);
            popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        } else {
            popupWindow.setAnimationStyle(R.style.NespTipbarAnimTop);
            popupWindow.showAtLocation(parentView, Gravity.TOP, 0, 0);
        }
    }

    public Boolean isShown() {
        return isShown;
    }

    public void dismiss() {
        if (!isShown || popupWindow == null) return;
        isShown = false;
        if (onDismissListener != null) onDismissListener.onDismiss();
        popupWindow.dismiss();
    }

    public static class Action {
        String name;
        View.OnClickListener onClickListener;
        @ColorInt
        int btnBackgroundColor = 0;
        @ColorInt
        int btnTextColor = 0;

        long btnTextSize = 0;

        public Action(String name, View.OnClickListener onClickListener) {
            this.name = name;
            this.onClickListener = onClickListener;
        }

        public Action(String name, View.OnClickListener onClickListener, int btnBackgroundColor, int btnTextColor) {
            this.name = name;
            this.onClickListener = onClickListener;
            this.btnBackgroundColor = btnBackgroundColor;
            this.btnTextColor = btnTextColor;
        }

        public Action(String name, View.OnClickListener onClickListener, int btnBackgroundColor, int btnTextColor, long btnTextSize) {
            this.name = name;
            this.onClickListener = onClickListener;
            this.btnBackgroundColor = btnBackgroundColor;
            this.btnTextColor = btnTextColor;
            this.btnTextSize = btnTextSize;
        }

        public String getName() {
            return name;
        }

        public View.OnClickListener getOnClickListener() {
            return onClickListener;
        }

        public int getBtnBackgroundColor() {
            return btnBackgroundColor;
        }

        public int getBtnTextColor() {
            return btnTextColor;
        }

        public long getBtnTextSize() {
            return btnTextSize;
        }
    }

    public NespTipbar setOnShownListener(OnShownListener onShownListener) {
        this.onShownListener = onShownListener;
        return this;
    }

    public NespTipbar setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public interface OnShownListener {
        void onShown();
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}
