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

package com.nesp.sdk.android.graphics

import android.graphics.Paint

/**
 *
 * Author: <a href="mailto:1756404649@qq.com">Jack Email:1756404649@qq.com</a>
 * Time: Created 2020/10/16 11:17 AM
 * Project: NespAndroidSdk
 * Description:
 **/
object PaintUtil {
    @JvmStatic
    fun getPaintHeight(paint: Paint): Float {
        val fontMetrics = paint.fontMetrics
        return fontMetrics.descent - fontMetrics.ascent
    }

    @JvmStatic
    fun getPaintWidth(paint: Paint, text: String): Float {
        return paint.measureText(text)
    }
}