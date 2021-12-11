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

package com.nesp.sdk.android.core.ktx.widget

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.Glide
import java.io.File

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/7/17 0:11
 * Project: FishMovieAndroid
 **/

fun ImageView.load(bitmap: Bitmap?) {
    Glide.with(context).load(bitmap).into(this)
}

fun ImageView.load(drawable: Drawable?) {
    Glide.with(context).load(drawable).into(this)
}

fun ImageView.load(string: String?) {
    Glide.with(context).load(string).into(this)
}

fun ImageView.load(uri: Uri?) {
    Glide.with(context).load(uri).into(this)
}

fun ImageView.load(file: File?) {
    Glide.with(context).load(file).into(this)
}

fun ImageView.load(@RawRes @DrawableRes resourceId: Int?) {
    Glide.with(context).load(resourceId).into(this)
}

fun ImageView.load(model: ByteArray?) {
    Glide.with(context).load(model).into(this)
}

fun ImageView.load(model: Any?) {
    Glide.with(context).load(model).into(this)
}