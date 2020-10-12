package com.nesp.sdk.android.util

import android.content.Context

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 10:11
 Project: NespAndroidSdk
 **/
class WindowUtil {
    companion object {

        @JvmStatic
        fun getStatusBarHeight(context: Context): Int {
            val resources = context.applicationContext.resources
            val identifier = resources.getIdentifier("status_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(identifier)
        }
    }
}