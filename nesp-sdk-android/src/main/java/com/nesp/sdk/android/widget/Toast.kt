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

package com.nesp.sdk.android.widget

import android.content.Context
import android.widget.Toast

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 7:29
 * Project: NespAndroidSdk
 **/
@Deprecated(
    "This is not usable",
    replaceWith = ReplaceWith(
        "ToastUtil",
        imports = ["com.nesp.sdk.android.widget.ToastUtil"]
    )
)
open class Toast(context: Context) : Toast(context) {
    companion object {

        @JvmStatic
        fun showShort(context: Context?, msg: String?) {
            if (context == null || msg == null || msg.isEmpty()) return
            ToastUtil.showShortToast(context, msg)
        }

        @JvmStatic
        fun showLong(context: Context?, msg: String?) {
            if (context == null || msg == null || msg.isEmpty()) return
            ToastUtil.showLongToast(context, msg)
        }

    }
}