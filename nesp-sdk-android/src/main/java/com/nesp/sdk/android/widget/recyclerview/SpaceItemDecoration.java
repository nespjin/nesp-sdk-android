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

package com.nesp.sdk.android.widget.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nesp.sdk.android.utils.DisplayUtil;

import org.jetbrains.annotations.NotNull;

/**
 * GridLayoutManager设置一个3列列表的间距，左右贴边，中间居中。
 * 由于RecyclerView会将宽自动填充满，因此每个item的右边都会留有空白部分， 3列.
 *
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 18-12-19 下午11:55
 * @project MuBingMovie
 **/
@Deprecated
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int offset = 0;
    private final int space;  //位移间距
    private final int outSpace;  //位移间距

    public SpaceItemDecoration(int space, int outSpace) {
        this.space = space;
        this.outSpace = outSpace;
    }

    public SpaceItemDecoration(int space, int outSpace, int offset) {
        this.offset = offset;
        this.space = space;
        this.outSpace = outSpace;
    }

    public SpaceItemDecoration(Context context, @DimenRes int space, @DimenRes int outSpace) {
        this.space = DisplayUtil.dp2px(context, context.getResources().getDimension(space));
        this.outSpace = DisplayUtil.dp2px(context, context.getResources().getDimension(outSpace));
    }

    public SpaceItemDecoration(Context context, @DimenRes int space, @DimenRes int outSpace, int offset) {
        this.offset = offset;
        this.space = DisplayUtil.dp2px(context, context.getResources().getDimension(space));
        this.outSpace = DisplayUtil.dp2px(context, context.getResources().getDimension(outSpace));
    }


    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        if (layoutManager == null) return;

        int spanCount = layoutManager.getSpanCount();

        int childPos = parent.getChildAdapterPosition(view);

        if ((childPos + offset) % spanCount == 0) {
            outRect.left = outSpace; //第一列左边贴边
        } else {
            if ((childPos + offset) % spanCount == 1) {
                outRect.left = space;//第二列移动一个位移间距
            } else {
                outRect.left = space * 2;//由于第二列已经移动了一个间距，所以第三列要移动两个位移间距就能右边贴边，且item间距相等
            }
        }

        //        if (parent.getChildAdapterPosition(view) >= 3) {
        //            outRect.top = SizeUtils.dp2px(context, 10);
        //        } else {
        //            outRect.top = 0;
        //        }
    }
}
