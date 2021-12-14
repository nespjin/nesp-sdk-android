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

import android.os.Build;
import android.view.View;

import com.nesp.sdk.java.util.ArrayUtil;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-8-13 下午3:31
 **/
public final class ViewUtil {

    private ViewUtil() {
        //no instance
    }

    public static void visible(View view) {
        if (view == null) return;
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void visible(View... views) {
        if (views == null || views.length <= 0) return;
        for (View view : views) {
            visible(view);
        }
    }

    public static void invisible(View view) {
        if (view == null) return;
        if (view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void invisible(View... views) {
        if (views == null || views.length <= 0) return;
        for (View view : views) {
            visible(view);
        }
    }

    public static void gone(View view) {
        if (view == null) return;
        if (view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }

    public static void gone(View... views) {
        if (views == null || views.length <= 0) return;
        for (View view : views) {
            gone(view);
        }
    }

    public static boolean isVisible(final View view) {
        if (view == null) return false;
        return view.getVisibility() == View.VISIBLE;
    }

    public static void isVisible(final View view, final boolean visible) {
        if (view == null) return;
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public static void isVisible(final View[] views, final boolean visible) {
        if (ArrayUtil.isEmpty(views)) {
            return;
        }

        for (final View view : views) {
            isVisible(view, visible);
        }
    }

    public static boolean isInvisible(final View view) {
        if (view == null) return false;
        return view.getVisibility() == View.INVISIBLE;
    }

    public static void isInvisible(final View view, final boolean visible) {
        if (view == null) return;
        if (visible) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public static void isInvisible(final View[] views, final boolean visible) {
        if (ArrayUtil.isEmpty(views)) {
            return;
        }

        for (final View view : views) {
            isVisible(view, visible);
        }
    }

    public static boolean isGone(final View view) {
        if (view == null) return false;
        return view.getVisibility() == View.GONE;
    }

    public static void isGone(final View view, final boolean visible) {
        if (view == null) return;
        if (visible) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void isGone(final View[] views, final boolean visible) {
        if (ArrayUtil.isEmpty(views)) {
            return;
        }

        for (final View view : views) {
            isGone(view, visible);
        }
    }

    public static void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }
}
