/*
 *
 *   Copyright (c) 2020  NESP Technology Corporation. All rights reserved.
 *
 *   This program is not free software; you can't redistribute it and/or modify it
 *   without the permit of team manager.
 *
 *   Unless required by applicable law or agreed to in writing.
 *
 *   If you have any questions or if you find a bug,
 *   please contact the author by email or ask for Issues.
 *
 *   Author:JinZhaolu <1756404649@qq.com>
 */

package com.nesp.sdk.android.core.ktx.app

import android.app.Activity
import android.view.Window

/**
 *
 *
 * @team NESP Technology
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @time: Created 19-7-26 下午4:07
 * @project FishMovie
 **/


/******************
 * 设置隐藏标题栏
 *
 */
fun Activity.setNoTitleBar() {
    requestWindowFeature(Window.FEATURE_NO_TITLE)//去掉标题栏
}