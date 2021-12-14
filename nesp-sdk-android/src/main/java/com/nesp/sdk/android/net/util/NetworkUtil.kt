/*
 * Copyright (C) 2021 The NESP Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nesp.sdk.android.net.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException

/**
 *
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/15 1:03 PM
 * Description:
 **/
object NetworkUtil {

    @JvmStatic
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager?
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager
                    .getNetworkCapabilities(connectivityManager.activeNetwork)
                if (networkCapabilities != null) {
                    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                return activeNetworkInfo != null && activeNetworkInfo.isConnected
            }
        }
        return false
    }

    @JvmStatic
    fun isNetworkAvailable(context: Context): Boolean {
        val mConnectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfo = mConnectivityManager.activeNetworkInfo
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable
        }
        return false
    }

    @JvmStatic
    fun isEthernetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager?
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager
                    .getNetworkCapabilities(connectivityManager.activeNetwork)
                if (networkCapabilities != null) {
                    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                return activeNetworkInfo != null && activeNetworkInfo.isConnected &&
                        activeNetworkInfo.type == ConnectivityManager.TYPE_ETHERNET
            }
        }
        return false
    }

    @JvmStatic
    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager?
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager
                    .getNetworkCapabilities(connectivityManager.activeNetwork)
                if (networkCapabilities != null) {
                    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                return activeNetworkInfo != null && activeNetworkInfo.isConnected &&
                        activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
            }
        }
        return false
    }

    @JvmStatic
    fun isWifiAvailable(context: Context): Boolean {
        val mConnectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mWiFiNetworkInfo = mConnectivityManager
            .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isAvailable
        }
        return false
    }

    @JvmStatic
    fun isMobileConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager?
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager
                    .getNetworkCapabilities(connectivityManager.activeNetwork)
                if (networkCapabilities != null) {
                    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                return activeNetworkInfo != null && activeNetworkInfo.isConnected &&
                        activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
            }
        }
        return false
    }

    @JvmStatic
    fun isMobileAvailable(context: Context): Boolean {
        val mConnectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mMobileNetworkInfo = mConnectivityManager
            .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (mMobileNetworkInfo != null) {
            return mMobileNetworkInfo.isAvailable
        }
        return false
    }

    /**
     * 网络连接方式
     *
     * @param context context
     * @return 网络连接方式
     */
    @JvmStatic
    fun getActiveNetworkTypeName(context: Context): String? {
        //网络连接方式
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.typeName //MOBILE(数据连接)/WIFI
    }

    @JvmStatic
    fun getActiveNetworkType(context: Context) = when (getActiveNetworkTypeName(context)) {
        "WIFI" -> ConnectivityManager.TYPE_WIFI
        "MOBILE" -> ConnectivityManager.TYPE_MOBILE
        else -> -1;
    }

    @JvmStatic
    fun isWifiActive(context: Context): Boolean {
        return getActiveNetworkType(context) == ConnectivityManager.TYPE_WIFI
    }

    @JvmStatic
    fun isMobileActive(context: Context): Boolean {
        return getActiveNetworkType(context) == ConnectivityManager.TYPE_MOBILE
    }

    @JvmStatic
    fun getAvailableNetworkType(context: Context): Int {
        val mConnectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfo = mConnectivityManager.activeNetworkInfo
        if (mNetworkInfo != null && mNetworkInfo.isAvailable) {
            return mNetworkInfo.type
        }
        return -1
    }

    /**
     * 获取当前的网络状态 ：没有网络-1：WIFI网络1：3G网络2：2G网络3
     *
     * @param context
     * @return
     */
    @Deprecated("")
    fun getAPNType(context: Context): Int {
        var networkType = -1
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo ?: return networkType
        val nType = networkInfo.type
        if (nType == ConnectivityManager.TYPE_WIFI) {
            networkType = 1
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            val nSubType = networkInfo.subtype
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            networkType =
                if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    && !telephonyManager.isNetworkRoaming
                ) {
                    2 // 3G
                } else {
                    3 // 2G
                }
        }
        return networkType
    }

    @JvmStatic
    fun getConnectedWifiSsid(context: Context): String? {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ssid = wifiManager.connectionInfo.ssid
        return if (ssid == "") {
            "unknown ssid"
        } else {
            ssid
        }
    }

    @JvmStatic
    fun getLocalIpAddress(): String {
        try {
            val networkInterfaces = NetworkInterface.getNetworkInterfaces()
            while (networkInterfaces.hasMoreElements()) {
                val netI = networkInterfaces.nextElement()
                val enumIpAddr = netI.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (inetAddress is Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace();
        }
        return "0.0.0.0"
    }

    @JvmStatic
    fun getWifiIpAddress(context: Context): String {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        return intToIp(ipAddress)
    }

    @JvmStatic
    fun intToIp(ipInt: Int): String {
        return (ipInt and 0xFF).toString() + "." +
                (ipInt shr 8 and 0xFF) + "." +
                (ipInt shr 16 and 0xFF) + "." +
                (ipInt shr 24 and 0xFF)
    }

}