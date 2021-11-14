/*
 * Copyright (C) 2021 The NESP Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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