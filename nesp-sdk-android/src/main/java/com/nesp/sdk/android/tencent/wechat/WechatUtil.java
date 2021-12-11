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

package com.nesp.sdk.android.tencent.wechat;

import static com.nesp.sdk.android.os.shell.SuCommand.isRoot;

import android.content.Context;
import android.widget.Toast;

import com.nesp.sdk.android.os.shell.RootShell;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信工具类
 * <p>
 * 需要ROOT
 *
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 18-6-6 下午3:37
 * @Project Assistant
 **/
public final class WechatUtil {

    private WechatUtil() {
        //no instance
    }

    public static IWXAPI getWXApi(Context context, String appId) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        IWXAPI wxapi = WXAPIFactory.createWXAPI(context, appId, false);
        // 将应用的appId注册到微信
        wxapi.registerApp(appId);
        return wxapi;
    }

    /**
     * 打开微信扫一扫
     */
    public static void openScan(Context context) {
        if (isRoot(context)) {
            RootShell rootShell = RootShell.open();
            rootShell.execute("am start -n com.tencent.mm/com.tencent.mm.plugin.scanner.ui.BaseScanUI");
            rootShell.close();
        } else {
            Toast.makeText(context, "没有ROOT权限,该功能无法使用", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 打开微信付款二维码
     */
    public static void openPayQrCode(Context context) {
        if (isRoot(context)) {
            RootShell rootShell = RootShell.open();
            rootShell.execute("am start -n com.tencent.mm/com.tencent.mm.plugin.offline.ui.WalletOfflineCoinPurseUI");
            rootShell.close();
        } else {
            Toast.makeText(context, "没有ROOT权限,该功能无法使用", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 打开微信收款二维码
     */
    public static void openCollectionQrCode(Context context) {
        if (isRoot(context)) {
            RootShell rootShell = RootShell.open();
            rootShell.execute("am start -n com.tencent.mm/com.tencent.mm.plugin.collect.ui.CollectMainUI");
            rootShell.close();
        } else {
            Toast.makeText(context, "没有ROOT权限,该功能无法使用", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 打开微信名片，二维码，加好友，发红包等
     */
    public static void openVisitingCardQrCode(Context context) {
        if (isRoot(context)) {
            RootShell rootShell = RootShell.open();
            rootShell.execute("am start -n com.tencent.mm/com.tencent.mm.plugin.setting.ui.setting.SelfQRCodeUI");
            rootShell.close();
        } else {
            Toast.makeText(context, "没有ROOT权限,该功能无法使用", Toast.LENGTH_LONG).show();
        }
    }
}
