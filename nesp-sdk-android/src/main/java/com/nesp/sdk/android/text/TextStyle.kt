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

package com.nesp.sdk.android.text

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.TextView
import androidx.annotation.ColorInt

class TextStyle {

    var textSize: Float = TEXT_SIZE_NONE

    @ColorInt
    var textColor: Int = TEXT_COLOR_NONE
        set(value) {
            if (value != TEXT_COLOR_NONE) {
                field = value
                this.textColorStateList = null
            }
        }

    var textColorStateList: ColorStateList? = null
        set(value) {
            if (value != null) {
                field = value
                this.textColor = TEXT_COLOR_NONE
            }
        }

    constructor(textSize: Float, @ColorInt textColor: Int) {
        this.textSize = textSize
        this.textColor = textColor
        this.textColorStateList = null
    }

    constructor(textSize: Float, textColorStateList: ColorStateList) {
        this.textSize = textSize
        this.textColorStateList = textColorStateList
        this.textColor = TEXT_COLOR_NONE
    }

    fun setToTextView(textView: TextView) {
        if (textSize != TEXT_SIZE_NONE) {
            textView.textSize = textSize
        }

        if (textColor != TEXT_COLOR_NONE) {
            textView.setTextColor(textColor)
        }

        if (textColorStateList != null) {
            textView.setTextColor(textColorStateList)
        }
    }

    companion object {
        const val TEXT_SIZE_NONE = -1F
        const val TEXT_COLOR_NONE = -1
    }
}