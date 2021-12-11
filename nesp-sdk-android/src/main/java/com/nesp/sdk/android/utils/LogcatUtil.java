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

import androidx.annotation.NonNull;

import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 18-6-24 上午12:48
 * @Project Assistant
 **/
@Deprecated
public final class LogcatUtil {

    public static Boolean isStopLogcat = false;

    public static void saveLogCat(@NonNull final String logcatCommand, @NonNull final String savePath) {
        new Thread() {
            @Override
            public void run() {
                Process exec = null;
                try {
                    exec = Runtime.getRuntime().exec(logcatCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final InputStream is = exec.getInputStream();

                FileOutputStream os = null;
                if (!isStopLogcat) {
                    try {
                        //新建一个路径信息
                        os = new FileOutputStream(savePath);
                        int len = 0;
                        byte[] buf = new byte[1024];
                        while (-1 != (len = is.read(buf))) {
                            os.write(buf, 0, len);
                            os.flush();

                        }

                    } catch (Exception e) {
                        Log.d("writelog", "read logcat process failed. message: " + e.getMessage());
                    } finally {
                        if (null != os) {
                            try {
                                os.close();
                                os = null;
                            } catch (IOException e) {
                                // Do nothing
                            }
                        }
                    }
                } else {
                    stop();
                }
            }
        }.start();
    }
}
