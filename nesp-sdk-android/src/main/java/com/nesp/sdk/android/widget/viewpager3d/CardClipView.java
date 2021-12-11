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

package com.nesp.sdk.android.widget.viewpager3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

/**
 * Created by tangshuai on 2017/9/18.
 */

public class CardClipView extends CardView implements Clipable {

    private Rect rect = null;

    public CardClipView(Context context) {
        super(context);
    }

    public CardClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardClipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setClipBound(Rect rect) {
        this.rect = rect;
        postInvalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (rect != null)
            canvas.clipRect(rect, Region.Op.INTERSECT);
        super.dispatchDraw(canvas);
    }
}
