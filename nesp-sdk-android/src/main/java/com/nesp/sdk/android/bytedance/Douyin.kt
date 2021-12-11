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

package com.nesp.sdk.android.bytedance

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.nesp.sdk.android.app.ApplicationUtil

/**
 *
 *
 * @team NESP Technology
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @time: Created 2020-02-02 22:36
 **/
class Douyin {
    companion object {

        @JvmStatic
        fun openNespAccount(context: Context) {
            openFriend(context, "snssdk1128://user/profile/89946477381")
        }

        @JvmStatic
        fun openFriend(context: Context, url: String) {
            if (!ApplicationUtil.isAppInstalled(context, "com.ss.android.ugc.aweme")) {
                Toast.makeText(context, "请先安装抖音短视频！", Toast.LENGTH_SHORT).show()
                return
            }

            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
//            intent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
            context.startActivity(intent)
        }

    }
}