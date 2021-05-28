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
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
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

