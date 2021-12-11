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
import android.widget.Toast;

import com.nesp.sdk.android.R;

import java.util.ArrayList;

import static com.nesp.sdk.android.os.shell.SuCommand.checkRootPermission;
import static com.nesp.sdk.android.os.shell.SuCommand.execCommand;

/**
 * @Team : NESP Technology
 * @Author : nesp
 * Email : 1756404649@qq.com
 * @Time : 18-4-4 下午8:18
 */
@Deprecated
public class PermissionsManager {

    private static ArrayList<String> commandList = new ArrayList<>();

    public static void getRootPermission(Context context) {
        String apkPath = context.getApplicationContext().getPackageResourcePath();
        if (checkRootPermission()) {
            commandList.clear();
            commandList.add("su chmod 777 " + apkPath);
            execCommand(commandList, true);
        } else {
            Toast.makeText(context, R.string.no_root, Toast.LENGTH_LONG).show();
        }
    }
}