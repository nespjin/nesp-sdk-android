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

package com.nesp.sdk.android.graphics;

import static com.nesp.sdk.android.graphics.BitmapUtil.drawableToBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.hoko.blur.HokoBlur;
import com.hoko.blur.task.AsyncBlurTask;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-1-24 下午4:47
 * @project FishMovie
 **/
@Deprecated
public final class BlurUtil {
    private BlurUtil() {
        //no instance
    }

    public static void BlurBitmap(Context context, Bitmap bitmap) {
        HokoBlur.with(context)
                .processor().blur(bitmap);
    }

    public static void BlurBitmap(Context context, Bitmap bitmap, AsyncBlurTask.Callback callback) {
        HokoBlur.with(context)
                .processor().asyncBlur(bitmap, callback);
    }

    public static Bitmap BlurBitmap(Context context, Bitmap bitmap, int radius, float scale) {
        return HokoBlur.with(context)
                .radius(radius)
                .sampleFactor(scale)
                .processor().blur(bitmap);
    }

    public static void BlurBitmap(Context context, Bitmap bitmap, int radius, float scale, AsyncBlurTask.Callback callback) {
        HokoBlur.with(context)
                .radius(radius)
                .sampleFactor(scale)
                .processor()
                .asyncBlur(bitmap, callback);
    }

    public static void BlurDrawable(Context context, Drawable drawable) {
        BlurBitmap(context, drawableToBitmap(drawable));
    }

    public static void BlurDrawable(Context context, Drawable drawable, AsyncBlurTask.Callback callback) {
        BlurBitmap(context, drawableToBitmap(drawable), callback);
    }

    public static void BlurDrawable(Context context, Drawable drawable, int radius, float scale) {
        BlurBitmap(context, drawableToBitmap(drawable), radius, scale);
    }

    public static void BlurDrawable(Context context, Drawable drawable, int radius, float scale, AsyncBlurTask.Callback callback) {
        BlurBitmap(context, drawableToBitmap(drawable), radius, scale, callback);
    }

    public static void BlurView(Context context, View view) {
        HokoBlur.with(context)
                .processor().blur(view);
    }

    public static void BlurView(Context context, View view, int radius, float scale) {
        HokoBlur.with(context)
                .radius(radius)
                .sampleFactor(scale)
                .processor().blur(view);
    }
}
