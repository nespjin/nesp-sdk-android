package com.nesp.sdk.android.app

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.nesp.sdk.android.app.utils.DialogUtil
import com.nesp.sdk.android.concurrent.ThreadManager
import com.nesp.sdk.android.widget.ToastUtil
import java.util.concurrent.Executor

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/12 10:04 组件
 **/
interface IComponent {
    val act: Activity?
    val ctx: Context

    private fun runOnUIThread(runnable: Runnable) {
        ThreadManager.getInstance().runOnUIThread(runnable)
    }

    fun showShortToast(message: String) {
        runOnUIThread {
            ToastUtil.showTopShortToast(ctx, message)
        }
    }

    fun showLongToast(message: String) {
        runOnUIThread {
            ToastUtil.showTopLongToast(ctx, message)
        }
    }

    fun showSuccessTipDialog(msg: String) {
        runOnUIThread {
            DialogUtil.showSuccessTipDialog(act, msg)
        }
    }

    fun showWarningTipDialog(msg: String) {
        runOnUIThread {
            DialogUtil.showWarningTipDialog(act, msg)
        }
    }

    fun showErrorTipDialog(msg: String) {
        runOnUIThread {
            DialogUtil.showErrorTipDialog(act, msg)
        }
    }

    /**
     * Must run on ui thread
     */
    fun showLoadingDialog(msg: String): TipDialog? {
        return DialogUtil.showLoadingDialog(act, msg)
    }

    fun registerLocalBroadcastReceiver(
        receiver: BroadcastReceiver,
        filter: IntentFilter,
    ) {
        LocalBroadcastManager.getInstance(ctx).registerReceiver(receiver, filter)
    }

    fun unregisterLocalBroadcastReceiver(receiver: BroadcastReceiver) {
        LocalBroadcastManager.getInstance(ctx).unregisterReceiver(receiver)
    }

    fun sendLocalBroadcast(intent: Intent) {
        LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent)
    }

    fun getMainExecutorCompat(): Executor {
        return ThreadManager.getInstance().mainExecutor
    }

    companion object {
        private const val TAG = "IComponent"
    }
}