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

package com.nesp.sdk.android.app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.nesp.sdk.android.app.TipDialog;
import com.nesp.sdk.android.view.ViewUtil;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-6-14 上午1:55
 **/
public final class DialogUtil {
    private DialogUtil() {
        //no instance
    }

    /**
     * 全屏显示对画框
     *
     * @param dialog 对话框
     */
    public static void showFullWindowDialog(Dialog dialog) {
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.show();
        ViewUtil.fullScreenImmersive(window.getDecorView());
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    /**
     * 隐藏虚拟按键
     */
    public static void hideBottomNav(Dialog dialog) {
        Window window = dialog.getWindow();
        if (window == null || window.getDecorView() == null) {
            return;
        }
        //不加FLAG_NOT_FOCUSABLE，dialog显示时就会显示虚拟按键
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.setOnShowListener(d -> {
            //dialog显示之后，要清除FLAG_NOT_FOCUSABLE，否则不会弹出软键盘
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        });
        //隐藏虚拟按键
        hideBottomNavInner(dialog);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            //从后台重新进入时，要再次隐藏虚拟按键
            hideBottomNavInner(dialog);
        });
    }

    private static void hideBottomNavInner(Dialog dialog) {
        View decorView = dialog.getWindow().getDecorView();
        int vis = decorView.getSystemUiVisibility();
        vis |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(vis);
    }

    public static void showSuccessTipDialog(final Activity activity, String msg) {
        showTipDialog(activity, TipDialog.Type.SUCCESS, msg);
    }

    public static void showWarningTipDialog(final Activity activity, String msg) {
        showTipDialog(activity, TipDialog.Type.WARNING, msg);
    }

    public static void showErrorTipDialog(final Activity activity, String msg) {
        showTipDialog(activity, TipDialog.Type.ERROR, msg);
    }

    public static TipDialog showLoadingDialog(final Activity activity, String msg) {
        if (activity == null) return null;
        return TipDialog.showTip(activity, TipDialog.Type.LOADING, msg);
    }

    public static void showTipDialog(final Activity activity, TipDialog.Type type, String msg) {
        if (activity == null) return;
        activity.runOnUiThread(() -> TipDialog.showTip(activity, type, msg));
    }

}
