package com.nesp.sdk.android.core.ktx.widget

import android.widget.TextView
import androidx.core.view.isGone
/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 8:07
 * Project: NespAndroidSdk
 **/


fun TextView.goneIfTextEmpty() {
    this.isGone = this.text.trim().isEmpty()
}