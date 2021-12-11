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

package com.nesp.sdk.android.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/7/19 13:06
 * @Project Assistant
 **/
public final class ScreenShotUtil {

    private ScreenShotUtil() {
        //no instance
    }

    /**
     * 获取当前屏幕截屏
     *
     * @param context 当前 Activity
     * @param bar     所截取部分顶部的位置（根据自身需求更改）
     * @param bottom  所截取部分底部的位置（根据自身需求更改）
     *                提示：截取顶部和底部不要的部分，剩下的就是需要截取的内容
     * @return 当前屏幕截屏
     */
    public static Bitmap createScreenShot(Activity context, View bar, LinearLayout bottom) {
        // 获取当前页面布局
        View view = context.getWindow().getDecorView().getRootView();
        // 设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        // 从缓存中获取当前屏幕的图片
        Bitmap temp = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        // 获取手机屏幕布局
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        // 获取屏幕长和高
        int width = context.getWindowManager().getDefaultDisplay().getWidth();
        int height = context.getWindowManager().getDefaultDisplay().getHeight();
        // 获取系统状态栏高度
        int statusBarHeight_s = frame.top;
        // 获取项目状态栏高度
        int statusBarHeight_p = bar.getMeasuredHeight();
        //获取项目底部高度
        int statusBarHeight_b = bottom.getMeasuredHeight();
        // 去掉状态栏，如果需要的话
        return Bitmap.createBitmap(temp, 0, statusBarHeight_s + statusBarHeight_p,
                width, height - statusBarHeight_s - statusBarHeight_p - statusBarHeight_b);
    }


}
