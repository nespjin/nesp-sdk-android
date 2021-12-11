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

package com.nesp.sdk.android.webkit;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.nesp.sdk.android.R;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 18-11-12 下午9:43
 * @Project Assistant
 **/
public class WebLinearLayout extends LinearLayout {

    private static final String TAG = "WebLinearLayout";

    private View mTop;
    private LinearLayout ll_dynamic_web;
    private int mTopViewHeight;
    private OverScroller mScroller;

    private Integer webHeadId, webWrapId;


    public WebLinearLayout(Context context) {
        this(context, null);
    }

    public WebLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public WebLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WebLinearLayout);
        webHeadId = typedArray.getResourceId(R.styleable.WebLinearLayout_web_head_layout_id, 0);
        webWrapId = typedArray.getResourceId(R.styleable.WebLinearLayout_web_wrap_layout_id, 0);
        typedArray.recycle();
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (webHeadId == 0) {
            Log.e(TAG, "WebLinearLayout.onFinishInflate:======== webHeadId not setted =======");
            throw new RuntimeException("Please set webHeadId");
        } else if (webWrapId == 0) {
            Log.e(TAG, "WebLinearLayout.onFinishInflate:======== webWrapId not setted =======");
            throw new RuntimeException("Please set webWrapId");
        }

        //这个id必须能找到
        mTop = findViewById(webHeadId);
        ll_dynamic_web = findViewById(webWrapId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = ll_dynamic_web.getLayoutParams();
        params.height = getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), mTop.getMeasuredHeight() + ll_dynamic_web.getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTop.getMeasuredHeight();
    }

    @Override
    public void scrollTo(int x, int y) {

        if (y < 0) {
            y = 0;
        } else if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }



        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
}
