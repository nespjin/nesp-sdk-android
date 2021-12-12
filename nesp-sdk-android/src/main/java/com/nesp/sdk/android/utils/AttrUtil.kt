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

package com.nesp.sdk.android.utils

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.nesp.sdk.kotlin.lang.cast

/**
 *
 * @@author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/13 4:34 PM
 * Project: NespAndroidSdk
 * Description:
 **/
object AttrUtil {

    @JvmStatic
    fun getDimensionAttrValue(context: Context, id: Int): Float {
        val outTypeValue = getAttrOutTypeValue(context, id)
        val windowManager =
            context.getSystemService(Context.WINDOW_SERVICE).cast<WindowManager>()
        val outDisplayMetrics = DisplayMetrics()

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && context is AppCompatActivity) {
//            context.display!!.getRealMetrics(outDisplayMetrics)
//        } else {
//            windowManager.defaultDisplay!!.getRealMetrics(outDisplayMetrics)
//        }
        // TODO: Resolve for R
        windowManager.defaultDisplay!!.getRealMetrics(outDisplayMetrics)
        return outTypeValue.getDimension(outDisplayMetrics)
    }

    @JvmStatic
    fun getAttrOutTypeValue(context: Context, id: Int): TypedValue {
        val outTypeValue = TypedValue()
        context.theme.resolveAttribute(id, outTypeValue, true)
        return outTypeValue
    }

}