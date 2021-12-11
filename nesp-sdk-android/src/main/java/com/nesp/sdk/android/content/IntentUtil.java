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

package com.nesp.sdk.android.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.nesp.sdk.java.io.FileUtil;

import java.io.File;
import java.util.Locale;

/**
 * Team: NESP Technology
 *
 * @author 靳兆鲁
 * Email: 1756404649@qq.com
 * Time: Created 18-6-4 下午4:14
 **/
public final class IntentUtil {

    private IntentUtil() {
        //no instance
    }

    /**
     * 使用默认浏览器打开链接
     *
     * @param context context
     * @param url     url
     */
    public static void openUrlByDefaultBrowser(Context context, String url) {
        if (context == null || TextUtils.isEmpty(url)) return;
        Intent intent = new Intent();
        //Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    /**
     * 使用系统浏览器打开链接
     *
     * @param context context
     * @param url     url
     */
    public static void openUrlBySystemBrowser(Context context, String url) {
        if (context == null || TextUtils.isEmpty(url)) return;
        Intent intent = new Intent();
        //Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        context.startActivity(intent);
    }


    public static Intent openFile(String filePath) {
        if (filePath == null) return null;
        File file = new File(filePath);
        if (!file.exists()) return null;

        /* 依扩展名的类型决定MimeType */
        switch (FileUtil.extensionName(file).toLowerCase(Locale.getDefault())/* 取得扩展名 */) {
            case "m4a":
            case "mp3":
            case "mid":
            case "xmf":
            case "ogg":
            case "wav":
                return IntentFactory.buildOpenAudioFileIntent(filePath);
            case "3gp":
            case "mp4":
                return IntentFactory.buildOpenVideoFileIntent(filePath);
            case "jpg":
            case "gif":
            case "png":
            case "jpeg":
            case "bmp":
                return IntentFactory.buildOpenImageFileIntent(filePath);
            case "apk":
                return IntentFactory.buildOpenApkFileIntent(filePath);
            case "ppt":
                return IntentFactory.buildOpenPptFileIntent(filePath);
            case "xls":
                return IntentFactory.buildOpenExcelFileIntent(filePath);
            case "doc":
                return IntentFactory.buildOpenWordFileIntent(filePath);
            case "pdf":
                return IntentFactory.buildOpenPdfFileIntent(filePath);
            case "chm":
                return IntentFactory.buildOpenChmFileIntent(filePath);
            case "txt":
                return IntentFactory.buildOpenTextFileIntent(filePath, false);
            default:
                return IntentFactory.buildOpenAllFileIntent(filePath);
        }
    }

}
