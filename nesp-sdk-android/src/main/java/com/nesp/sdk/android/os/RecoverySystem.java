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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.nesp.sdk.android.os.shell.SuCommand.execCommand;

import com.nesp.sdk.java.io.FileUtil;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 18-7-3 下午7:02
 * @Project Assistant
 **/
@Deprecated
public class RecoverySystem {
    private static final String TAG = "RecoverySystem";
    private List<String> listCommands = new ArrayList<>();

    private static RecoverySystem instance;

    public static RecoverySystem getInstance() {
        if (instance == null) {
            instance = new RecoverySystem();
        }
        return instance;
    }

    /**
     * 安装ZIP包
     *
     * @param context
     * @param zipFile
     */
    public void installZipPackage(Context context, File zipFile) {
        String filePath = zipFile.getAbsolutePath();
        listCommands.add("su");
        listCommands.add("echo > /cache/recovery/command");
        listCommands.add("echo --update_package=" + filePath + " > /cache/recovery/command");
        listCommands.add("reboot");
        execCommand(listCommands, true);
    }

    /**
     * 格式化cache分区
     *
     * @param context
     */
    public static void wipeCache(Context context) {
        execCommand("su", true);
        if (!new File("/cache/recovery/command").exists())
            FileUtil.createFile("/cache/recovery/", "command");
        execCommand("echo > /cache/recovery/command", true);
        execCommand("echo --wipe_cache > /cache/recovery/command", true);
    }

    /**
     * 格式化Data分区
     *
     * @param context
     */
    public static void wipeData(Context context) {
        execCommand("su", true);
        if (!new File("/cache/recovery/command").exists())
            FileUtil.createFile("/cache/recovery/", "command");
        execCommand("echo > /cache/recovery/command", true);
        execCommand("echo --wipe_data > /cache/recovery/command", true);
    }
}
