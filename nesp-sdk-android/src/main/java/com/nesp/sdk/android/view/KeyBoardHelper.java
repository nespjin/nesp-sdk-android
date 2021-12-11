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

package com.nesp.sdk.android.view;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Build;

import androidx.annotation.RequiresApi;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 18-12-15 上午11:47
 **/
public class KeyBoardHelper {

    private final Activity mActivity;
    private OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener;
    private final int mScreenHeight;
    // 空白高度 = 屏幕高度 - 当前 Activity 的可见区域的高度
    // 当 blankHeight 不为 0 即为软键盘高度。
    private int mBlankHeight = 0;

    public KeyBoardHelper(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity must not be null");
        }
        this.mActivity = activity;
        this.mScreenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void onCreate() {
        View content = mActivity.findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onDestroy() {
        View content = mActivity.findViewById(android.R.id.content);
        content.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener =
            new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Rect rect = new Rect();
                    mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                    int newBlankHeight = mScreenHeight - rect.bottom;
                    if (newBlankHeight != mBlankHeight) {
                        if (newBlankHeight == 0) {
                            // keyboard close
                            if (onKeyBoardStatusChangeListener != null) {
                                onKeyBoardStatusChangeListener.OnKeyBoardClose(mBlankHeight);
                            }
                        } else {
                            // keyboard pop
                            if (onKeyBoardStatusChangeListener != null) {
                                onKeyBoardStatusChangeListener.OnKeyBoardPop(newBlankHeight);
                            }
                        }
                    }
                    mBlankHeight = newBlankHeight;
                }
            };

    public void setOnKeyBoardStatusChangeListener(
            OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener) {
        this.onKeyBoardStatusChangeListener = onKeyBoardStatusChangeListener;
    }

    public interface OnKeyBoardStatusChangeListener {

        void OnKeyBoardPop(int keyBoardHeight);

        void OnKeyBoardClose(int oldKeyBoardHeight);
    }
}
