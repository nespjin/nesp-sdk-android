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

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @Team : NESP Technology
 * @Author : nesp
 * Email : 1756404649@qq.com
 * @Time : 18-4-2 上午12:00
 */
public final class ListViewUtil {

    private ListViewUtil() {
        //no instance
    }

    private static final String TAG = "ListViewUtils";

    /**
     * 计算ListView高度
     * 动态设置ListView高度
     *
     * @param listView ListView对象
     */
    public static void setListViewHeightBaseOnChildren(ListView listView) {

        //获取ListView对应的Adapter
        final ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {

            return;
        }

        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            //listAdapter.getCount() 返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            //计算子view的宽高
            listItem.measure(0, 0);//父布局一定要为LinearLayout，否则可能会出现空指针
            //统计所有子项的总高
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listView.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}