/*
 *
 *   Copyright (c) 2021  NESP Technology Corporation. All rights reserved.
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

package com.nesp.sdk.android.server

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nesp.sdk.android.content.SdkSharedPreferences
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/6/27 9:51
 * Project: FishMovieAndroid
 * Description:
 **/
class HostServer private constructor(
    private val url: String,
    private val context: Context
) {
    /** Cache for hosts, the key is app id, the value is host. */
    private val hostsCacheMap = hashMapOf<String, Host>()

    private val gson = Gson()
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    @WorkerThread
    fun parseHost(): ArrayList<Host> {
        if (hostsCacheMap.isNotEmpty()) return getHostsFromCache()
        val hostsFormStorageCache = getHostsFormStorageCache()
        if (hostsFormStorageCache.isNotEmpty()) {
            hostsCacheMap.clear()
            for (hostParsed in hostsFormStorageCache) {
                hostsCacheMap[hostParsed.appId] = hostParsed
            }
            return hostsFormStorageCache
        }
        val request: Request = Request.Builder()
            .url(url)
            .header(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0"
            )
            .addHeader("token", "myToken")
            .build()
        val response = okHttpClient.newCall(request).execute()
        val responseBodyString = response.body?.string()
        val responseCode = response.code
        if (responseCode == 200) {
            if (responseBodyString != null && responseBodyString.isNotEmpty()) {
                val hostsParsed =
                    gson.fromJson<ArrayList<Host>>(responseBodyString, ARRAY_LIST_TYPE_TYPE)
                if (hostsParsed.isNotEmpty()) {
                    hostsCacheMap.clear()
                    for (hostParsed in hostsParsed) {
                        hostsCacheMap[hostParsed.appId] = hostParsed
                    }
                    saveHostsToCache()
                    return getHostsFromCache()
                }
            }
        }
        return arrayListOf()
    }

    fun lookUp(appId: String): Host? {
        return hostsCacheMap[appId] ?: hostsCacheMap[APP_ID_DATA_CENTRAL]
    }

    fun lookUpFrom(hosts: ArrayList<Host>, appId: String): Host? {
        for (host in hosts) {
            if (host.appId == appId) return host
        }
        for (host in hosts) {
            if (host.appId == APP_ID_DATA_CENTRAL) return host
        }
        return null
    }

    private fun getHostsFromCache(): ArrayList<Host> {
        return ArrayList(hostsCacheMap.values)
    }

    private fun getHostsFormStorageCache(): ArrayList<Host> {
        val hostsJsonString = SdkSharedPreferences.getSharedPreferences(context.applicationContext)
            .getString(SHARED_PREFERENCES_CACHE_KEY, "") ?: ""
        if (hostsJsonString.isEmpty()) return arrayListOf()
        return gson.fromJson(hostsJsonString, ARRAY_LIST_TYPE_TYPE)
    }

    private fun saveHostsToCache() {
        val hostsFromCache = getHostsFromCache()
        if (hostsFromCache.isNotEmpty()) {
            SdkSharedPreferences.getSharedPreferences(context)
                .edit()
                .putString(SHARED_PREFERENCES_CACHE_KEY, gson.toJson(hostsFromCache))
                .apply()
        }
    }

    fun clearCache() {
        hostsCacheMap.clear()
        SdkSharedPreferences.getSharedPreferences(context)
            .edit()
            .remove(SHARED_PREFERENCES_CACHE_KEY)
            .apply()
    }

    companion object {
        private const val TAG = "HostServer"
        private const val SHARED_PREFERENCES_CACHE_KEY = "HOST_CACHE"
        private const val APP_ID_DATA_CENTRAL = "com.nesp.data.central"
        private val ARRAY_LIST_TYPE_TYPE = object : TypeToken<ArrayList<Host>>() {}.type

        @Volatile
        @SuppressLint("StaticFieldLeak")
        private var instance: HostServer? = null

        @JvmStatic
        fun getInstance(context: Context, url: String): HostServer =
            instance ?: synchronized(this) {
                instance ?: HostServer(url, context.applicationContext).also { instance = it }
            }
    }
}