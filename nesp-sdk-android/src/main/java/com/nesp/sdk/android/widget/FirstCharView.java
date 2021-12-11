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
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.nesp.sdk.android.R;

import java.util.Random;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 18-11-22 下午1:28
 * @Project Imagindemo
 **/
public class FirstCharView extends View {

    private String text = "";

    private int circleBackgroundColor = Color.YELLOW;
    private int textColor = Color.WHITE;
    private Boolean isRandomBackgroundColor = false;
    private Boolean isRandomTextColor = false;
    private String[] circleBackgroundColors =
            new String[]{
                    "#616161", "#536DFE", "#7B1FA2", "#fb8c00", "#d84315",
                    "#795548", "#D32F2F", "#FFCDD2", "#FF5252",
                    "#03A9F4", "#2196F3", "#4CAF50", "#FF9800"};
    private String[] textColors = new String[]{"#f25252", "#FFF4CA23", "#FFD123D7", "#FFCB75ED"};


    private Paint paintBackground;
    private Paint paintText;

    public FirstCharView(Context context) {
        this(context, null);
    }

    public FirstCharView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FirstCharView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FirstCharView);
        circleBackgroundColor = typedArray.getColor(R.styleable.FirstCharView_circleBackgroundColor, circleBackgroundColor);
        textColor = typedArray.getColor(R.styleable.FirstCharView_textColor, textColor);
        text = typedArray.getString(R.styleable.FirstCharView_text);

        typedArray.recycle();

        paintBackground = new Paint();
        paintText = new Paint();
    }

    public FirstCharView setRandomBackgroundColor(Boolean randomBackgroundColor) {
        this.isRandomBackgroundColor = randomBackgroundColor;
        invalidate();
        return this;
    }

    public FirstCharView setRandomTextColor(Boolean randomTextColor) {
        this.isRandomTextColor = randomTextColor;
        invalidate();
        return this;
    }

    public FirstCharView setCircleBackgroundColors(String[] circleBackgroundColors) {
        this.circleBackgroundColors = circleBackgroundColors;
        invalidate();
        return this;
    }

    public FirstCharView setTextColors(String[] textColors) {
        this.textColors = textColors;
        invalidate();
        return this;
    }

    public FirstCharView setText(String text) {
        this.text = text;
        invalidate();
        return this;
    }

    public FirstCharView setTextFirstChar(String text) {
        this.text = text.substring(0, 1);
        invalidate();
        return this;
    }

    public FirstCharView setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
        return this;
    }

    public FirstCharView setCircleBackgroundColor(int circleBackgroundColor) {
        this.circleBackgroundColor = circleBackgroundColor;
        invalidate();
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int width = getWidth();
        final int height = getHeight();
        final int r = width / 2;

        paintBackground.setAntiAlias(true);
        paintText.setAntiAlias(true);

        if (isRandomBackgroundColor) {
            final Random random = new Random();
            final int n = random.nextInt(circleBackgroundColors.length);
            circleBackgroundColor = Color.parseColor(circleBackgroundColors[n]);
        }
        paintBackground.setColor(circleBackgroundColor);

        if (isRandomTextColor) {
            Random random1 = new Random();
            int n1 = random1.nextInt(textColors.length);
            textColor = Color.parseColor(textColors[n1]);
        }
        paintText.setColor(textColor);


        paintText.setTextSize(width / 2);

        paintText.setStrokeWidth(8);

        paintText.setTextAlign(Paint.Align.LEFT);

        canvas.drawCircle(width / 2, height / 2, r, paintBackground);
        canvas.drawText(text, width / 2 - paintText.measureText(text) / 2, height / 2 + 1.5f * paintText.getFontMetrics().bottom, paintText);
    }


}
