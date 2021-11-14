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

import androidx.lifecycle.LifecycleOwner
import com.nesp.sdk.android.lifecycle.DefaultLifecycleObserver

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/5/28 23:21
 * Project: NespAndroidSdkSample
 * Description:
 **/
class PermissionRequestHost : DefaultLifecycleObserver, IPermissionRequestHost,
    IPermissionDelegate {

    private val permissionRequesters = mutableSetOf<AbsPermissionRequester>()

    override fun registerPermissionRequester(permissionRequester: AbsPermissionRequester) {
        permissionRequesters.add(permissionRequester)
        permissionRequester.setRequestCode(this.permissionRequesters)
    }

    override fun registerPermissionRequesters(permissionRequesters: ArrayList<AbsPermissionRequester>) {
        permissionRequesters.addAll(permissionRequesters)
        for (permissionRequester in permissionRequesters) {
            permissionRequester.setRequestCode(this.permissionRequesters)
        }
    }

    override fun unregisterPermissionRequester(permissionRequester: AbsPermissionRequester) {
        permissionRequesters.remove(permissionRequester)
    }

    override fun unregisterPermissionRequesters(permissionRequesters: ArrayList<AbsPermissionRequester>) {
        permissionRequesters.removeAll(permissionRequesters)
    }

    override fun clearPermissionRequesters() {
        permissionRequesters.clear()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        for (permissionRequester in permissionRequesters) {
            permissionRequester.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        }
    }

    override fun onCreate(source: LifecycleOwner) {

    }

    override fun onStart(source: LifecycleOwner) {

    }

    override fun onResume(source: LifecycleOwner) {

    }

    override fun onPause(source: LifecycleOwner) {

    }

    override fun onStop(source: LifecycleOwner) {

    }

    override fun onDestroy(source: LifecycleOwner) {
        permissionRequesters.clear()
    }

    override fun onAny(source: LifecycleOwner) {

    }

}