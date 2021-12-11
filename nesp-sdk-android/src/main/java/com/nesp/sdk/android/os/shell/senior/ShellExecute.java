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

package com.nesp.sdk.android.os.shell.senior;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.nesp.sdk.android.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static com.nesp.sdk.android.os.shell.SuCommand.isRoot;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 2018/8/15 2:47
 * @Project Assistant
 **/
@Deprecated
public final class ShellExecute {

    private static final String TAG = "ShellExecute";
    private static boolean haveRoot = false;

    /**
     * 判断机器Android是否已经root，即是否获取root权限
     */
    public static boolean haveRoot(Context context) {
        return isRoot(context);
    }

    /**
     * 执行命令并且输出结果
     */
    public static String execShellCmd(Context context, String cmd, boolean root) {
        StringBuilder result = new StringBuilder();
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        Process process;
        try {
            if (root) {
                if (haveRoot(context)) {
                    process = Runtime.getRuntime().exec("su");
                } else {
                    process = Runtime.getRuntime().exec("");
                    Toast.makeText(context, context.getString(R.string.no_root), Toast.LENGTH_SHORT).show();
                }
            } else
                process = Runtime.getRuntime().exec("");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataInputStream = new DataInputStream(process.getInputStream());

            Log.i(TAG, "ShellExecute.execShellCmd: execCmd " + cmd);

            dataOutputStream.writeBytes(cmd + "\n");
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            String line;
            while ((line = dataInputStream.readLine()) != null) {
                Log.d(TAG, "ShellExecute.execShellCmd: shell result" + line);
                result.append(line);
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    /**
     * 执行命令但不关注结果输出
     */
    public static int execCmdSilent(Context context, String cmd, boolean root) {
        int result = -1;
        DataOutputStream dataOutputStream = null;
        Process process;
        try {
            if (root) {
                if (haveRoot(context)) {
                    process = Runtime.getRuntime().exec("su");
                } else {
                    process = Runtime.getRuntime().exec("");
                    Toast.makeText(context, context.getString(R.string.no_root), Toast.LENGTH_SHORT).show();
                }
            } else
                process = Runtime.getRuntime().exec("");

            Log.i(TAG, "ShellExecute.execCmdSilent: cmd" + cmd);

            dataOutputStream.writeBytes(cmd + "\n");
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            result = process.exitValue();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
