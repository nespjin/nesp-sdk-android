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

package com.nesp.sdk.android.utils;

import android.content.Context;

/**
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 12/14/21 4:48 PM
 * Description:
 **/
public final class DisplayUtil2 {
    private DisplayUtil2() {
        //no instance
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = getScale(context);
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = getScale(context);
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = getScale(context);
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = getScale(context);
        return (int) (spValue * fontScale + 0.5f);
    }

    private static float getScale(Context context) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return findScale(fontScale);
    }

    private static float findScale(float scale) {
        if (scale <= 1) {
            scale = 1;
        } else if (scale <= 1.5) {
            scale = 1.5f;
        } else if (scale <= 2) {
            scale = 2f;
        } else if (scale <= 3) {
            scale = 3f;
        }
        return scale;
    }
}
