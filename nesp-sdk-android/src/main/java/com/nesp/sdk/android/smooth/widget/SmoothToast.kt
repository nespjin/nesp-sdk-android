package com.nesp.sdk.android.smooth.widget

import android.content.Context
import com.nesp.sdk.android.smooth.app.SmoothBaseApplication
import com.nesp.sdk.android.widget.Toast

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 7:42
 * Project: NespAndroidSdk
 **/
class SmoothToast(context: Context) : Toast(context) {
    companion object {

        @JvmStatic
        fun showShort(msg: String?) {
            val smoothBaseApplication = SmoothBaseApplication.getInstance()
            showShort(smoothBaseApplication, msg)
        }

        @JvmStatic
        fun showLong(msg: String?) {
            val smoothBaseApplication = SmoothBaseApplication.getInstance()
            showLong(smoothBaseApplication, msg)
        }
    }
}