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

public class LinearRotationTransformer implements RotationTransformer {

    private float rotation;

    private float maxRotation;

    public LinearRotationTransformer(float rotation, float maxRotation) {
        this.rotation = rotation;
        this.maxRotation = Math.abs(maxRotation);
    }

    @Override
    public float getRotation(float position) {
        float rotationY = -position * rotation;
        rotationY = rotationY >= 90 ? 89.9999f : rotationY;
        rotationY = rotationY <= -90 ? -89.9999f : rotationY;

        rotationY = rotationY >= maxRotation ? maxRotation : rotationY;
        rotationY = rotationY <= -maxRotation ? -maxRotation : rotationY;

        return rotationY;
    }
}
