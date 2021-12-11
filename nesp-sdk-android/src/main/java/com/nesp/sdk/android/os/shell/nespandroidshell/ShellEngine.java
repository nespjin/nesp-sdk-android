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

package com.nesp.sdk.android.os.shell.nespandroidshell;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @Team: NESP Technology
 * @Author: 靳兆鲁
 * Email: 1756404649@qq.com
 * @Time: Created 18-11-25 下午2:21
 * @Project WriteDemo
 **/
@Deprecated
public class ShellEngine {

    private static final String TAG = "ShellEngine";
    private static final String COMMAND_SU = "su";
    private static final String COMMAND_SH = "sh";
    private static final String COMMAND_EXIT = "exit\n";
    private static final String COMMAND_LINE_END = "\n";

    private BufferedReader successResult = null;
    private BufferedReader errorResult = null;
    private StringBuilder successMsg = null;
    private StringBuilder errorMsg = null;
    private Process process = null;
    private DataOutputStream os = null;
    private OnShellEngineRunListener onShellEngineRunListener;

    @SuppressLint("HandlerLeak")
    Handler handlerShellRun = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//               Success
                case 0:
                    onShellEngineRunListener.onRun((String) msg.obj);
                    Log.e(TAG, "++++++++++ShellEngine.handleMessage: CallBack  Success++++++" + msg.obj);
                    break;
//              Failed
                case 1:
                    onShellEngineRunListener.OnError((String) msg.obj);
                    Log.e(TAG, "++++++++++ShellEngine.handleMessage: CallBack  Error++++++" + msg.obj);
                    break;
            }
        }
    };

    public void stopExec() throws Exception {
        this.os.writeBytes(COMMAND_EXIT);
        this.os.flush();

        if (os != null) {
            os.close();
        }
        if (successResult != null) {
            successResult.close();
        }
        if (errorResult != null) {
            errorResult.close();
        }

        if (process != null) {
            process.destroy();
        }
        Log.e(TAG, "++++++ShellEngine.stopExec: Stop++++");
    }


    public void execCommand(String command, OnShellEngineRunListener onShellEngineRunListener) {
        if (this.onShellEngineRunListener == null)
            this.onShellEngineRunListener = onShellEngineRunListener;

        new Thread(() -> {
            Looper.prepare();
            Log.e(TAG, "++++++++++ShellEngine.execCommand: Start Shell++++++");

            int result = -1;

            try {
//                if (os != null) {
//                    os.write(command.getBytes());
//                    os.writeBytes(COMMAND_LINE_END);
//                    os.flush();
//                }else {
                process = Runtime.getRuntime().exec("sh");
                os = new DataOutputStream(process.getOutputStream());

                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.writeBytes(COMMAND_EXIT);
                os.flush();
//                }

//                process.waitFor();

                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                    s = s + "\n";
                    Message messageOnRun = new Message();
                    messageOnRun.what = 0;
                    messageOnRun.obj = s;
                    handlerShellRun.sendMessage(messageOnRun);
                    Log.e(TAG, "++++++++++ShellEngine.execCommand: Success++++++" + s);
                }

                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                    s = s + "\n";
                    Message messageOnRunError = new Message();
                    messageOnRunError.what = 1;
                    messageOnRunError.obj = s;
                    handlerShellRun.sendMessage(messageOnRunError);
                    Log.e(TAG, "++++++++++ShellEngine.execCommand: Error++++++" + s);

                }
            } catch (IOException e) {
                e.printStackTrace();
                Message messageOnRunError2 = new Message();
                messageOnRunError2.what = 1;
                messageOnRunError2.obj = e.getMessage();
                handlerShellRun.sendMessage(messageOnRunError2);
                Log.e(TAG, "++++++++++ShellEngine.execCommand: IOException++++++" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                Message messageOnRunError3 = new Message();
                messageOnRunError3.what = 1;
                messageOnRunError3.obj = e.getMessage();
                handlerShellRun.sendMessage(messageOnRunError3);
                Log.e(TAG, "++++++++++ShellEngine.execCommand: Exception++++++" + e.getMessage());
            } finally {
                Log.e(TAG, "++++++++++ShellEngine.execCommand: Shell End++++++");

                try {
                    if (os != null) {
                        os.close();
                    }
                    if (successResult != null) {
                        successResult.close();
                    }
                    if (errorResult != null) {
                        errorResult.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Message messageOnRunError4 = new Message();
                    messageOnRunError4.what = 1;
                    messageOnRunError4.obj = e.getMessage();
                    handlerShellRun.sendMessage(messageOnRunError4);
                }

                if (process != null) {
                    process.destroy();
                }
            }
            Looper.loop();
        }).start();
    }

    public Process getProcess() {
        return process;
    }

    public DataOutputStream getOs() {
        return os;
    }
}
