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

import com.nesp.sdk.android.R;

import androidx.annotation.NonNull;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/7/12 10:51
 * @Project Assistant
 **/
@Deprecated
public class Dialog extends android.app.Dialog implements View.OnClickListener {

    private static final String TAG = "Dialog";

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

    /**
     * Overload method
     *
     * @param context       context
     * @param layoutResID   layout id of Dialog
     * @param listenedItems listener for layout widget
     */
    public Dialog(@NonNull Context context, int layoutResID, int[] listenedItems) {
        super(context, R.style.dialog_custom); //dialog的样式
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
    }

    /**
     * Overload method to custom Theme
     *
     * @param context       {@link Dialog#Dialog(Context, int, int[])}
     * @param layoutResID   {@link Dialog#Dialog(Context, int, int[])}
     * @param listenedItems {@link Dialog#Dialog(Context, int, int[])}
     * @param themeResID    Custom theme id of dialog
     */
    public Dialog(@NonNull Context context, int layoutResID, int[] listenedItems, int themeResID) {
        super(context, themeResID);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
        this.themeResID = themeResID;
    }

    /**
     * Overload method to custom Theme，custom gravity
     *
     * @param context       {@link Dialog#Dialog(Context, int, int[])}
     * @param layoutResID   {@link Dialog#Dialog(Context, int, int[])}
     * @param listenedItems {@link Dialog#Dialog(Context, int, int[])}
     * @param themeResID    {@link Dialog#Dialog(Context, int, int[], int)}
     * @param windowGravity Gravity for Dialog Window
     */
    public Dialog(@NonNull Context context, int layoutResID, int[] listenedItems, int themeResID, int windowGravity) {
        super(context, themeResID);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
        this.themeResID = themeResID;
        this.windowGravity = windowGravity;
    }

    /**
     * Overload method to custom Theme，custom gravity,custom animal
     *
     * @param context       {@link Dialog#Dialog(Context, int, int[])}
     * @param layoutResID   {@link Dialog#Dialog(Context, int, int[])}
     * @param listenedItems {@link Dialog#Dialog(Context, int, int[])}
     * @param themeResID    {@link Dialog#Dialog(Context, int, int[], int)}
     * @param windowGravity {@link Dialog#Dialog(Context, int, int[], int, int)}
     * @param animalResID   Dialog Animal ResID
     */
    public Dialog(@NonNull Context context, int layoutResID, int[] listenedItems, int themeResID, int windowGravity, int animalResID) {
        super(context, themeResID);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
        this.themeResID = themeResID;
        this.windowGravity = windowGravity;
        this.animalResID = animalResID;
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
        if (windowGravity != 0) {
            if (windowGravity == Gravity.BOTTOM) window.getDecorView().setPadding(0, 0, 0, 20);
            window.setGravity(windowGravity);
        } else {
            window.getDecorView().setPadding(0, 0, 0, 20);
            window.setGravity(Gravity.BOTTOM);
        }
        /**
         * Set Dialog Window Animal
         */
        if (animalResID != 0) window.setWindowAnimations(animalResID);
        else window.setWindowAnimations(R.style.nesp_sdk_center_dialog_animation);
        /**
         * Set Dialog Window Layout
         */
        setContentView(layoutResID);
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
        void OnDialogItemsClick(Dialog dialog, View view);
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
