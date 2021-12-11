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

package com.nesp.sdk.android.app.launcher;

import android.content.Context;
import android.content.Intent;

/**
 * @Team : NESP Technology
 * @Author : nesp
 * Email : 1756404649@qq.com
 * @Time : 18-4-19 上午10:06
 */
public final class ShortcutUtil {
    private ShortcutUtil() {
        //no instance
    }

    /**
     * 创建快捷方式
     * @param name name
     * @param icon icon
     * @param context context
     * @param packageName packageName
     * @param classNaem classNaem
     */
    public static void installShortCut(String name, int icon, Context context, String packageName, String classNaem) {

        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        shortcut.putExtra("duplicate", false); // 不允许重复创建

        // 快捷方式的图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(context, icon);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        Intent actionIntent = new Intent();
        //                actionIntent.setComponent(new ComponentName("com.nesp.assistant", "com.nesp.assistant.ui.activities.WifiActivity"));
        actionIntent.setClassName(packageName, classNaem);
        actionIntent.setAction("android.intent.action.MAIN");// 桌面图标和应用绑定，卸载应用后系统会同时自动删除图标
        actionIntent.addCategory("android.intent.category.LAUNCHER");// 桌面图标和应用绑定，卸载应用后系统会同时自动删除图标
        actionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        actionIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        actionIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);

        context.sendBroadcast(shortcut);
    }

    /**
     * 删除桌面快捷方式
     * @param context context
     * @param shortcutName
     * 快捷方式名
     * @param actionIntent
     * 快捷方式操作，也就是上面创建的Intent
     * @param isDuplicate
     * 为true时循环删除快捷方式（即存在很多相同的快捷方式）
     */
    public static void deleteShortcut(Context context , String shortcutName ,
                                      Intent actionIntent , boolean isDuplicate) {
        Intent shortcutIntent = new Intent ("com.android.launcher.action.UNINSTALL_SHORTCUT");
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME ,shortcutName);
        shortcutIntent.putExtra("duplicate" , isDuplicate);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT , actionIntent);
        context.sendBroadcast(shortcutIntent);
    }
}