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
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.nesp.sdk.android.graphics.BitmapUtil;

import java.io.Serializable;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 18-12-21 下午2:06
 * @project MuBingMovie
 **/
@Deprecated
public class CircleImageView extends androidx.appcompat.widget.AppCompatImageView implements Serializable {

    private float width;
    private float height;
    private float radius;
    private Paint paint;
    private Matrix matrix;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);   //设置抗锯齿
        matrix = new Matrix();      //初始化缩放矩阵
    }

    /**
     * 测量控件的宽高，并获取其内切圆的半径
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        radius = Math.min(width, height) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        BitmapShader bitmapShader = initBitmapShader();
        if (bitmapShader == null) {
            Toast.makeText(getContext(), "不支持的图片类型", Toast.LENGTH_SHORT).show();
        } else {
            paint.setShader(initBitmapShader());//将着色器设置给画笔
            canvas.drawCircle(width / 2, height / 2, radius, paint);//使用画笔在画布上画圆
        }
    }

    public void setImageBase64(String base64) {
        setImageBitmap(BitmapUtil.base64ToBitmap(base64));
    }

    public String getImageBase64() {
        return BitmapUtil.bitmapToBase64(((BitmapDrawable) getDrawable()).getBitmap());
    }

    /**
     * 获取ImageView中资源图片的Bitmap，利用Bitmap初始化图片着色器,通过缩放矩阵将原资源图片缩放到铺满整个绘制区域，避免边界填充
     */
    private BitmapShader initBitmapShader() {

        Bitmap bitmap = null;
        if (getDrawable() != null) {
            try {
                bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                bitmap = BitmapUtil.drawableToBitmap(getBackground());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if (bitmap == null) return null;
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = Math.max(width / bitmap.getWidth(), height / bitmap.getHeight());
        matrix.setScale(scale, scale);//将图片宽高等比例缩放，避免拉伸
        bitmapShader.setLocalMatrix(matrix);
        return bitmapShader;
    }

}
