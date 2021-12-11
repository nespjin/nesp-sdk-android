/*
 *
 *   Copyright (c) 2020  NESP Technology Corporation. All rights reserved.
 *
 *   This program is not free software; you can't redistribute it and/or modify it
 *   without the permit of team manager.
 *
 *   Unless required by applicable law or agreed to in writing.
 *
 *   If you have any questions or if you find a bug,
 *   please contact the author by email or ask for Issues.
 *
 *   Author:JinZhaolu <1756404649@qq.com>
 */

package com.nesp.sdk.android.app

import android.content.Context
import android.os.Bundle
import com.nesp.sdk.android.widget.Toast

/**
 *
 *
 * @team NESP Technology
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @time: Created 19-10-20 下午5:38
 **/
open class AppCompatActivity : androidx.appcompat.app.AppCompatActivity() {

    private var context: Context = this

    @JvmField
    protected var isActivityRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isActivityRunning = false
    }

    override fun onStart() {
        super.onStart()
        isActivityRunning = false
    }

    override fun onResume() {
        super.onResume()
        isActivityRunning = true
    }

    override fun onPause() {
        isActivityRunning = false
        super.onPause()
    }

    override fun onStop() {
        isActivityRunning = false
        super.onStop()
    }

    override fun onDestroy() {
        isActivityRunning = false
        super.onDestroy()
    }

    protected fun showShortToast(msg: String) {
        Toast.showShort(context, msg)
    }

    protected fun showLongToast(msg: String) {
        Toast.showLong(context, msg)
    }

}