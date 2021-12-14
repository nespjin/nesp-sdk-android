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

package com.nesp.sdk.android.device;

import static android.text.TextUtils.isEmpty;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.UUID;

/**
 * @Team : NESP Technology
 * @Author : nesp
 * Email : 1756404649@qq.com
 * @Time : 18-4-3 下午5:33
 */
// @TODO: 重构
public final class DeviceUtil {
    private DeviceUtil() {
        //no instance
    }


    /**
     * 获取IP
     *
     * @param context
     * @return
     */
    public static String getIp(final Context context) {
        String ip = null;
        ConnectivityManager conMan = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // mobile 3G Data Network
        android.net.NetworkInfo.State mobile = conMan.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE).getState();
        // wifi
        android.net.NetworkInfo.State wifi = conMan.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState();

        // 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
        if (mobile == android.net.NetworkInfo.State.CONNECTED
                || mobile == android.net.NetworkInfo.State.CONNECTING) {
            ip = getLocalIpAddress();
        }
        if (wifi == android.net.NetworkInfo.State.CONNECTED
                || wifi == android.net.NetworkInfo.State.CONNECTING) {
            //获取wifi服务
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            //判断wifi是否开启
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            ip = (ipAddress & 0xFF) + "." +
                    ((ipAddress >> 8) & 0xFF) + "." +
                    ((ipAddress >> 16) & 0xFF) + "." +
                    (ipAddress >> 24 & 0xFF);
        }
        return ip;

    }

    /**
     * @return 手机GPRS网络的IP
     */
    private static String getLocalIpAddress() {
        try {
            //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {//获取IPv4的IP地址
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String kbTOGb(String valueOfKb) {
        Float r = Float.parseFloat(valueOfKb) / 1024 / 1024;
        DecimalFormat decimalFormat = new DecimalFormat("##0.000");
        return decimalFormat.format(r);//format 返回的是字符串
    }

    /**
     * </span></p>  * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
     * <p>
     * 渠道标志为：
     * 1，andriod（a）
     * <p>
     * 识别符来源标志：
     * 1， wifi_backup mac地址（wifi_backup）；
     * 2， IMEI（imei）；
     * 3， 序列号（sn）；
     * 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        StringBuilder deviceId = new StringBuilder();
        // 渠道标志
        deviceId.append("a");
        try {
            //wifi_backup mac地址
            DeviceInfo.getMACAddress(context);
            //IMEI（imei）
            DeviceInfo.getIMEI(context);
            //序列号（sn）
            DeviceInfo.getSN(context);
            //如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if (!isEmpty(uuid)) {
                deviceId.append("id");
                deviceId.append(uuid);
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(getUUID(context));
        }
        return deviceId.toString();
    }

    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context) {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * 设备唯一UUID
     *
     * @param context context
     * @return 设备唯一UUID
     */
    public static String getDeviceUUID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//        return "6112415C-6A74-4E46-B7DF-3656249F3E22";
    }

    public static class DeviceInfo {

        /**
         * 获取MAC地址
         *
         * @param context s上下文
         * @return String
         */
        public static String getMACAddress(Context context) {
            StringBuilder deviceId = new StringBuilder();
            // 渠道标志
            deviceId.append("a");
            try {
                //wifi_backup mac地址
                WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifi.getConnectionInfo();
                String wifiMac = info.getMacAddress();
                if (!isEmpty(wifiMac)) {
                    deviceId.append("wifi_backup");
                    deviceId.append(wifiMac);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return deviceId.toString();
        }

        /**
         * 获取SN
         *
         * @param context 上下文
         * @return String
         */
        public static String getSN(Context context) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            StringBuilder deviceId = new StringBuilder();
            // 渠道标志
            deviceId.append("a");
            try {
                String sn = tm.getSimSerialNumber();
                if (!isEmpty(sn)) {
                    deviceId.append("sn");
                    deviceId.append(sn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return deviceId.toString();
        }

        /**
         * 获取IMEI
         *
         * @param context 上下文
         * @return String
         */
        public static String getIMEI(Context context) {
            StringBuilder deviceId = new StringBuilder();
            // 渠道标志
            deviceId.append("");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return "";
            }
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String imei = tm.getDeviceId();
                if (!isEmpty(imei)) {
                    deviceId.append(imei);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return deviceId.toString();
        }

        /**
         * 获取屏幕密度 DPI
         *
         * @param activity activities
         * @return String
         */
        public static String getDensityDpi(Activity activity) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return Integer.toString(metric.densityDpi);
        }

        /**
         * 设备品牌
         *
         * @return String
         */
        public static String getBrand() {
            return Build.BRAND;
        }

        /**
         * 设备制造商
         *
         * @return String
         */
        public static String getManufacturer() {
            return Build.MANUFACTURER;
        }

        /**
         * 型号
         *
         * @return String
         */
        public static String getModel() {
//            String model = SystemProperties.get("ro.product.model");
            return Build.MODEL;
        }

        /**
         * 安卓版本
         *
         * @return String
         */
        public static String getAndroidVersion() {
            return SystemProperties.get("ro.build.version.release");
        }

        /**
         * Android安全补丁程序级别
         *
         * @return String
         */
        public static String getAndroidSecurityPatchVersion() {
            return SystemProperties.get("ro.build.version.security_patch");
        }

        /**
         * SDK 版本
         *
         * @return String
         */
        public static String getSdkVersion() {
            return Build.VERSION.SDK;
        }

        /**
         * 设备名
         *
         * @return String
         */
        public static String getProductName() {
            return Build.PRODUCT;
        }


        /**
         * 内核版本
         *
         * @return String
         */
        public static String getKernelVersion() {
            String kernelVersion = "";
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream("/proc/version");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return kernelVersion;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8 * 1024);
            String info = "";
            String line = "";
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    info += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                if (info != "") {
                    final String keyword = "version ";
                    int index = info.indexOf(keyword);
                    line = info.substring(index + keyword.length());
                    index = line.indexOf(" ");
                    kernelVersion = line.substring(0, index);
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            return kernelVersion;
        }

        /**
         * 内存大小
         *
         * @return String
         */
        public static String getTotalMemory() {
            String str1 = "/proc/meminfo";
            String str2;
            String totalSize = "";
            try {
                FileReader fr = new FileReader(str1);
                BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
                while ((str2 = localBufferedReader.readLine()) != null) {
                    totalSize = str2.replace("MemTotal:", "").replace("kB", "").trim();
                    return kbTOGb(totalSize) + "GB";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return kbTOGb(totalSize) + "GB";
        }

        /**
         * 内部储存大小
         *
         * @return long[]
         */
        public long[] getRomMemroy() {
            long[] romInfo = new long[2];
            //Total rom memory
            romInfo[0] = getTotalInternalMemorySize();

            //Available rom memory
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            romInfo[1] = blockSize * availableBlocks;
//            getSysApiLevel();
            return romInfo;
        }

        public long getTotalInternalMemorySize() {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        }

        /**
         * SD大小
         *
         * @return long[]
         */
        public long[] getSDCardMemory() {
            long[] sdCardInfo = new long[2];
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File sdcardDir = Environment.getExternalStorageDirectory();
                StatFs sf = new StatFs(sdcardDir.getPath());
                long bSize = sf.getBlockSize();
                long bCount = sf.getBlockCount();
                long availBlocks = sf.getAvailableBlocks();

                sdCardInfo[0] = bSize * bCount;//总大小
                sdCardInfo[1] = bSize * availBlocks;//可用大小
            }
            return sdCardInfo;
        }

        /**
         * CPU信息
         */
        public static class CpuInfo {
            // 获取CPU最大频率（单位KHZ）
            // "/system/bin/cat" 命令行
            // "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径
            public static String getMaxCpuFreq() {
                String result = "";
                ProcessBuilder cmd;
                try {
                    String[] args = {"/system/bin/cat",
                            "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
                    cmd = new ProcessBuilder(args);
                    Process process = cmd.start();
                    InputStream in = process.getInputStream();
                    byte[] re = new byte[24];
                    while (in.read(re) != -1) {
                        result = result + new String(re);
                    }
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    result = "N/A";
                }
                return "".equals(result.trim()) ? "null" : kbTOGb(result.trim()) + "GHz";
            }

            // 获取CPU最小频率（单位KHZ）
            public static String getMinCpuFreq() {
                String result = "";
                ProcessBuilder cmd;
                try {
                    String[] args = {"/system/bin/cat",
                            "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
                    cmd = new ProcessBuilder(args);
                    Process process = cmd.start();
                    InputStream in = process.getInputStream();
                    byte[] re = new byte[24];
                    while (in.read(re) != -1) {
                        result = result + new String(re);
                    }
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    result = "N/A";
                }
//                Float r = Float.parseFloat(result.trim())/1024/1024;
//                DecimalFormat decimalFormat = new DecimalFormat("##0.000");
//                return  decimalFormat.format(r) + "GHz";//format 返回的是字符串
                return "".equals(result.trim()) ? "null" : kbTOGb(result.trim()) + "GHz";
            }

            // 实时获取CPU当前频率（单位KHZ）
            public static String getCurCpuFreq() {
                String result = "N/A";
                try {
                    FileReader fr = new FileReader(
                            "/sys/devices/system/cpu/cpu7/cpufreq/scaling_cur_freq");
                    BufferedReader br = new BufferedReader(fr);
                    String text = br.readLine();
                    result = text.trim();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return kbTOGb(result.trim()) + "GHz";
            }

            // 获取CPU名字
            public static String getCpuName() {
                try {
                    FileReader fr = new FileReader("/proc/cpuinfo");
                    BufferedReader br = new BufferedReader(fr);
                    String text = br.readLine();
                    String[] array = text.split(":\\s+", 2);
                    for (int i = 0; i < array.length; i++) {
                    }
                    return array[1];
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            // 获取CPU名字
            public static String getCpuModel() {
                try {
                    Scanner scanner = new Scanner(new FileReader("/proc/cpuinfo"));
                    String line = null;
                    while (scanner.hasNextLine() && (line = scanner.nextLine()) != null) {
                        if (!scanner.hasNextLine()) {
                            return line.replace("Hardware\t:", "");
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
    }
}