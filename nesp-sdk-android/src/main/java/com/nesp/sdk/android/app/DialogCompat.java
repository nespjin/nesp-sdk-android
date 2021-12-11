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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.nesp.sdk.android.R;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/7/12 10:51
 * @Project Assistant
 **/
@Deprecated
public class DialogCompat extends android.app.Dialog implements View.OnClickListener {

    private static final String TAG = "DialogCompat";

    public static class Builder {

        protected Context context;
        /**
         * Dialog layout file ID
         */
        private int layoutResID;
        /**
         * The listener for widget of dialog layout
         */
        private int[] listenedItems;
        /**
         * The theme of Dialog
         */
        private int themeResID;
        /**
         * Gravity for Dialog Window
         * <p>
         * default：Gravity.CENTER
         */
        private int windowGravity;
        /**
         * Dialog Animal ResID
         */
        private int animalResID;

        private View dialogView;

        public Builder(Context context, int layoutResId) {
            this.context = context;
            this.layoutResID = layoutResId;
        }

        public Builder(Context context, View dialogView) {
            this.context = context;
            this.dialogView = dialogView;
        }

        public Context getContext() {
            return context;
        }

        public int getLayoutResID() {
            return layoutResID;
        }

        public Builder setLayoutResID(int layoutResID) {
            this.layoutResID = layoutResID;
            return this;
        }

        public View getDialogView() {
            return dialogView;
        }

        public int[] getListenedItems() {
            return listenedItems;
        }

        public Builder setListenedItems(int[] listenedItems) {
            this.listenedItems = listenedItems;
            return this;
        }

        public int getThemeResID() {
            return themeResID;
        }

        public Builder setThemeResID(int themeResID) {
            this.themeResID = themeResID;
            return this;
        }

        public int getWindowGravity() {
            return windowGravity;
        }

        public Builder setWindowGravity(int windowGravity) {
            this.windowGravity = windowGravity;
            return this;
        }

        public int getAnimalResID() {
            return animalResID;
        }

        public Builder setAnimalResID(int animalResID) {
            this.animalResID = animalResID;
            return this;
        }

        public DialogCompat create() {
            return new DialogCompat(this);
        }
    }


    protected Context context;
    /**
     * Dialog layout file ID
     */
    private int layoutResID;
    /**
     * The listener for widget of dialog layout
     */
    private int[] listenedItems;
    /**
     * The theme of Dialog
     */
    private int themeResID;
    /**
     * Gravity for Dialog Window
     * <p>
     * default：Gravity.CENTER
     */
    private int windowGravity = Gravity.CENTER;

    /**
     * Dialog Animal ResID
     */
    private int animalResID;

    private View dialogView;

    private DialogCompat(Builder builder) {
        super(builder.getContext(), R.style.dialog_custom); //dialog的样式
        this.context = builder.getContext();
        this.layoutResID = builder.getLayoutResID();
        this.listenedItems = builder.getListenedItems();
        this.themeResID = builder.getThemeResID();
        this.windowGravity = builder.getWindowGravity();
        this.dialogView = builder.getDialogView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Window Settings
         */
        Window window = getWindow();
        /**
         * Set Dialog Window Gravity
         *
         * default:Gravity.BOTTOM
         */
        if (windowGravity == Gravity.BOTTOM) {
            window.getDecorView().setPadding(0, 0, 0, 20);
            animalResID = R.style.nesp_sdk_bottom_dialog_animation;
        } else if (windowGravity == Gravity.CENTER) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            animalResID = R.style.nesp_sdk_center_dialog_animation;
        }

        window.setGravity(windowGravity);
        window.setWindowAnimations(animalResID);

        /**
         * Set Dialog Window Layout
         */
        if (dialogView != null) {
            ((ViewGroup) dialogView.getParent())
                    .removeView(dialogView);
            setContentView(dialogView);
        } else {
            setContentView(layoutResID);
        }
        /**
         * Set Dialog Window param
         */
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
//        layoutParams.width = displayMetrics.widthPixels * 4 / 5;//Set Width with to Screen's 4/5
        layoutParams.width = displayMetrics.widthPixels * 95 / 100;//Set Width with to Screen's 95/100
        window.setAttributes(layoutParams);
        setCanceledOnTouchOutside(true); // Click Dialog outside to Cancel
        /**
         *  Traversal(遍历) the Widget id to add a click event
         */
        if (listenedItems != null && listenedItems.length > 0)
            for (int id : listenedItems) {
                findViewById(id).setOnClickListener(this);
            }
    }

    //================================ Interface =======================================
    private OnDialogItemsClickListener onDialogItemsClickListener;

    public interface OnDialogItemsClickListener {
        void OnDialogItemsClick(DialogCompat dialog, View view);
    }

    public void setOnDialogItemsClickListener(OnDialogItemsClickListener onDialogItemsClickListener) {
        this.onDialogItemsClickListener = onDialogItemsClickListener;
    }

    @Override
    public void onClick(View view) {
        dismiss();//click widget to dismiss
        if (onDialogItemsClickListener != null)
            onDialogItemsClickListener.OnDialogItemsClick(this, view);
    }
}
