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