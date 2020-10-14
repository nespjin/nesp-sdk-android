package com.nesp.sdk.android.core.ktx.content.res

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import com.nesp.sdk.android.smooth.app.SmoothBaseApplication


/**
 *
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/14 9:42 AM
 * Project: NespAndroidSdk
 * Description:
 **/
@ColorInt
fun Resources.getColorCompat(@ColorRes id: Int, theme: Resources.Theme? = null): Int {
    return ResourcesCompat.getColor(this, id, theme)
}

fun Resources.getDrawableCompat(@DrawableRes id: Int, theme: Resources.Theme? = null): Drawable? {
    return ResourcesCompat.getDrawable(this, id, theme)
}

fun Resources.getColorStateListCompat(context: Context, id: Int): ColorStateList? {
    return AppCompatResources.getColorStateList(context, id)
}

fun Resources.getColorStateListCompat(id: Int): ColorStateList? {
    val application = SmoothBaseApplication.getInstance()
    return AppCompatResources.getColorStateList(application, id)
}