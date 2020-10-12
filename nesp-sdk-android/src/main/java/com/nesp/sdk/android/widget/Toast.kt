package com.nesp.sdk.android.widget

import android.content.Context
import android.widget.Toast

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 7:29
 * Project: NespAndroidSdk
 **/
open class Toast(context: Context) : Toast(context) {
    companion object {

        @JvmStatic
        fun showShort(context: Context?, msg: String?) {
            if (context == null || msg == null || msg.isEmpty()) return
            makeText(context.applicationContext, msg, LENGTH_SHORT).show()
        }

        @JvmStatic
        fun showLong(context: Context?, msg: String?) {
            if (context == null || msg == null || msg.isEmpty()) return
            makeText(context.applicationContext, msg, LENGTH_LONG).show()
        }

    }
}