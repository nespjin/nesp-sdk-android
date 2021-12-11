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

package com.nesp.sdk.android.core.ktx.widget

import android.annotation.SuppressLint
import android.util.TypedValue
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nesp.sdk.kotlin.jvm.reflect.getFieldValue
import com.nesp.sdk.kotlin.jvm.reflect.setFieldValue

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2021/4/21 1:00
 * Project: NespAndroidSdkSample
 **/
@SuppressLint("RestrictedApi")
fun BottomNavigationView.closeMenuItemAnimation() {
    val mMenuView = this.getChildAt(0) as BottomNavigationMenuView
    for (i in 0 until mMenuView.childCount) {
        val button = mMenuView.getChildAt(i) as BottomNavigationItemView
        val mLargeLabel: TextView? =
            button.javaClass.getFieldValue(button, "largeLabel") as TextView?
        val mSmallLabel: TextView? =
            button.javaClass.getFieldValue(button, "smallLabel") as TextView?
        val mSmallLabelSize = mSmallLabel!!.textSize
        button.javaClass.setFieldValue(button, "shiftAmount", 0f)
        button.javaClass.setFieldValue(button, "scaleUpFactor", 1f)
        button.javaClass.setFieldValue(button, "scaleDownFactor", 1f)
        mLargeLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize)
    }
    mMenuView.updateMenuView()
}
