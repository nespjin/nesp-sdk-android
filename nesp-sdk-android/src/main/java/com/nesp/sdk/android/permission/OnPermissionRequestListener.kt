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

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/5/28 23:07
 * Project: NespAndroidSdkSample
 * Description:
 *
 * Sample below:
 * ```
 * class OnCameraPermissionRequestListener(
 * private val activity: Activity,
 * ) : OnPermissionRequestListener() {
 *
 * override fun onPermissionDenied() {
 * ToastUtils.showShortToast("相机权限被拒绝")
 * AlertDialog.Builder(activity)
 * .setTitle("需要相机权限")
 * .setMessage("相机权限被拒绝")
 * .setPositiveButton("ok") { _, _ ->
 * permissionRequester?.requestPermissions()
 * }
 * .setNegativeButton("cancel", null)
 * .create()
 * .show()
 * }
 *
 * override fun onShouldShowPermissionRational() {
 * ToastUtils.showShortToast("相机权限弹出解释")
 * AlertDialog.Builder(activity)
 * .setTitle("需要相机权限")
 * .setMessage("需要相机权限去拍摄照片")
 * .setPositiveButton("ok") { _, _ ->
 * permissionRequester?.requestPermissions()
 * }
 * .setNegativeButton("cancel", null)
 * .create()
 * .show()
 * }
 *
 * override fun onPermissionNeverAskAgain() {
 * ToastUtils.showShortToast("相机权限不再询问")
 * AlertDialog.Builder(activity)
 * .setTitle("需要相机权限")
 * .setMessage("需要相机权限去拍摄照片,去打开相机权限")
 * .setPositiveButton("ok") { _, _ ->
 * App.getInstance().startAppDetailSettings()
 * }
 * .setNegativeButton("cancel", null)
 * .create()
 * .show()
 * }
 *
 * }
 * ```
 **/
abstract class OnPermissionRequestListener : BaseOnPermissionRequestListener {

    var permissionRequester: AbsPermissionRequester? = null

    override fun onPermissionGranted() {

    }

}