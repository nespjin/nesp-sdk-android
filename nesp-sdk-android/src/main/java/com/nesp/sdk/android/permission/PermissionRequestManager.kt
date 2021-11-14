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

import com.nesp.sdk.android.core.ktx.cast
import java.lang.reflect.Proxy

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/5/28 23:22
 * Project: NespAndroidSdkSample
 * Description:
 **/
class PermissionRequestManager {


    companion object {

        private const val TAG = "PermissionRequestManager"

        @JvmStatic
        fun createHost(executor: IPermissionDelegate): IPermissionDelegate {
            return Proxy.newProxyInstance(
                IPermissionDelegate::class.java.classLoader,
                arrayOf(IPermissionDelegate::class.java),
                PermissionProxyHandler(executor)
            ).cast()
        }

    }
}