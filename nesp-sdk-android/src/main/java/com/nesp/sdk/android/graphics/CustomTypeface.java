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

import android.content.Context;
import android.graphics.Typeface;

/**********************************************************************
 * Created by jinzhaolu at 2018/04/10
 * 自定义字体类
 * 设置你需要的字体
 **********************************************************************/
@Deprecated
public class CustomTypeface {

    //Typeface是字体，这里我们创建一个对象
    private static Typeface typeface;

    /**
     * 设置字体
     */
    public static Typeface setFont(Context context, String fontName) {
        if (typeface == null) {
            //给它设置你传入的自定义字体文件，再返回回来
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
        }
        return typeface;
    }
}