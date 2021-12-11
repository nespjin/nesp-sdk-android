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

package com.nesp.sdk.android.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.nesp.sdk.android.R;
import com.nesp.sdk.android.app.ApplicationUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/7/19 13:44
 * @Project Assistant
 **/
@Deprecated
public final class UrlUtil {

    private UrlUtil() {
        //no instance
    }

    /**
     * 非直链获取文件名
     *
     * @param url
     * @return
     */
    public static String getFileNameByUrl(String url) {
        String filename = null;
        try {
            URL myURL = new URL(url);
            URLConnection conn = myURL.openConnection();
            conn.connect();
            if (((HttpURLConnection) conn).getResponseCode() == 200) {
                String file = conn.getURL().getFile();
                filename = file.substring(file.lastIndexOf('/') + 1);
                //    		Log.v("111", filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public static String getFileNameByDirectUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static void openUrlByDefaulBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static void openUrlByQQBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//intent.setClassName("com.UCMobile","com.uc.browser.InnerUCMobile");//打开UC浏览器
        intent.setClassName("com.tencent.mtt", "com.tencent.mtt.MainActivity");//打开QQ浏览器
        context.startActivity(intent);

    }

    public static void openUrlByWechat(Context context, String url) {

        if (!ApplicationUtil.isAppInstalled(context, "com.tencent.mm")) {
            Toast.makeText(context, R.string.open_url_by_wechat_toast_no_wechat, Toast.LENGTH_SHORT).show();
        } else {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");//打开QQ浏览器
            context.startActivity(intent);
        }
    }

}
