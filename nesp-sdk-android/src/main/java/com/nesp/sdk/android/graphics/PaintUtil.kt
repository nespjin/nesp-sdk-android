package com.nesp.sdk.android.graphics

import android.graphics.Paint

/**
 *
 * Author: <a href="mailto:1756404649@qq.com">Jack Email:1756404649@qq.com</a>
 * Time: Created 2020/10/16 11:17 AM
 * Project: NespAndroidSdk
 * Description:
 **/
class PaintUtil {
    companion object {

        fun getPaintHeight(paint: Paint): Float {
            val fontMetrics = paint.fontMetrics
            return fontMetrics.descent - fontMetrics.ascent
        }

        fun getPaintWidth(paint: Paint, text: String): Float {
            return paint.measureText(text)
        }

    }
}