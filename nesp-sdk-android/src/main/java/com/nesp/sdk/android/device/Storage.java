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

package com.nesp.sdk.android.device;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import androidx.annotation.RequiresApi;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-2-7 下午12:34
 **/
public final class Storage {

    private final StatFs mStatFs;

    public Storage(String path) {
        mStatFs = new StatFs(path);
    }

    public static Storage getInternalInstance() {
        return new Storage(Environment.getExternalStorageDirectory().getPath());
    }

    /**
     * 存储块总数量
     *
     * @return 存储块总数量
     */
    public long getBlockCount() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return mStatFs.getBlockCountLong();
        } else {
            return mStatFs.getBlockCount();
        }
    }

    /**
     * 获取块总大小
     *
     * @return 获取块总大小
     */
    public long getBlockSize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return mStatFs.getBlockSizeLong();
        } else {
            return mStatFs.getBlockSize();
        }
    }

    /**
     * 获取可用块数量
     *
     * @return 获取可用块数量
     */
    public long getAvailableBlockCount() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return mStatFs.getAvailableBlocksLong();
        } else {
            return mStatFs.getAvailableBlocks();
        }
    }

    /**
     * 剩余块数量（包括保留块，即应用无法使用的空间）
     *
     * @return 剩余块数量（包括保留块，即应用无法使用的空间）
     */
    public long getFreeBlockCount() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return mStatFs.getFreeBlocksLong();
        } else {
            return mStatFs.getFreeBlocks();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public long getTotalSize() {
        return mStatFs.getTotalBytes();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public long getAvailableSize() {
        return mStatFs.getAvailableBytes();
    }

    public long getTotalBlockSize() {
        return getBlockSize() * getBlockCount();
    }

    public long getTotalAvailableSize() {
        return getBlockSize() * getAvailableBlockCount();
    }

    public long getTotalFreeBlockSize() {
        return getBlockSize() * getFreeBlockCount();
    }

}
