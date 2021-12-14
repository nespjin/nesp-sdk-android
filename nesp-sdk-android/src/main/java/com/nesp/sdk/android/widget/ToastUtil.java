/*
 *
 *   Copyright (c) 2021  NESP Technology Corporation. All rights reserved.
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

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.nesp.sdk.java.text.TextUtil;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/12 9:56
 **/
public final class ToastUtil {

    private ToastUtil() {
        //no instance
    }

    public static void showLongToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    public static void showShortToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String message, int duration) {
        if (context == null || TextUtil.isEmpty(message)) return;
        Toast.makeText(context, message, duration).show();
    }

    public static void showTopLongToast(Context context, String message) {
        showTopToast(context, message, Toast.LENGTH_LONG);
    }

    public static void showTopShortToast(Context context, String message) {
        showTopToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showTopToast(Context context, String message, int duration) {
        if (context == null || TextUtil.isEmpty(message)) return;
        Toast toast = Toast.makeText(context, message, duration);
        toast.setGravity(Gravity.TOP, 0, 150);
        toast.show();
    }

}
