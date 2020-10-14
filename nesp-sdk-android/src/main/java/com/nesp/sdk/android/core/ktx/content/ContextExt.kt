package com.nesp.sdk.android.core.ktx.content

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.nesp.sdk.android.core.ktx.content.res.getColorCompat
import com.nesp.sdk.android.core.ktx.content.res.getDrawableCompat

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 12:53
 * Project: NespAndroidSdk
 **/

fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable? {
    return resources.getDrawableCompat(id, theme)
}

@ColorInt
fun Context.getColorCompat(@ColorRes id: Int): Int {
    return resources.getColorCompat(id, theme)
}

fun Context.getColorStateListCompat(id: Int): ColorStateList? {
    return ContextCompat.getColorStateList(this, id)
}

