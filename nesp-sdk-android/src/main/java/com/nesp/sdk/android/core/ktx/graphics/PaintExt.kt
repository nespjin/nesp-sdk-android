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

package com.nesp.sdk.android.core.ktx.graphics

import android.graphics.Paint
import com.nesp.sdk.android.graphics.PaintUtil

/**
 *
 * Author: <a href="mailto:1756404649@qq.com">Jack Email:1756404649@qq.com</a>
 * Time: Created 2020/10/16 11:15 AM
 * Project: NespAndroidSdk
 * Description:
 **/
fun Paint.getFontHeight(): Float {
    return PaintUtil.getPaintHeight(this)
}

fun Paint.getFontWidth(text: String): Float {
    return PaintUtil.getPaintWidth(this, text)
}