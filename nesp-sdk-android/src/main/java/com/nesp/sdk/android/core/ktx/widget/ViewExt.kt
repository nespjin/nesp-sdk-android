package com.nesp.sdk.android.core.ktx.widget

import android.view.View

/**
*
*
* Team: NESP Technology
* @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
* Time: Created 2020/10/11 8:07
* Project: NespAndroidSdk
**/

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}