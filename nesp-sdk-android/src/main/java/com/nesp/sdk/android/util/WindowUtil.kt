package com.nesp.sdk.android.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
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

        @JvmStatic
        fun isSoftShowing(activity: Activity): Boolean {
            //获取当前屏幕内容的高度
            val screenHeight = activity.window.decorView.height
            //获取View可见区域的bottom
            val rect = Rect()
            activity.window.decorView.getWindowVisibleDisplayFrame(rect)
            return screenHeight - rect.bottom != 0
        }
    }
}