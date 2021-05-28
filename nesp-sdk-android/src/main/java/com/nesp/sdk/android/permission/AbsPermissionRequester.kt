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

package com.nesp.sdk.android.permission

import android.app.Activity
import android.content.pm.PackageManager
import androidx.annotation.CallSuper
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/5/28 23:07
 * Project: NespAndroidSdkSample
 * Description:
 *
 * Sample below:
 * ```
 * class CameraPermissionRequester(
 *  activity: Activity,
 *  doWork: (() -> Unit),
 *  ) : AbsPermissionRequester(
 *  activity,
 *  Manifest.permission.CAMERA,
 *  OnCameraPermissionRequestListener(activity),
 *  doWork
 *  )
 * ```
 **/
abstract class AbsPermissionRequester(
    private val activity: Activity,
    private val permissions: Array<String>,
    private val onPermissionRequestListener: OnPermissionRequestListener?,
    private val doWork: (() -> Unit)?
) : IPermissionRequester {

    constructor(
        activity: Activity,
        permissions: Array<String>,
        onPermissionRequestListener: OnPermissionRequestListener?,
    ) : this(activity, permissions, onPermissionRequestListener, null)

    constructor(
        activity: Activity,
        permissions: Array<String>
    ) : this(activity, permissions, null, null)

    constructor(
        activity: Activity,
        permissions: Array<String>,
        doWork: (() -> Unit)?
    ) : this(activity, permissions, null, doWork)

    constructor(
        activity: Activity,
        permission: String,
        onPermissionRequestListener: OnPermissionRequestListener?,
        doWork: (() -> Unit)?
    ) : this(activity, arrayOf(permission), onPermissionRequestListener, doWork)

    constructor(
        activity: Activity,
        permission: String,
        onPermissionRequestListener: OnPermissionRequestListener?,
    ) : this(activity, arrayOf(permission), onPermissionRequestListener, null)

    constructor(
        activity: Activity,
        permission: String
    ) : this(activity, arrayOf(permission), null, null)


    constructor(
        activity: Activity,
        permission: String,
        doWork: (() -> Unit)?
    ) : this(activity, arrayOf(permission), null, doWork)

    final override var requestCode: Int = -1

    init {
        onPermissionRequestListener?.permissionRequester = this
    }

    override fun setRequestCode(permissionRequesters: MutableSet<AbsPermissionRequester>) {
        requestCode = permissionRequesters.indexOf(this)
    }

    override fun workWithCheckPermission() {
        val permissionsDenied = getPermissionDenied(activity, permissions)

        if (permissionsDenied.isEmpty()) {
            onPermissionGranted()
        } else {
            if (activity.shouldShowRequestPermissionsRationale(permissionsDenied.toTypedArray())) {
                onShouldShowPermissionRational()
            } else {
                requestPermissions()
            }
        }
    }

    @CallSuper
    override fun onPermissionGranted() {
        doWork?.invoke()
        onPermissionRequestListener?.onPermissionGranted()
    }

    @CallSuper
    override fun onPermissionDenied() {
        onPermissionRequestListener?.onPermissionDenied()
    }

    @CallSuper
    override fun onShouldShowPermissionRational() {
        onPermissionRequestListener?.onShouldShowPermissionRational()
    }

    @CallSuper
    override fun onPermissionNeverAskAgain() {
        onPermissionRequestListener?.onPermissionNeverAskAgain()
    }

    override fun requestPermissions() {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == this.requestCode) {
            if (checkPermissionGrantResults(grantResults)) {
                onPermissionGranted()
            } else {
                if (!activity.shouldShowRequestPermissionsRationale(permissions)) {
                    onPermissionNeverAskAgain()
                } else {
                    onPermissionDenied()
                }
            }
        }
    }

    private fun Activity.shouldShowRequestPermissionsRationale(permissions: Array<out String>):
            Boolean {
        for (permission in permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true
            }
        }
        return false
    }

    companion object {

        private const val TAG = "AbsPermissionRequester"

        @JvmStatic
        private fun getPermissionDenied(
            activity: Activity,
            permissions: Array<String>
        ): ArrayList<String> {
            val permissionsDenied = arrayListOf<String>()
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED
                ) permissionsDenied.add(permission)
            }
            return permissionsDenied
        }

        @JvmStatic
        private fun checkPermissionGrantResults(grantResults: IntArray): Boolean {
            if (grantResults.isEmpty()) return false
            for (grantResult in grantResults) {
                if (grantResult != PermissionChecker.PERMISSION_GRANTED) return false
            }
            return true
        }

    }
}