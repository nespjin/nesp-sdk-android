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

package com.nesp.sdk.android.util

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.cast

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/10/11 16:44
 * Project: NespAndroidSdk
 **/
class DisplayUtil {
    companion object {

        fun sp2dp(context: Context, spValue: Float): Float {
            return px2dp(context, sp2px(context, spValue).toFloat())
        }


        /**
         * dp转px
         *
         * @param context
         * @param dpValue
         * @return
         */
        fun dp2px(context: Context, dpValue: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                context.resources.displayMetrics
            ).toInt()
        }

        /**
         * sp转px
         *
         * @param context
         * @param spValue
         * @return
         */
        fun sp2px(context: Context, spValue: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                spValue,
                context.resources.displayMetrics
            )
                .toInt()
        }


        /**
         * px转dp
         *
         * @param context
         * @param pxValue
         * @return
         */
        fun px2dp(context: Context, pxValue: Float): Float {
            val scale = context.resources.displayMetrics.density
            return pxValue / scale
        }


        /**
         * px转sp
         *
         * @param context
         * @param pxValue
         * @return
         */
        fun px2sp(context: Context, pxValue: Float): Float {
            return pxValue / context.resources.displayMetrics.scaledDensity
        }

    }
}