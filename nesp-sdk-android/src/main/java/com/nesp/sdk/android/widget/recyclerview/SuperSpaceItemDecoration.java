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

import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.nesp.sdk.android.device.ScreenUtil;

import java.util.HashMap;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class SuperSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "SuperSpaceItemDecoration";
    private Boolean isAuto = true;

    private int gapHSizePx = -1;
    private int gapVSizePx = -1;
    private int edgePaddingPx;
    private int eachItemPaddingH;
    private Rect preRect;
    private float gapHorizontalDp;
    private float gapVerticalDp;
    private float edgePaddingDp;
    private int offest = 0;
    public static final String VERTICAL_DECORATION = "Vertical_decoration";
    public static final String HORIZONTAL_DECORATION = "Horizontal_decoration";
    public static final String EDGE_PADDING = "edge_padding";


    public SuperSpaceItemDecoration(@NotNull HashMap<String, Float> mSpaceValueMap) {
        this.gapVerticalDp = mSpaceValueMap.get(VERTICAL_DECORATION);
        this.gapHorizontalDp = mSpaceValueMap.get(HORIZONTAL_DECORATION);
        this.edgePaddingDp = mSpaceValueMap.get(EDGE_PADDING);
    }

    public SuperSpaceItemDecoration(@NotNull HashMap<String, Float> mSpaceValueMap, int offest) {
        this.gapVerticalDp = mSpaceValueMap.get(VERTICAL_DECORATION);
        this.gapHorizontalDp = mSpaceValueMap.get(HORIZONTAL_DECORATION);
        this.edgePaddingDp = mSpaceValueMap.get(EDGE_PADDING);
        this.offest = offest;
    }

    public SuperSpaceItemDecoration setAuto(Boolean auto) {
        isAuto = auto;
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = layoutManager.getSpanCount();
            int count = parent.getAdapter().getItemCount();
            int position = parent.getChildAdapterPosition(view);
            if (gapHSizePx < 0 || gapVSizePx < 0) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                parent.getDisplay().getMetrics(displayMetrics);
                edgePaddingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, edgePaddingDp, displayMetrics);
                view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                gapHSizePx = isAuto ? (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gapHorizontalDp, displayMetrics)
                        : (ScreenUtil.getScreenWidth(parent.getContext()) - spanCount * view.getMeasuredWidth() - edgePaddingPx * 2) / (spanCount - 1);

                gapVSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gapVerticalDp, displayMetrics);
                eachItemPaddingH = (edgePaddingPx * 2 + gapHSizePx * (spanCount - 1)) / spanCount;
            }
            outRect.top = gapVSizePx;
            outRect.bottom = 0;

            int visualPos = position + 1 + offest;

            if (visualPos % spanCount == 1) {
                //第一列
                outRect.left = edgePaddingPx;
                outRect.right = isAuto ? eachItemPaddingH - edgePaddingPx : gapHSizePx / 2;
            } else if (visualPos % spanCount == 0) {
                //最后一列
                outRect.left = isAuto ? eachItemPaddingH - edgePaddingPx : gapHSizePx / 2;
                outRect.right = edgePaddingPx;
            } else {
                outRect.left = isAuto ? gapHSizePx - preRect.right : gapHSizePx / 2;
                outRect.right = isAuto ? eachItemPaddingH - outRect.left : gapHSizePx / 2;
            }
            if (visualPos - spanCount <= 0) {
                //第一行
                outRect.top = 0;
            } else if (isLastRow(visualPos, spanCount, count)) {
                //最后一行
            }
            preRect = new Rect(outRect);
//            Log.e(TAG, "SuperSpaceItemDecoration.getItemOffsets: " + "pos=" + position + "," + outRect.toShortString());
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

    private boolean isLastRow(int visualPos, int spanCount, int sectionItemCount) {
        int lastRowCount = sectionItemCount % spanCount;
        lastRowCount = lastRowCount == 0 ? spanCount : lastRowCount;
        return visualPos > sectionItemCount - lastRowCount;
    }
}
