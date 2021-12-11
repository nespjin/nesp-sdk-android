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

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-1-14 上午1:53
 * @project NespAndroidSdk
 **/
public class CrashCatcher implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashCatcher";

    private static CrashCatcher instance;

    private final Context mContext;
    private static Boolean isActivityMode = false;

    private CrashCatcher(Context context) {
        this.mContext = context.getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static CrashCatcher init(Context context) {
        if (instance != null) return instance;
        else return instance = new CrashCatcher(context);
    }

    public static CrashCatcher init(Context context, boolean activityMode) {
        isActivityMode = activityMode;
        if (instance != null) return instance;
        else return instance = new CrashCatcher(context);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        CrashInfo crashInfo = parseCrash(e);
        handleException(crashInfo);
        Log.e(TAG, "CrashCatcher.uncaughtException: Crash");

        try {
            t.interrupt();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            t.stop();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void handleException(CrashInfo crashInfo) {
        try {
            Intent intent;
            if (isActivityMode)
                intent = new Intent(mContext, CrashCatcherActivity.class);
            else
                intent = new Intent(mContext, CrashCatcherDialogActivity.class);
            intent.putExtra(CrashCatcherActivity.CRASH_MODEL, crashInfo);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CrashInfo parseCrash(Throwable ex) {
        CrashInfo model = new CrashInfo();
        try {
            model.setEx(ex);
            model.setTime(new Date().getTime());
            if (ex.getCause() != null) {
                ex = ex.getCause();
            }
            model.setExceptionMsg(ex.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            pw.flush();
            String exceptionType = ex.getClass().getName();

            if (ex.getStackTrace().length > 0) {
                StackTraceElement element = ex.getStackTrace()[0];

                model.setLineNumber(element.getLineNumber());
                model.setClassName(element.getClassName());
//                model.setPackageName(element.get);
                model.setFileName(element.getFileName());
                model.setMethodName(element.getMethodName());
                model.setExceptionType(exceptionType);
            }

            model.setFullException(sw.toString());
        } catch (Exception e) {
            return model;
        }
        return model;
    }

    //================================Model=======================================

    public static class CrashInfo implements Parcelable {

        /**
         * 崩溃主体信息
         */
        private Throwable ex;
        /**
         * 包名，暂时未使用
         */
        private String packageName;
        /**
         * 崩溃主信息
         */
        private String exceptionMsg;
        /**
         * 崩溃类名
         */
        private String className;
        /**
         * 崩溃文件名
         */
        private String fileName;
        /**
         * 崩溃方法
         */
        private String methodName;
        /**
         * 崩溃行数
         */

        private int lineNumber;
        /**
         * 崩溃类型
         */
        private String exceptionType;
        /**
         * 全部信息
         */
        private String fullException;
        /**
         * 崩溃时间
         */
        private long time;
        /**
         * 设备信息
         */
        private final Device device = new Device();

        protected CrashInfo(Parcel in) {
            ex = (Throwable) in.readSerializable();
            exceptionMsg = in.readString();
            className = in.readString();
            fileName = in.readString();
            methodName = in.readString();
            lineNumber = in.readInt();
            exceptionType = in.readString();
            fullException = in.readString();
            time = in.readLong();
        }

        public CrashInfo() {
        }

        public static final Creator<CrashInfo> CREATOR = new Creator<CrashInfo>() {
            @Override
            public CrashInfo createFromParcel(Parcel in) {
                return new CrashInfo(in);
            }

            @Override
            public CrashInfo[] newArray(int size) {
                return new CrashInfo[size];
            }
        };

        public Throwable getEx() {
            return ex;
        }

        public void setEx(Throwable ex) {
            this.ex = ex;
        }

        public String getExceptionMsg() {
            return exceptionMsg;
        }

        public void setExceptionMsg(String exceptionMsg) {
            this.exceptionMsg = exceptionMsg;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getExceptionType() {
            return exceptionType;
        }

        public void setExceptionType(String exceptionType) {
            this.exceptionType = exceptionType;
        }

        public String getFullException() {
            return fullException;
        }

        public void setFullException(String fullException) {
            this.fullException = fullException;
        }

        public String getPackageName() {
            return getClassName().replace(getFileName(), "");
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public Device getDevice() {
            return device;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeSerializable(ex);
            dest.writeString(exceptionMsg);
            dest.writeString(className);
            dest.writeString(fileName);
            dest.writeString(methodName);
            dest.writeInt(lineNumber);
            dest.writeString(exceptionType);
            dest.writeString(fullException);
            dest.writeLong(time);
        }

        public static class Device implements Parcelable {

            //设备名
            private String model = Build.MODEL;
            //设备厂商
            private String brand = Build.BRAND;
            //系统API等级
            private String sysApiLevel = String.valueOf(Build.VERSION.SDK_INT);
            //系统版本
            private String sysVersion = String.valueOf(Build.VERSION.RELEASE);


            protected Device(Parcel in) {
                model = in.readString();
                brand = in.readString();
                sysApiLevel = in.readString();
                sysVersion = in.readString();
            }

            public static final Creator<Device> CREATOR = new Creator<Device>() {
                @Override
                public Device createFromParcel(Parcel in) {
                    return new Device(in);
                }

                @Override
                public Device[] newArray(int size) {
                    return new Device[size];
                }
            };

            public Device() {
            }

            public String getModel() {
                return model;
            }

            public String getBrand() {
                return brand;
            }

            public String getSysApiLevel() {
                return sysApiLevel;
            }

            public String getSysVersion() {
                return sysVersion;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(model);
                dest.writeString(brand);
                dest.writeString(sysApiLevel);
                dest.writeString(sysVersion);
            }
        }

        @Override
        public String toString() {
            return "CrashModel{" +
                    "ex=" + ex +
                    ", packageName='" + packageName + '\'' +
                    ", exceptionMsg='" + exceptionMsg + '\'' +
                    ", className='" + className + '\'' +
                    ", fileName='" + fileName + '\'' +
                    ", methodName='" + methodName + '\'' +
                    ", lineNumber=" + lineNumber +
                    ", exceptionType='" + exceptionType + '\'' +
                    ", fullException='" + fullException + '\'' +
                    ", time=" + time +
                    ", device=" + device +
                    '}';
        }
    }


}
