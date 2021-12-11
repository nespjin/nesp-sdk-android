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

package com.nesp.sdk.android.alipay;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.nesp.sdk.android.app.ApplicationUtil;
import com.nesp.sdk.android.content.IntentUtil;

import java.net.URISyntaxException;

/**
 * 支付宝工具类
 * <p>
 * Team: NESP Technology
 *
 * @author 靳兆鲁
 * Email: 1756404649@qq.com
 * Time: Created 18-6-6 下午3:25
 **/
public final class AlipayUtil {

    private AlipayUtil() {
        //no instance
    }

    /**
     * 支付宝的包名
     */
    public static final String ALIPAY_PKG_NAME = "com.eg.android.AlipayGphone";

    /**
     * 打开支付宝客户端
     *
     * @param context context
     */
    public static Boolean openAlipay(final Context context) {
        if (context == null) return false;
        if (isAlipayNotInstalled(context)) {
            Toast.makeText(context, "请先安装支付宝", Toast.LENGTH_SHORT).show();
            return false;
        }
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final String alipayLauncherActivityClassName = "com.alipay.mobile.quinox.LauncherActivity";
        intent.setComponent(new ComponentName(ALIPAY_PKG_NAME, alipayLauncherActivityClassName));
        context.startActivity(intent);
        return true;
    }

    /**
     * 打开支付宝扫一扫
     *
     * @param context context
     */
    public static boolean openScan(final Context context) {
        if (context == null) return false;
        if (isAlipayNotInstalled(context)) {
            Toast.makeText(context, "请先安装支付宝", Toast.LENGTH_SHORT).show();
            return false;
        }

        Intent intent;
        try {
            intent = Intent.parseUri("alipayqr://platformapi/startapp?saId=10000007",
                    Intent.URI_INTENT_SCHEME);
        } catch (URISyntaxException e) {
            return false;
        }
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        context.startActivity(intent);
        return true;
    }

    /**
     * 打开支付宝付款二维码
     *
     * @param context context
     */
    public static boolean openPayQrCode(final Context context) {
        if (context == null) return false;
        if (isAlipayNotInstalled(context)) {
            Toast.makeText(context, "请先安装支付宝", Toast.LENGTH_SHORT).show();
            return false;
        }

        final Uri uri;
        try {
            uri = Uri.parse("alipays://platformapi/startapp?appId=20000056");
        } catch (Exception e) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
        return true;
    }

    /**
     * 打开支付宝收款二维码
     *
     * @param context context
     * @return 是否打开成功
     */
    public static boolean openCollectionQrCode(final Context context) {
        if (context == null) return false;
        if (isAlipayNotInstalled(context)) {
            Toast.makeText(context, "请先安装支付宝", Toast.LENGTH_SHORT).show();
            return false;
        }

        Uri uri;
        try {
            uri = Uri.parse("alipays://platformapi/startapp?appId=20000123");
        } catch (Exception e) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
        return true;
    }

    /**
     * 打开支付宝名片（个人用户二维码家好友，发红包）
     *
     * @param context context
     * @return 是否打开成功
     */
    public static boolean openVisitingCardQrCode(final Context context) {
        if (context == null) return false;
        if (isAlipayNotInstalled(context)) {
            Toast.makeText(context, "请先安装支付宝", Toast.LENGTH_SHORT).show();
            return false;
        }

        Uri uri;
        try {
            uri = Uri.parse("alipays://platformapi/startapp?appId=20000085");
        } catch (Exception e) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
        return true;
    }

    /**
     * 打开用户收款页面
     *
     * @param context context
     * @return 是否打开成功
     */
    public static boolean openUserCollectionPage(final Context context) {
        if (context == null) return false;
        if (isAlipayNotInstalled(context)) {
            Toast.makeText(context, "请先安装支付宝", Toast.LENGTH_SHORT).show();
            return false;
        }
        //            IntentUtils.openUrlByDefaultBrowser(context, "https://qr.alipay.com/a6x01030q7ls9ptyabh68a6");
        IntentUtil.openUrlByDefaultBrowser(context, "https://qr.alipay.com/FKX065797UZZFAR7KPCTFB?t=1529318965921");
        return true;
    }


    /**
     * 打开制定的二维码
     *
     * @param context context
     * @param qrcode  制定二维码
     * @return 是否打开成功
     */
    public static boolean openQrCode(final Context context, final String qrcode) {
        if (context == null || TextUtils.isEmpty(qrcode)) return false;
        if (isAlipayNotInstalled(context)) {
            Toast.makeText(context, "请先安装支付宝", Toast.LENGTH_SHORT).show();
            return false;
        }

        Uri uri;
        try {
            uri = Uri.parse("alipays://platformapi/startapp?saId=10000007&qrcode=" + qrcode);
        } catch (Exception e) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
        return true;
    }

    public static boolean isAlipayInstalled(final Context context) {
        return !isAlipayNotInstalled(context);
    }

    public static boolean isAlipayNotInstalled(final Context context) {
        if (context == null) return true;
        return !ApplicationUtil.isAppInstalled(context.getApplicationContext(), ALIPAY_PKG_NAME);
    }
}
