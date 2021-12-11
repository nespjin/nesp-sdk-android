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

package com.nesp.sdk.android.social;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.nesp.sdk.android.app.ApplicationUtil;
import com.nesp.sdk.android.sina.Weibo;
import com.nesp.sdk.android.tencent.qq.QQ;
import com.nesp.sdk.android.utils.ClipboardUtil;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/8/21 21:30
 * @Project NespMovie
 **/
public final class Follow {
    private static final String TAG = "Follow";

    private Follow() {
        //no instance
    }

    public static void weibo(Context context) {
        try {
            if (!ApplicationUtil.isAppInstalled(context, "com.sina.weibo")) {
                new AlertDialog.Builder(context).setPositiveButton("确定", null).setMessage("请先安装微博").create().show();
            } else {
                Weibo.openWeiboUser(context, "3619635672");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void weChat(Context context) {
        try {
            if (!ApplicationUtil.isAppInstalled(context, "com.tencent.mm")) {
                new AlertDialog.Builder(context).setPositiveButton("确定", null).setMessage("请先安装微信").create().show();
            } else {
                ApplicationUtil.startSpecialActivity(context, "com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                ClipboardUtil.copyTextToClipboard(context, "NESP_Technology");
                Toast.makeText(context, "微信公众号ID已复制,请粘贴搜索", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void weChat(Context context,String wechatId) {
        try {
            if (!ApplicationUtil.isAppInstalled(context, "com.tencent.mm")) {
                new AlertDialog.Builder(context).setPositiveButton("确定", null).setMessage("请先安装微信").create().show();
            } else {
                ApplicationUtil.startSpecialActivity(context, "com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                ClipboardUtil.copyTextToClipboard(context, wechatId);
                Toast.makeText(context, "微信号已复制,请粘贴搜索", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void qqGroup(Context context) {
        qqGroup(context, QQ.QQGroupType.NESP_TECHNOLOGY);
    }

    public static void qqGroup(Context context, @QQ.QQGroupType int qqGroupType) {
        try {
            String key = QQ.getQQGroupKey(qqGroupType);
            if (!QQ.joinQQGroup(context, key))
                new AlertDialog.Builder(context).setPositiveButton("确定", null).setMessage("请先安装QQ或Tim").create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
