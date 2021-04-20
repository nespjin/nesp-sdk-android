package com.nesp.sdk.android.smooth.app

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.nesp.sdk.android.util.WindowUtil
import com.nesp.sdk.android.widget.Toast

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/10 20:35
 * Project: NespAndroidSdk
 **/
open class SmoothBaseActivity : AppCompatActivity() {

    protected fun showShortToast(msg: String?) {
        Toast.showShort(this, msg)
    }

    protected fun showLongToast(msg: String?) {
        Toast.showLong(this, msg)
    }

    protected fun sendLocalBroadcastReceiverSync(intent: Intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcastSync(intent)
    }

    protected fun sendLocalBroadcastReceiver(intent: Intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    protected fun registerLocalBroadcastReceiver(
        broadcastReceiver: BroadcastReceiver, intentFilter: IntentFilter
    ) {
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)
    }

    protected fun unregisterLocalBroadcastReceiver(broadcastReceiver: BroadcastReceiver) {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

    protected fun getStatusBarHeight(): Int {
        return WindowUtil.getStatusBarHeight(this)
    }

}