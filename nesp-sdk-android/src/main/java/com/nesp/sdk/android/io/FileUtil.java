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

package com.nesp.sdk.android.io;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.nesp.sdk.java.text.TextUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Team : NESP Technology
 * @Author : nesp
 * Email : 1756404649@qq.com
 * @Time : 18-4-4 下午7:13
 */
public final class FileUtil {

    private static final String TAG = "FileUtil";

    private FileUtil() {
        //no instance
    }

    /**
     * 打开指目录
     *
     * @param context context
     * @param dirPath dirPath
     */
    public static void openSpecialDirectory(final Context context, final String dirPath) {
        if (context == null || TextUtil.isEmpty(dirPath)) return;
        //        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.parse(dirPath), "*/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//        startActivity(Intent.createChooser(intent, "选择浏览工具"));
    }

    public static String readFileToString(Context context, Uri uri) throws IOException {
        if (context == null || uri == null) return "";
        context = context.getApplicationContext();
        final StringBuilder content = new StringBuilder();

        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        if (inputStream != null) {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            while (line != null) {
                content.append(line);
                line = bufferedReader.readLine();
            }
        }
        return content.toString();
    }

}