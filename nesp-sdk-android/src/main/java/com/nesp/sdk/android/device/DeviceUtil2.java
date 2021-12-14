package com.nesp.sdk.android.device;

import android.os.Build;
import android.text.TextUtils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.UUID;

/**
 * 设备码获取工具
 */
@Deprecated
public final class DeviceUtil2 {

    private DeviceUtil2() {
        //no instance
    }

    /**
     * 获取设备以太网的Mac地址
     *
     * @return
     */
    public static String getEthernetMac() {
//        return "3650E02B27DE";
        String ethernetMac = "";
        try {
            NetworkInterface NIC = NetworkInterface.getByName("eth0");
            if (NIC == null) {
                NIC = NetworkInterface.getByName("wlan0");
            }
            byte[] buf = NIC.getHardwareAddress();
            ethernetMac = byteHexString(buf);
        } catch (SocketException | NullPointerException e) {
            ethernetMac = "";
        }

        // 校验Mac地址的合法性
        if (TextUtils.isEmpty(ethernetMac)) {
            return getSerialID();
        }
        return ethernetMac;
    }

    public static String getEthernetStandardMac() {
        return convertMacToStandard(getEthernetMac());
    }

    public static String convertMacToStandard(String mac) {
        if (TextUtils.isEmpty(mac)) {
            return null;
        } else if (mac.contains(":")) {
            mac = mac.replace(":", "");
        } else {
            try {
                String[] macs = new String[6];
                for (int i = 0; i <= 5; i++) {
                    macs[i] = mac.substring(i * 2, i * 2 + 2);
                }
                mac = macs[0];
                for (int i = 1; i < macs.length; i++) {
                    mac += ":" + macs[i];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mac;
    }

    /**
     * 字节数组转16进制字符串
     *
     * @param array
     * @return
     */
    private static String byteHexString(byte[] array) {
        StringBuilder builder = new StringBuilder();
        for (byte b : array) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            builder.append(hex);
        }
        return builder.toString().toUpperCase();
    }

    /**
     * 获取设备序列号
     *
     * @return
     */
    private static String getSerialID() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                return Build.getSerial();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Build.SERIAL;
    }

    /**
     * 获取设备UUID
     *
     * @param isFilterChar
     * @return
     */
    public static String getUUID(boolean isFilterChar) {
        if (isFilterChar) {
            return UUID.randomUUID().toString().replace("-", "");
        } else {
            return UUID.randomUUID().toString();
        }
    }
}
