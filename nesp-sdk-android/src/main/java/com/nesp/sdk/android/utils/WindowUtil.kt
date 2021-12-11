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

package com.nesp.sdk.android.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect

/**
 *
 *
 * Team: NESP Technology
 * @author <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 10:11
 **/
object WindowUtil {
    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        val resources = context.applicationContext.resources
        val identifier = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(identifier)
    }

    @JvmStatic
    fun isSoftShowing(activity: Activity): Boolean {
        //获取当前屏幕内容的高度
        val screenHeight = activity.window.decorView.height
        //获取View可见区域的bottom
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        return screenHeight - rect.bottom != 0
    }
}