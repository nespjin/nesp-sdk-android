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

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nesp.sdk.android.R;

import androidx.annotation.Nullable;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-4-23 上午1:09
 * @project FishMovie
 **/
public class IOSLoading extends View {

    private int mWidth;

    private int mHeight;

    private int mCenterX;

    private int mCenterY;

    private Paint mPaint;

    private final int mDefaultColor = 0xff999999;

    private final int mDefaultSegmentWidth = 10;

    private final int mDefaultSegmentLength = 20;

    private int mSegmentWidth = mDefaultSegmentWidth;

    private int mSegmentColor = mDefaultColor;

    private int mSegmentLength = mDefaultSegmentLength;

    private int control = 1;

    public IOSLoading(Context context) {
        this(context, null);
    }

    public IOSLoading(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IOSLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IOSLoading);

        int indexCount = typedArray.getIndexCount();

        for (int i = 0; i < indexCount; i++) {

            int attr = typedArray.getIndex(i);

            if (attr == R.styleable.IOSLoading_pathColor) {
                mSegmentColor = typedArray.getColor(attr, mDefaultColor);
            } else if (attr == R.styleable.IOSLoading_segmentLength) {
                mSegmentLength = typedArray.getDimensionPixelSize(attr, mDefaultSegmentLength);
            } else if (attr == R.styleable.IOSLoading_segmentWidth) {
                mSegmentWidth = typedArray.getDimensionPixelSize(attr, mDefaultSegmentWidth);
            }

        }

        typedArray.recycle();


        mPaint = new Paint();

        mPaint.setAntiAlias(true);

        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setColor(mSegmentColor);

        mPaint.setStrokeWidth(mSegmentWidth);
    }


    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);

        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mCenterX = mWidth / 2;

        mCenterY = mHeight / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        for (int i = 0; i < 12; i++) {

            mPaint.setAlpha(((i + 1 + control) % 12 * 255) / 12);

            canvas.drawLine(mCenterX, mCenterY - mSegmentLength, mCenterX, mCenterY - mSegmentLength * 2, mPaint);

            canvas.rotate(30, mCenterX, mCenterY);

        }

    }


    @Override

    protected void onFinishInflate() {

        super.onFinishInflate();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(12, 1);

        valueAnimator.setDuration(1000);

        valueAnimator.setInterpolator(new LinearInterpolator());

        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);

        valueAnimator.setRepeatMode(ValueAnimator.RESTART);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override

            public void onAnimationUpdate(ValueAnimator animation) {

                control = (int) animation.getAnimatedValue();

                invalidate();

            }

        });

        valueAnimator.start();

    }
}
