/*
 *
 *   Copyright (c) 2021  NESP Technology Corporation. All rights reserved.
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

package com.nesp.sdk.android.device;

import android.content.Context;
import android.os.Environment;

import com.nesp.sdk.java.util.StorageSizeUtil;

import org.apache.commons.io.FileUtils;

import java.io.IOException;

/**
 * Team: NESP Technology
 *
 * @author 靳兆鲁
 * Email: 1756404649@qq.com
 * Time: Created 2018/8/22 12:58
 **/
public final class StorageUtil {

    private StorageUtil() {
        //no instance
    }

    /**
     * 获取APP缓存大小
     *
     * @param context context
     * @return APP缓存大小
     */
    public static String totalCacheSize(Context context) {
        long cacheSize = FileUtils.sizeOfDirectory(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += FileUtils.sizeOfDirectory(context.getExternalCacheDir());
        }
        return StorageSizeUtil.formatStorageSize(cacheSize);
    }

    /**
     * 清除缓存
     *
     * @param context context
     */
    public static void clearCache(Context context) {
        try {
            FileUtils.deleteDirectory(context.getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                FileUtils.deleteDirectory(context.getExternalCacheDir());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
