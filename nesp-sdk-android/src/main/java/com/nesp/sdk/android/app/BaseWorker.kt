package com.nesp.sdk.android.app

import android.app.Activity
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/13 8:49
 **/
@Deprecated("Do not recommend this class")
abstract class BaseWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams), IComponent {

    override val act: Activity? = null
    override val ctx: Context = applicationContext

    companion object {
        private const val TAG = "AppBaseWorker"
    }

}