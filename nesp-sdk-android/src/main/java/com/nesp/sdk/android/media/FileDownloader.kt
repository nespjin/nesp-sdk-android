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

package com.nesp.sdk.android.media

import android.os.Environment
import androidx.annotation.IntDef
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile
import java.util.concurrent.TimeUnit


/**
 *
 *
 * @team NESP Technology
 * @author <a href="mailto1756404649@qq.com">靳兆鲁 Email1756404649@qq.com</a>
 * @timeCreated 2020-02-18 1644
 * @project FishMovie
 **/
@Deprecated("")
class FileDownloader(private var downloadPath: String = "", private val onDownloadListener: OnDownloadListener? = null) : IDownloader {

    //是否取消下载
    private var isCanceled = false

    //是否暂停下载
    private var isPaused = false

    //上一次的下载进度
    private var lastProgress = 0

    private val okHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

    override fun download(downloadUrl: String, downloadFileName: String) {

        println("download url: $downloadUrl")

        var fileName = downloadFileName
        val downloadDir = File(downloadPath)
        if (!downloadDir.exists()) downloadDir.mkdirs()

        var inputStream: InputStream? = null
        var downloadedFile: RandomAccessFile? = null
        var response: Response? = null
        var file: File? = null
        try {
            var downloadedLength: Long = 0
            val url = downloadUrl
            fileName = if (fileName.isEmpty()) {
                url.substring(url.lastIndexOf("/"))
            } else {
                fileName
            }

            file = if (downloadPath.isEmpty()) {
                val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
                File(directory, fileName)
            } else {
                File(downloadPath, fileName)
            }

            if (file.exists()) {
                downloadedLength = file.length()
            }

            val contentLength = getContentLength(url)
            if (contentLength == 0L) {
                onDownloadListener?.onFailed("文件大小获取失败")
                return
            } else if (contentLength == downloadedLength) {
                onDownloadListener?.onSuccess()
                return
            } else if (downloadedLength > contentLength) {
                file.delete()
                downloadedLength = 0
            }

            val request = Request.Builder()
                    .addHeader("RANGE", "bytes=$downloadedLength-")
                    .url(url)
                    .build()

            response = okHttpClient.newCall(request).execute()

            if (response == null) {
                onDownloadListener?.onFailed("响应为空")
                return
            }

            inputStream = response.body?.byteStream()

            if (inputStream == null) {
                onDownloadListener?.onFailed("响应流为空")
                return
            }

            downloadedFile = RandomAccessFile(file, "rw")
            downloadedFile.seek(downloadedLength)
            val bytes = ByteArray(1024)

            //下载的总字节数
            var total = 0
            //读取的字节数
            var readLength: Int
            var progress: Int

            var speed = 0L
            var timer = System.currentTimeMillis()

            while (inputStream.read(bytes).also { readLength = it } != -1) { //在下载的过程中，判断下载是否被取消或者被暂停
                when {
                    isCanceled -> { //取消
                        onDownloadListener?.onCanceled()
                        return
                    }
                    isPaused -> { //暂停
                        onDownloadListener?.onPaused()
                        return
                    }
                    else -> {
                        total += readLength
                        speed += readLength

                        val currentTimer = System.currentTimeMillis()

                        if (currentTimer - timer > 1000) {
                            onDownloadListener?.onNetSpeedChanged(speed)
                            timer = currentTimer
                            speed = 0L

                            progress = ((total + downloadedLength) * 100 / contentLength).toInt()
                            if (progress > lastProgress) {
                                onDownloadListener?.onProgress(progress, (total + downloadedLength), contentLength)
                                lastProgress = progress
                            }
                        }
                        downloadedFile.write(bytes, 0, readLength)
                    }
                }
                onDownloadListener?.onSuccess()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            onDownloadListener?.onFailed(e.message.toString())
        } finally {
            try {
                response?.close()
                inputStream?.close()
                downloadedFile?.close()
                if (isCanceled && file != null) {
                    file.delete()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun pause() {
        isPaused = true
    }

    override fun cancel() {
        isCanceled = true
    }


    /**
     * 获取待下载文件的总长度
     *
     * @param url 下载文件地址
     * @return
     */
    @Throws(IOException::class)
    private fun getContentLength(url: String): Long {
        val client = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        return if (response != null && response.isSuccessful) {
            val contentLength = response.body!!.contentLength()
            response.close()
            contentLength
        } else {
            0
        }
    }

    interface OnDownloadListener {

        fun onProgress(progress: Int, currentLength: Long, fullLength: Long)

        fun onNetSpeedChanged(netSpeedPerMillis: Long)

        fun onSuccess()

        fun onFailed(error: String)

        fun onPaused()

        fun onCanceled()
    }

    @IntDef(DownloadResult.SUCCESS, DownloadResult.FAILED, DownloadResult.CANCELED, DownloadResult.PAUSED)
    @Retention(AnnotationRetention.SOURCE)
    annotation class DownloadResult {
        companion object {
            //下载成功
            const val SUCCESS = 0
            //下载失败
            const val FAILED = 1
            //下载被暂停
            const val PAUSED = 2
            //下载被取消
            const val CANCELED = 3

        }
    }


}