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
import android.util.TypedValue

/**
 *
 *
 * Team: NESP Technology
 * @author <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 16:44
 **/
object DisplayUtil {

    /**
     * sp转dp
     *
     * @param context context
     * @param spValue spValue
     * @return dp
     */
    @JvmStatic
    fun sp2dp(context: Context, spValue: Float) = px2dp(context, sp2px(context, spValue).toFloat())

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dpValue
     * @return px
     */
    @JvmStatic
    fun dp2px(context: Context, dpValue: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dpValue, context.resources.displayMetrics
    ).toInt()

    @Deprecated(
        "Use dp2px",
        ReplaceWith("dp2px(context, dpValue)")
    )
    fun dp2px2(context: Context, dpValue: Float) =
        dpValue * context.resources.displayMetrics.density + 0.5F

    /**
     * sp转px
     *
     * @param context context
     * @param spValue spValue
     * @return px
     */
    @JvmStatic
    fun sp2px(context: Context, spValue: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, spValue, context.resources.displayMetrics
    ).toInt()


    /**
     * px转dp
     *
     * @param context context
     * @param pxValue pxValue
     * @return dp
     */
    @JvmStatic
    fun px2dp(context: Context, pxValue: Float) = pxValue / context.resources.displayMetrics.density


    @Deprecated(
        "Use px2dp",
        ReplaceWith("px2dp(context, dpValue)")
    )
    fun px2dp2(context: Context, pxValue: Float) =
        (pxValue / context.resources.displayMetrics.density + 0.5F).toInt()

    /**
     * px转sp
     *
     * @param context context
     * @param pxValue pxValue
     * @return sp
     */
    @JvmStatic
    fun px2sp(context: Context, pxValue: Float) =
        pxValue / context.resources.displayMetrics.scaledDensity

}