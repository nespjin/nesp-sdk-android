package com.nesp.sdk.android.smooth.app

import android.app.Application

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 7:02
 * Project: NespAndroidSdk
 **/
open class SmoothBaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {

        @Volatile
        private var instance: SmoothBaseApplication? = null

        @JvmStatic
        fun getInstance() = instance

    }

}