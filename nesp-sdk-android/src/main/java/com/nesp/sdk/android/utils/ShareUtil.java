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
import android.os.Parcelable;

import com.nesp.sdk.java.text.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/7/27 9:40
 * @Project Assistant
 **/
public final class ShareUtil {

    private ShareUtil() {
        //no instance
    }

    /**
     * 分享多个文件
     */
    public static void shareFiles(Context context, File[] files) {
        if (context == null || files == null || files.length == 0) return;
        final List<Uri> uris = new ArrayList<>();
        for (final File file : files) {
            if (file != null) uris.add(Uri.fromFile(file));
        }
        //分享文件
        final Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);//发送多个文件
        intent.setType("*/*");//多个文件格式
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, (ArrayList<? extends Parcelable>) uris);//Intent.EXTRA_STREAM同于传输文件流
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 分享文件
     */
    public static void shareFile(Context context, File file) {
        if (context == null || file == null) return;
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        final Intent chooser = Intent.createChooser(intent, "分享");
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(chooser);
    }

    public static void shareText(Context context, String shareText, String shareActivityTitle) {
        if (context == null || TextUtil.isEmpty(shareText)) return;
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        final Intent chooser = Intent.createChooser(intent, shareActivityTitle);
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(chooser);
    }
}
