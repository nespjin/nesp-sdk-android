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

package com.nesp.sdk.android.widget.recyclerview;

import android.graphics.Rect;

/**
 * @author <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 12/16/21 3:43 PM
 * Description:
 **/
public final class ItemDecorationUtil {

    private static final String TAG = "ItemDecorationUtil";

    private ItemDecorationUtil() {
        //no instance
    }

    /**
     * 水平平均分配两端对齐
     *
     * @param columnIndex 列位置
     * @param itemSpace   间距
     * @param spanCount   总列数
     * @param outRect     偏移量输出
     */
    public static void spaceBetweenHorizontal(final int columnIndex, final int itemSpace,
                                              final int spanCount, final Rect outRect) {
        outRect.left = columnIndex * itemSpace / spanCount;
        outRect.right = itemSpace - (columnIndex + 1) * itemSpace / spanCount;
    }

}
