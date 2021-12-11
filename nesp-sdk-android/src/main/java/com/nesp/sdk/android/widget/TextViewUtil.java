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

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/7/25 10:46
 * @Project Assistant
 **/
public final class TextViewUtil {

    private TextViewUtil() {
        //no instance
    }

    /**
     * TextView下划线
     */
    public static void setUnderLine(TextView textView, String text) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        textView.getPaint().setAntiAlias(true);//抗锯齿
        textView.setText(android.text.Html.fromHtml(text));
    }

    /**
     * 设置TextView显示的字体
     *
     * @param context ctx
     * @param textView tv
     * @param typefaceAssetsPath src/main/assets/fonts/DINCond-Bold-Number.ttf => "fonts/DINCond-Bold-Number.ttf"
     */
    public static void setTypeFace(Context context, TextView textView, String typefaceAssetsPath) {
         textView.setTypeface(Typeface.createFromAsset(context.getAssets(), typefaceAssetsPath));
    }

}
