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

package com.nesp.sdk.android.smooth.app

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.nesp.sdk.android.utils.WindowUtil
import com.nesp.sdk.android.widget.Toast

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
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