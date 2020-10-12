package com.nesp.sdk.android.util

import android.util.Log

/**
*
*
* Team: NESP Technology
* Author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
* Time: Created 2020/10/11 8:18
* Project: NespAndroidSdk
**/
class Log {
    companion object {

        @JvmStatic
        var isEnable = true

        @JvmStatic
        fun v(tag: String?, msg: String) {
            if (isEnable) Log.v(tag, msg)
        }

        @JvmStatic
        fun v(tag: String?, msg: String?, tr: Throwable?) {
            if (isEnable) Log.v(tag, msg, tr)
        }

        @JvmStatic
        fun i(tag: String?, msg: String) {
            if (isEnable) Log.i(tag, msg)
        }

        @JvmStatic
        fun i(tag: String?, msg: String?, tr: Throwable?) {
            if (isEnable) Log.i(tag, msg, tr)
        }

        @JvmStatic
        fun d(tag: String?, msg: String) {
            if (isEnable) Log.d(tag, msg)
        }

        @JvmStatic
        fun d(tag: String?, msg: String?, tr: Throwable?) {
            if (isEnable) Log.d(tag, msg, tr)
        }

        @JvmStatic
        fun w(tag: String?, msg: String) {
            if (isEnable) Log.w(tag, msg)
        }

        @JvmStatic
        fun w(tag: String?, msg: String?, tr: Throwable?) {
            if (isEnable) Log.w(tag, msg, tr)
        }

        @JvmStatic
        fun e(tag: String?, msg: String) {
            if (isEnable) Log.e(tag, msg)
        }

        @JvmStatic
        fun e(tag: String?, msg: String?, tr: Throwable?) {
            if (isEnable) Log.e(tag, msg, tr)
        }

        @JvmStatic
        fun wtf(tag: String?, msg: String) {
            if (isEnable) Log.wtf(tag, msg)
        }

        @JvmStatic
        fun wtf(tag: String?, msg: String?, tr: Throwable?) {
            if (isEnable) Log.wtf(tag, msg, tr)
        }

        @JvmStatic
        fun getStackTraceString(tr: Throwable?): String {
            return Log.getStackTraceString(tr)
        }

    }
}