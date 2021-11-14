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
import android.os.Build

/**
 *
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/15 1:03 PM
 * Project: BarcodeSystemAndroid
 * Description:
 **/
@Suppress("DEPRECATION")
class NetworkUtil {
    companion object {

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
    }
}