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

package com.nesp.sdk.android.view;

import android.view.View;
import android.widget.TextView;

import com.nesp.sdk.android.utils.Optional;
import com.nesp.sdk.java.text.TextUtil;

/**
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 12/14/21 5:06 PM
 * Description:
 **/
public final class TextViewUtil {

    private TextViewUtil() {
        //no instance
    }

    public static void goneIfTextEmpty(TextView view) {
        goneIfTextEmpty(view, Optional.ofNullable(view.getText()).orElse("").toString());
    }

    public static void goneIfTextEmpty(View view, String text) {
        if (TextUtil.isEmpty(text)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
