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

/**
 * Created by tangshuai on 2017/9/17.
 */

public class LinearScaleTransformer implements ScaleTransformer {

    private float scaleFactor;

    public LinearScaleTransformer(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    @Override
    public float getScale(float position) {
        return (1 - scaleFactor) + scaleFactor * (1 - Math.abs(position));
    }
}
