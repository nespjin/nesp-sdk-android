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
import android.util.TypedValue
import androidx.annotation.*
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


fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun Context.getActionBarHeight(): Float {
    var actionBarHeight = 0f
    val tv = TypedValue()
    if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        //            //方法一
        //            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        //
        //            //方法二
        //            int[] attribute = new int[] { android.R.attr.actionBarSize };
        //            TypedArray array = obtainStyledAttributes(tv.resourceId, attribute);
        //            int actionBarHeight1 = array.getDimensionPixelSize(0 /* index */, -1 /* default size */);
        //            array.recycle();

        //方法三
        val actionbarSizeTypedArray = obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        actionBarHeight = actionbarSizeTypedArray.getDimension(0, 0f)
        actionbarSizeTypedArray.recycle()
    }
    return actionBarHeight
}

fun Context.dip2px(@DimenRes dpValueRes: Int): Int {
    return dip2px(resources.getDimension(dpValueRes))
}

fun Context.dip2px(@Dimension dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}
