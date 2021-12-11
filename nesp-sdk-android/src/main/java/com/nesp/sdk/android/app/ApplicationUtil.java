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

package com.nesp.sdk.android.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.nesp.sdk.android.content.SdkSharedPreferences;
import com.nesp.sdk.java.text.TextUtil;

import java.io.File;
import java.util.List;

import static androidx.core.content.FileProvider.getUriForFile;

/**
 * Team: NESP Technology
 *
 * @author jinzhaolu
 * Email: 1756404649@qq.com
 * Time: 18-6-3 下午1:23
 **/
public final class ApplicationUtil {
    private static final String TAG = "ApplicationUtil";

    private ApplicationUtil() {
        //no instance
    }

    public static boolean isDebug(final Context context) {
        if (context == null) return false;
        final ApplicationInfo applicationInfo = context.getApplicationContext().getApplicationInfo();
        return (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * @return Version name of application
     */
    public static String versionName(final Context context) {
        PackageInfo packageInfo;
        if (context == null || (packageInfo = packageInfo(context)) == null) return "";
        return packageInfo.versionName;
    }

    /**
     * @return Version code of application
     */
    public static int versionCode(final Context context) {
        PackageInfo packageInfo;
        if (context == null || (packageInfo = packageInfo(context)) == null) return -1;
        return packageInfo.versionCode;
    }

    /**
     * @return name of application
     */
    public static String name(final Context context) {
        PackageInfo packageInfo;
        if (context == null || (packageInfo = packageInfo(context)) == null) return "";
        return context.getResources().getString(packageInfo.applicationInfo.labelRes);
    }

    public static PackageInfo packageInfo(final Context context) {
        if (context == null) return null;
        final PackageManager packageManager = context.getApplicationContext().getPackageManager();
        try {
            return packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static String fileProviderName(final Context context) {
        if (context == null) return "";
        return context.getPackageName() + ".fileProvider";
    }

    public static void restart(final Context context) {
        if (context == null) return;
        final Intent intent =
                context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void exit(final Context context) {
        android.os.Process.killProcess(android.os.Process.myPid());
        if (context != null) {
            final ActivityManager activityManager =
                    (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT < 8) {
                activityManager.restartPackage(context.getPackageName());
            } else {
                activityManager.killBackgroundProcesses(context.getPackageName());
            }
        }
        System.exit(0);
    }

    public static boolean isFirstRun(final Context context, final Boolean isFirstRunInitialize) {
        SharedPreferences sharedPreferences = SdkSharedPreferences.getSharedPreferences(context);
        SharedPreferences.Editor editor = SdkSharedPreferences.getSharedPreferences(context).edit();
        if (sharedPreferences.getBoolean("app_is_first_run", true)) {
            editor.putBoolean("app_is_first_run", isFirstRunInitialize).apply();
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEnable(Context context, String key, Boolean defaultEnable) {
        return SdkSharedPreferences.getSharedPreferences(context).getBoolean(key, defaultEnable);
    }

    public static void setEnableNext(Context context, String key, boolean isEnableNext) {
        SdkSharedPreferences.getSharedPreferences(context)
                .edit()
                .putBoolean(key, isEnableNext)
                .apply();
    }

    /**
     * 判断某个应用是否存在
     *
     * @param context     上下文
     * @param packageName 应用包名
     * @return 返回结果
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) return false;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断某些应用之一是否存在
     *
     * @param context      上下文
     * @param packageNames 应用包名
     * @return 返回结果
     */
    public static boolean isAppsInstalled(Context context, String[] packageNames) {
        if (context == null || packageNames == null || packageNames.length <= 0) return false;
        PackageManager packageManager = context.getPackageManager();
        for (String packageName : packageNames) {
            try {
                packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Deprecated
    public static boolean isAppInstalled2(Context context, String appPackage) {
        if (context == null || TextUtils.isEmpty(appPackage)) return false;
        final PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) return false;
        List<PackageInfo> info = packageManager.getInstalledPackages(0);
        if (info == null || info.isEmpty())
            return false;
        for (int i = 0; i < info.size(); i++) {
            if (appPackage.equals(info.get(i).packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 打开开发者模式
     *
     * @param context 上下文
     * @return 是否打开成功
     */
    public static boolean startDevelopmentActivity(Context context) {
        if (context == null) return false;
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                final ComponentName componentName =
                        new ComponentName("com.android.settings",
                                "com.android.settings.Development.settings");
                Intent intent = new Intent();
                intent.setComponent(componentName);
                intent.setAction("android.intent.action.View");
                context.startActivity(intent);
                return true;
            } catch (Exception e1) {
                try {
                    //部分小米手机
                    final Intent intent =
                            new Intent("com.android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                    context.startActivity(intent);
                    return true;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 打开设置
     *
     * @param context 上下文
     * @return 是否打开成功
     */
    public static boolean startSettingActivity(Context context) {
        if (context == null) return false;
        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
        return true;
    }

    /**
     * 打开应用设置界面
     *
     * @param requestCode 请求码
     * @return success
     */
    public boolean startApplicationSettings(final Activity activity, final int requestCode) {
        if (activity == null) return false;
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + activity.getPackageName()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            // Android L 之后Activity的启动模式发生了一些变化
            // 如果用了下面的 Intent.FLAG_ACTIVITY_NEW_TASK ，并且是 startActivityForResult
            // 那么会在打开新的activity的时候就会立即回调 onActivityResult
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivityForResult(intent, requestCode);
            return true;
        } catch (Throwable ignored) {
        }
        return false;
    }

    /**
     * 打开指定Activity
     *
     * @param context      context
     * @param packageName  packageName
     * @param activityName activityName
     * @return 是否打开成功
     */
    public static boolean startSpecialActivity(final Context context,
                                               final String packageName,
                                               final String activityName) {
        if (context == null || TextUtils.isEmpty(packageName) || TextUtils.isEmpty(activityName)) {
            return false;
        }

        try {
            final Intent intent = new Intent();
            ComponentName componentName = new ComponentName(packageName, activityName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(componentName);
//            intent.setAction("android.intent.action.VIEW");
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void startLocationSettings(final Context context) {
        if (context == null) return;
        final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Install APK
    ///////////////////////////////////////////////////////////////////////////

    private static String sLastInstallApkFilePath = "";

    public static void installApk(Activity context, File apkFile, String authority, int requestCode) {
        doInstall(context, apkFile, authority, requestCode);
    }

    /**
     * APK安装
     *
     * @param activity         activity
     * @param apkDirectoryPath APK保存路径(不包括文件名)(xxx/xxx/)
     * @param apkFileName      APK文件名(xxx.apk)
     */
    public static void installApk(Activity activity, String apkDirectoryPath, String apkFileName,
                                  String authority, int requestCode) {
        if (TextUtil.isEmpty(apkDirectoryPath)) {
            throw new IllegalArgumentException("apkDirectoryPath must not be null or empty");
        }
        if (TextUtil.isEmpty(apkFileName)) {
            throw new IllegalArgumentException("apkFileName must not be null or empty");
        }
        final File apkFile = new File(apkDirectoryPath, apkFileName);
        doInstall(activity, apkFile, authority, requestCode);
    }

    public static void reinstallApk(Activity activity, String authority, int requestCode) {
        installApk(activity, sLastInstallApkFilePath, authority, requestCode);
    }

    /**
     * APK安装
     *
     * @param context     context
     * @param apkFilePath APK保存路径(包括文件名)(xx/xx.apk)
     */
    public static void installApk(Activity context, String apkFilePath, String authority, int requestCode) {
        if (TextUtil.isEmpty(apkFilePath)) {
            throw new IllegalArgumentException("apkFileName must not be null or empty");
        }
        doInstall(context, new File(apkFilePath), authority, requestCode);
    }

    /**
     * 执行APK安装
     *
     * @param activity activity
     * @param apkFile  apkFile
     */
    private static void doInstall(Activity activity, File apkFile, String authority, int requestCode) {
        sLastInstallApkFilePath = apkFile.getAbsolutePath();
        if (!apkFile.exists() || !apkFile.getName().endsWith(".apk")) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0+
            installApkForO(activity, apkFile, authority, requestCode);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 7.0+
            installApkForN(activity, apkFile, authority);
        } else {
            //1-6.0
            installApk(activity, apkFile);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void installApkForO(Activity activity, File apkFile, String authority, int requestCode) {
        if (TextUtil.isEmpty(authority)) {
            throw new IllegalArgumentException("authority must not be null or empty");
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        final boolean canRequestPackageInstalls = activity.getPackageManager().canRequestPackageInstalls();
        if (!canRequestPackageInstalls) {
            new AlertDialog.Builder(activity).setCancelable(false).setMessage("安装应用需要打开允许安装未知来源权限，是否在设置中开启此权限?").setPositiveButton("是", (dialog, which) -> {
                final Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                activity.startActivityForResult(intent, requestCode);
            }).setNegativeButton("否", null).create().show();
            return;
        }

        installApkForN(activity, apkFile, authority);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void installApkForN(Activity activity, File apkFile, String authority) {
        if (TextUtil.isEmpty(authority)) {
            throw new IllegalArgumentException("authority must not be null or empty");
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return;
        }

        final Uri uriForApkFile = getUriForFile(activity, authority, apkFile);
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uriForApkFile, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * 1-6.0
     */
    private static void installApk(Activity activity, File apkFile) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        activity.startActivity(intent);

    }

    // 判断该文件是否在下载
    private boolean isDowning(Context context, String uri, DownloadManager downloadManager) {
        boolean flag = false;
        try {
            DownloadManager.Query query = new DownloadManager.Query();

            query.setFilterByStatus(DownloadManager.STATUS_RUNNING);
            if (downloadManager == null) {
                downloadManager = ((DownloadManager) context.getSystemService(Activity.DOWNLOAD_SERVICE));
            }
            Cursor c = downloadManager.query(query);
            String downingURI;
            while (c.moveToNext()) {
                downingURI = c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI));
                if (downingURI.equalsIgnoreCase(uri)) {
                    flag = true;
                    break;
                }
            }
            if (c != null) {
                c.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }
}
