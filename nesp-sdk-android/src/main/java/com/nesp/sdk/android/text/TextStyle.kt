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

    }

    companion object {
        const val TEXT_SIZE_NONE = -1F
        const val TEXT_COLOR_NONE = -1
    }
}