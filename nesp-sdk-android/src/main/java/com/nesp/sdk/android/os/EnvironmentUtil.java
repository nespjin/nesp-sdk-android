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

package com.nesp.sdk.android.os;

import android.content.Context;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.DIRECTORY_MOVIES;
import static android.os.Environment.DIRECTORY_MUSIC;
import static android.os.Environment.DIRECTORY_PICTURES;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/7/30 11:45
 **/
@Deprecated
public final class EnvironmentUtil {

    private EnvironmentUtil() {
        //no instance
    }

    ///////////////////////////////////////////////////////////////////////////
    // 外部存储(External Storage)
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 外部存储空间中的公共目录
     * <p>
     * /storage/emulated/0/
     */
    public static final String extDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * /storage/emulated/0/Music
     */
    public static final String extMusicDir = android.os.Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC).getAbsolutePath();

    /**
     * /storage/emulated/0/Movies
     */
    public static final String extMovieDir = android.os.Environment.getExternalStoragePublicDirectory(DIRECTORY_MOVIES).getAbsolutePath();

    /**
     * /storage/emulated/0/Picture
     */
    public static final String extPictureDir = android.os.Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getAbsolutePath();

    /**
     * /storage/emulated/0/Download
     */
    public static final String extDownloadDir = android.os.Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getAbsolutePath();


    ///////////////////////////////////////////////////////////////////////////
    // 私有目录
    ///////////////////////////////////////////////////////////////////////////

    /**
     * /storage/emulated/0/Android/data/应用包名/files
     * 扩展： getExternalFilesDir是手机中设置 → 应用 → 具体应用详情→ 清除数据 的操作对象
     */
    public static String extPrivateFilesDir(Context context) {
        return context.getApplicationContext().getExternalFilesDir("").getAbsolutePath();
    }

    /**
     * /storage/emulated/0/Android/data/应用包名/cache
     * 扩展: getExternalCacheDir是手机中设置 → 应用 → 具体应用详情→ 清除缓存的操作对象
     */
    public static String extPrivateCacheDir(Context context) {
        return context.getExternalCacheDir().getAbsolutePath() + "/";
    }

    ///////////////////////////////////////////////////////////////////////////
    // 内部存储(Internal Storage）
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 内部存储是App的私有目录，当一个应用卸载之后，内部存储中的这些文件也被删除。Shared Preferences和SQLite数据库文件都是存储在内部存储空间上的。
     * <p>
     * -context.getFileDir()
     * <p>
     * 路径：(data/data/应用包名/files),
     * 不过经实际测试(华为、小米手机等)，getFileDir实际路径为: /data/sdk.android.user/0/ 应用包名/files
     */
    public static String internalFileDir(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * 应用程序的缓存目录，该目录内的文件在设备内存不足时会优先被删除掉，所以存放在这里的文件是没有任何保障的，可能会随时丢掉。
     * 路径：(data/data/应用包名/cache),不过经实际测试(华为、小米手机等)，getCacheDir的手机路径为: /data//data/sdk.android.user/0/应用包名/cache
     */
    public static String internalCacheDir(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }
}
