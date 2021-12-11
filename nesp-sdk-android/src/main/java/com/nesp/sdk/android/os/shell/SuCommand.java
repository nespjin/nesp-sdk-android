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

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Su命令
 * 用法：private ArrayList<String> commnandList;
 * commnandList=new ArrayList<>();
 * <p>
 * if(ShellUtils.checkRootPermission()){
 * commnandList.clear();
 * commnandList.add("reboot -p");
 * ShellUtils.execCommand(commnandList, true);
 * }else {
 * Toast.makeText(this,"未能获取ROOT权限，该功能无法正常使用",Toast.LENGTH_LONG).function();
 * }
 * commandlist:
 * reboot 重启
 * reboot -p 关机
 * reboot recovery　重启到recovery
 * reboot bootloader 重启到bootloader
 *
 * @Team : NESP Technology
 * @Author : Jinzhaolu
 * Email : 1756404649@qq.com
 * @Time : 18-3-7 下午9:06
 */
@Deprecated
public class SuCommand {

    private static final String COMMAND_SU = "su";
    private static final String COMMAND_SH = "sh";
    private static final String COMMAND_EXIT = "exit\n";
    private static final String COMMAND_LINE_END = "\n";
    protected ArrayList<String> commnandList;

    private SuCommand() {
        throw new AssertionError();
    }


    /**
     * 检查是否有ＲＯＯＴ权限
     *
     * @return
     */
    public static boolean isRoot(Context context) {
        try {
            try {
                return run("system/bin/mount -o rw,remount -t rootfs /data");
            } catch (Exception e) {
                Toast.makeText(context, "Root:" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private static boolean run(String command) {
        Process process = null;
        DataOutputStream os = null;
        int r = 1;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            r = process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
        return r == 0;
    }

    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }

    /**
     * @param command 命令
     * @param isRoot  　是否用Ｒｏｏｔ权限
     * @return
     */
    public static CommandResult execCommand(String command, boolean isRoot) {
        return execCommand(new String[]{command}, isRoot, true);
    }

    /**
     * @param commands 命令链表
     * @param isRoot   　是否用Ｒｏｏｔ权限
     * @return
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot) {
        return execCommand(commands == null ? null : commands.toArray(new String[]{}), isRoot, true);
    }

    /**
     * @param commands 命令数组
     * @param isRoot   　是否用Ｒｏｏｔ权限
     * @return
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot) {
        return execCommand(commands, isRoot, true);
    }

    /**
     * @param command         命令
     * @param isRoot          　是否用Ｒｏｏｔ权限
     * @param isNeedResultMsg 　是否需要返回信息
     * @return
     */
    public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(new String[]{command}, isRoot, isNeedResultMsg);
    }

    /**
     * @param commands        命令链表
     * @param isRoot          　是否需要ＲＯＯＴ权限
     * @param isNeedResultMsg 　是否需要返回信息
     * @return
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(commands == null ? null : commands.toArray(new String[]{}), isRoot, isNeedResultMsg);
    }

    /**
     * 执行Ｓｕ命令
     *
     * @param commands        命令数组
     * @param isRoot          　是否需要ＲＯＯＴ权限
     * @param isNeedResultMsg 　是否需要返回信息
     * @return
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;

        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }

                // donnot use os.writeBytes(commmand), avoid chinese charset error
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();

            result = process.waitFor();
            // get command result
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
            }

            if (process != null) {
                process.destroy();
            }
        }
        return new CommandResult(result, successMsg == null ? null : successMsg.toString(), errorMsg == null ? null
                : errorMsg.toString());
    }

    /**
     * 命令返回
     */
    public static class CommandResult {

        /**
         * result of command
         **/
        public int result;
        /**
         * success message of command result
         **/
        public String successMsg;
        /**
         * error message of command result
         **/
        public String errorMsg;

        public CommandResult(int result) {
            this.result = result;
        }

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }

    /**
     * 简单的Ｓｕ命令工具
     * 用法：
     * RunRootCmd("reboot bootloader");
     */
    public static class SuCommandLite {
        public static int RunRootCmd(String command) {
            try {
                Process localProcess = Runtime.getRuntime().exec("su");
                DataOutputStream localDataOutputStream = new DataOutputStream(
                        localProcess.getOutputStream());
                String str = command + "\n";
                localDataOutputStream.writeBytes(str);
                localDataOutputStream.flush();
                localDataOutputStream.writeBytes("exit\n");
                localDataOutputStream.flush();
                localProcess.waitFor();
                return localProcess.exitValue();

            } catch (Exception localException) {
                localException.printStackTrace();
            }
            return 1;
        }
    }
}