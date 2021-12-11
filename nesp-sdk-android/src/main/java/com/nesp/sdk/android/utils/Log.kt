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

package com.nesp.sdk.android.utils

import android.util.Log

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 8:18
 * Project: NespAndroidSdk
 **/
object Log {

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