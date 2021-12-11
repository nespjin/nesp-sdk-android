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

package com.nesp.sdk.android.os.shell;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/7/13 20:28
 * @Project Assistant
 **/
@Deprecated
public class ShellScripts {
    /**
     * 静默安装APK
     *
     * @param apkFile APK文件
     * @return 命令列表
     */
    public static List<String> installApk(File apkFile) {
        List<String> list = new ArrayList<>();
        list.add("su");
        list.add("pm install -r " + apkFile.getAbsolutePath());
        return list;
    }

    /**
     * logcat 命令
     * @param level logcat 级别
     * @return logcat 命令
     */
    public static String logcatCommands(level level) {
        switch (level) {
            case I:
                return "logcat *:I";
            case W:
                return "logcat *:W";
            case D:
                return "logcat *:D";
            case E:
                return "logcat *:E";
            case F:
                return "logcat *:F";
            default:
                return null;
        }
    }

    /**
     * logcat级别
     */
    public enum level {
        I, W, D, E, F
    }
}
