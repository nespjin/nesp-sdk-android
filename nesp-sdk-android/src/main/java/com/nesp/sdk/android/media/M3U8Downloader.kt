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

import com.nesp.sdk.android.media.M3U8Downloader.M3U8.Companion.M3U8_END_TAG
import com.nesp.sdk.android.media.M3U8Downloader.M3U8.Companion.TS_TIME_TAG
import com.nesp.sdk.java.internet.NespOkHttp
import com.nesp.sdk.java.util.ObjectUtil.modelToString
import com.nesp.sdk.java.util.StorageSizeUtil
import com.nesp.sdk.kotlin.lang.getCharAt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


/**
 *
 *
 * @team NESP Technology
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @time: Created 2020-02-18 17:50
 * @project FishMovie
 **/
@Deprecated("")
class M3U8Downloader : IDownloader {

    companion object {
        const val MAX_THREAD_COUNT = 20
    }

    var onDownloadListener: OnDownloadListener? = null
    var downloadDirPath: String = ""
    var threadCount = 1

    private var threadCompleteCount = 0

    private var isPaused = false
    private var isCanceled = false
    private var isCanceledNoDelete = false

    var isNeedConvertToMp4 = false

    private var m3u8File: File? = null
    private var mp4File: File? = null
    private var m3u8Dir: File? = null
    private var videoName: String = ""
    private var m3U8: M3U8? = null

    override fun download(url: String, videoName: String) {
        this.videoName = videoName

        if (threadCount > MAX_THREAD_COUNT) {
            threadCount = MAX_THREAD_COUNT
        }

        m3u8File = getM3U8File()

        m3u8Dir = getM3U8Dir()

        GlobalScope.launch(Dispatchers.Main) {

            withContext(Dispatchers.IO) {
                m3U8 = parserM3U8(url)
            }

            if (m3U8 == null) {
                onDownloadListener?.onFailed("error")
                return@launch
            }

            val tsList = m3U8!!.tsList

            if (tsList.size == 0) {
                isNetSpeedFinished = true
                onDownloadListener?.onFailed("解析失败")
                return@launch
            }

            try {
                mp4File = File(getMP4Path())
                if (mp4File!!.exists()) {
                    if (m3u8Dir!!.exists()) {
                        m3u8Dir!!.delete()
                    }
                    onDownloadListener?.onSuccess()
                    return@launch
                }
                withContext(Dispatchers.IO) { downloadTsList(tsList) }
            } catch (e: Exception) {
                isNetSpeedFinished = true
                onDownloadListener?.onFailed(e.toString())
            }
        }
    }

    private fun deleteM3U8Dir() {
        val m3u8Dir2 = if (m3u8Dir == null) {
            getM3U8Dir()
        } else {
            m3u8Dir!!
        }
        FileUtils.deleteDirectory(m3u8Dir2)
    }

    private fun getDownloadMP4Length(): Long {
        return mp4File!!.length()
    }

    private fun getM3U8DirLength() =
        FileUtils.sizeOfDirectory(
            if (m3u8Dir == null) {
                getM3U8Dir()
            } else {
                m3u8Dir!!
            }
        )


    fun getDownloadProgress(): Int {
        if (m3U8 == null) {
            throw RuntimeException("m3U8 cant be null")
        }
        return getDownloadedTsCount() * 100 / m3U8!!.tsList.size
    }

    private fun getDownloadedTsCount(): Int {
        val m3u8Dir2 = if (m3u8Dir == null) {
            getM3U8Dir()!!
        } else {
            m3u8Dir!!
        }

        var count = 0
        val listFiles = m3u8Dir2.listFiles()

        if (listFiles == null || listFiles.isEmpty()) return count

        for (listFile in listFiles) {
            if (listFile.absolutePath.endsWith(".ts") || listFile.absolutePath.endsWith(".png")) {
                ++count
            }
        }
        return count
    }

    private fun getM3U8Dir(): File? {
        return File(getM3U8DirPath())
    }

    private fun getM3U8File(): File {
        val m3u8Dir = File(getM3U8DirPath())
        if (!m3u8Dir.exists()) m3u8Dir.mkdirs()
        return File(getM3U8DirPath(), "$videoName.m3u8")
    }

    private fun getM3U8DirPath() = "$downloadDirPath/$videoName"

    private fun getMP4Path() = "$downloadDirPath/$videoName.mp4"

    private val cachedThreadPool = Executors.newCachedThreadPool()

    private var pausedCalled = false
    private var canceledCalled = false

    private var isNetSpeedFinished = false

    private fun downloadTsList(tsList: MutableList<TS>) {

        val downloadedStartIndex = getDownloadedTsCount()

        if (downloadedStartIndex == tsList.size) {
            onDownloadListener?.onSuccess()
            return
        }

        var tsListTmp = tsList
        if (downloadedStartIndex < tsList.size) {
            tsListTmp = tsList.subList(downloadedStartIndex, tsList.size)
        }

        var speed = 0L
        var timer = System.currentTimeMillis()
        var m3U8DirLength = getM3U8DirLength()
        cachedThreadPool.submit {
            try {
                FileUtils.writeStringToFile(
                    File(m3u8File!!.absolutePath + ".downloading"),
                    generateDownloadM3U8Str(m3U8!!),
                    StandardCharsets.UTF_8.toString()
                )
            } catch (e: Exception) {
            }

            while (true) {
                if (isNetSpeedFinished) break
                val currentTimer = System.currentTimeMillis()
                speed += getM3U8DirLength()
                val currentM3U8DirLength = getM3U8DirLength()
                if (currentTimer - timer > 1000) {
                    onDownloadListener?.onNetSpeedChanged((currentM3U8DirLength - m3U8DirLength) * 2)
                    timer = currentTimer
                    speed = 0L
                    m3U8DirLength = currentM3U8DirLength
                }
            }
        }

        val perThreadDownloadCount: Int
        var realThreadCount: Int
        if (tsListTmp.size >= threadCount) {
            perThreadDownloadCount = tsListTmp.size / threadCount
            realThreadCount = threadCount
        } else {
            perThreadDownloadCount = tsListTmp.size
            realThreadCount = 1
        }

        for (i in 0 until realThreadCount) {
            if (isCanceled) {
                if (!isCanceledNoDelete && !canceledCalled) {
                    onDownloadListener?.onCanceled()
                    canceledCalled = true
                }
                return
            } else if (isPaused && !pausedCalled) {
                onDownloadListener?.onPaused()
                pausedCalled = true
                return
            }

            val fromIndex = i * perThreadDownloadCount
            val toIndex = fromIndex + perThreadDownloadCount - 1
            val subList = if (fromIndex >= 0 && toIndex >= 0 && fromIndex == toIndex) {
                mutableListOf(tsListTmp[fromIndex])
            } else if (fromIndex >= 0 && toIndex >= 0 && fromIndex < toIndex) {
                tsListTmp.subList(fromIndex, toIndex)
            } else {
                mutableListOf()
            }

            cachedThreadPool.submit {

                if (fromIndex >= 0 && toIndex >= 0 && fromIndex == toIndex) {
                    println("正在下载$fromIndex ${tsListTmp.size}")
                } else if (fromIndex in 0 until toIndex && toIndex >= 0) {
                    println("正在下载$fromIndex-$toIndex ${tsListTmp.size}")
                }

                if (subList.size == 0) {
                    ++threadCompleteCount
                    return@submit
                }

                for (ts in subList) {
                    if (isCanceled) {
                        if (!isCanceledNoDelete && !canceledCalled) {
                            onDownloadListener?.onCanceled()
                            canceledCalled = true
                        }
                        return@submit
                    } else if (isPaused && !pausedCalled) {
                        onDownloadListener?.onPaused()
                        pausedCalled = true
                        return@submit
                    }

                    if (fileDownloader == null) {
                        fileDownloader = FileDownloader(getM3U8DirPath(), onFileDownloaderListener)
                    }

                    fileDownloader!!.download(ts.realUrl, ts.name)
                }
                ++threadCompleteCount
            }
        }
        val moreCount = tsListTmp.size - perThreadDownloadCount * realThreadCount
        if (moreCount > 0) {
            val fromIndex = perThreadDownloadCount * realThreadCount
            val toIndex = tsListTmp.size - 1
            if (fromIndex == toIndex && fromIndex >= 0 && toIndex >= 0) {
                println("正在下载2 $fromIndex ${tsListTmp.size}")
            } else if (fromIndex in 0 until toIndex && toIndex >= 0) {
                println("正在下载2 ${fromIndex}-${toIndex} ${tsListTmp.size}")
            }
            ++realThreadCount
            val subListMore = if (fromIndex == toIndex && fromIndex >= 0 && toIndex >= 0) {
                mutableListOf(tsListTmp[fromIndex])
            } else if (fromIndex >= 0 && toIndex >= 0 && fromIndex < toIndex) {
                tsListTmp.subList(fromIndex, toIndex)
            } else {
                mutableListOf()
            }
            cachedThreadPool.submit {
                if (subListMore.size == 0) {
                    ++threadCompleteCount
                    return@submit
                }
                for (ts in subListMore) {

                    if (isCanceled) {
                        if (!isCanceledNoDelete && !canceledCalled) {
                            onDownloadListener?.onCanceled()
                            canceledCalled = true
                        }
                        return@submit
                    } else if (isPaused && !pausedCalled) {
                        onDownloadListener?.onPaused()
                        pausedCalled = true
                        return@submit
                    }

                    if (fileDownloader == null) {
                        fileDownloader = FileDownloader(getM3U8DirPath(), onFileDownloaderListener)
                    }

                    fileDownloader!!.download(ts.realUrl, ts.name)
                }
                ++threadCompleteCount
            }
        }

        while (true) {
            if (threadCompleteCount == realThreadCount)
                break
        }

        isNetSpeedFinished = true

        if (isNeedConvertToMp4) {
            ts2MP4(m3U8!!)
        }
        onDownloadListener?.onSuccess()
        val m3u8DownloadingFile = File(m3u8File!!.absolutePath + ".downloading")
        if (m3u8DownloadingFile.exists()) {
            try {
                FileUtils.copyFile(
                    File(m3u8DownloadingFile.absolutePath),
                    File(m3u8File!!.absolutePath)
                )
            } catch (e: Exception) {
            }
            m3u8DownloadingFile.delete()
        }
    }

    private val onFileDownloaderListener = object : FileDownloader.OnDownloadListener {
        override fun onProgress(progress: Int, currentLength: Long, fullLength: Long) {}

        override fun onNetSpeedChanged(netSpeedPerMillis: Long) {
        }

        override fun onSuccess() {
            onDownloadListener?.onProgress(getDownloadProgress(), getM3U8DirLength(), -1L)
        }

        override fun onFailed(error: String) {
            println("M3U8Downloader.onFailed: $error")
            isNetSpeedFinished = true
            onDownloadListener?.onFailed(error)
            cancelNoDelete()
        }

        override fun onPaused() {
        }

        override fun onCanceled() {
        }
    }

    private var fileDownloader: FileDownloader? = null

    private fun ts2MP4(m3u8: M3U8) {
        onDownloadListener?.onConvertToMp4()
        val toFile = File(downloadDirPath, "$videoName.mp4")
        if (toFile.exists()) {
            toFile.delete()
        } else {
            val parentFile = toFile.parentFile
            if (!parentFile.exists()) parentFile.mkdirs()
        }
        val fos = FileOutputStream(toFile)

        val tsList = m3u8.tsList
        for (ts in tsList) {
            val tsFile = File(getM3U8DirPath(), ts.name)
            if (tsFile.exists())
                IOUtils.copyLarge(FileInputStream(tsFile), fos)
        }

        deleteM3U8Dir()
    }

    private fun caculateTsListSize(tsList: MutableList<TS>): Long {
        var length: Long = 0
        for (ts in tsList) {
            if (isCanceled || isPaused) {
                return length
            }

            length += getContentLength(ts.realUrl)
        }
        return length
    }

    private var okHttpClient: OkHttpClient? = null

    private fun getContentLength(url: String): Long {
        if (okHttpClient == null)
            okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()
        val request = Request.Builder().url(url).build()
        val response = okHttpClient!!.newCall(request).execute()
        return if (response != null && response.isSuccessful) {
            val contentLength = response.body!!.contentLength()
            response.close()
            contentLength
        } else {
            0
        }
    }

    private fun generateDownloadM3U8Str(m3u8: M3U8): String {
        var m3u8Str = ""
        val tsList = m3u8.tsList
        for (ts in tsList) {
            m3u8Str = m3u8Str + "\n" + TS_TIME_TAG + ts.timeSec + ",\n" + ts.name
        }
        return m3u8.header.trimEnd() + m3u8Str + "\n" + M3U8_END_TAG
    }

    private fun parserM3U8(url: String): M3U8? {

        val m3U8Str = getM3U8Str(url)
        if (m3U8Str == "") {
            return null
        }

        val m3U8Origin = m3U8Str.replace(M3U8_END_TAG, "")
        val hostUrl = url.substring(0, url.getCharAt("/", 3))
        println(hostUrl)

        val tsList = mutableListOf<TS>()

        if (m3U8Origin.contains(TS_TIME_TAG)) {
            for (s in m3U8Origin.split(TS_TIME_TAG)) {
                if (s.trimEnd().endsWith(".ts") || s.trimEnd().endsWith(".png")) {
                    val tsInfo = s.split(",")
                    val tsUrl = tsInfo[1].trimStart().trimEnd()
                    val ts = TS(
                        tsUrl.substring(tsUrl.lastIndexOf("/") + 1),
                        tsInfo[0],
                        tsUrl,
                        getRealUrl(url, tsUrl)
                    )
                    tsList.add(ts)
                } else if (s.contains(".ts?")) {
                    val tsInfo = s.split(",")
                    val tsUrl = tsInfo[1].trimStart().trimEnd()
                    val tmpUrl = tsUrl.split(".ts?")[0]
                    val ts = TS(
                        tmpUrl.substring(tmpUrl.lastIndexOf("/") + 1) + ".ts",
                        tsInfo[0],
                        tsUrl,
                        getRealUrl(url, tsUrl)
                    )
                    tsList.add(ts)
                }

            }
        } else {
            for (s in m3U8Origin.split("\n")) {
                if (s.endsWith(".m3u8")) {
                    println(getRealUrl(url, s))
                    return parserM3U8(getRealUrl(url, s))
                }
            }
        }
        return M3U8(m3U8Origin.split(TS_TIME_TAG)[0], tsList)
    }

    private fun getRealUrl(url: String, tsUrl: String): String {

        if (tsUrl.startsWith("/")) {
            return url.substring(0, url.getCharAt("/", 3)) + tsUrl
        } else if (tsUrl.startsWith("http")) {
            return tsUrl
        } else {
            return url.substring(0, url.lastIndexOf("/")) + "/" + tsUrl
        }

    }

    private fun getM3U8Str(url: String): String {
        return try {
            NespOkHttp.getResponseBody(url)
        } catch (e: Exception) {
            ""
        }
    }

    override fun pause() {
        isNetSpeedFinished = true
        isPaused = true
    }

    fun cancelNoDelete() {
        isNetSpeedFinished = true
        isCanceled = true
        isCanceledNoDelete = true
    }

    override fun cancel() {
        isNetSpeedFinished = true
        isCanceled = true
        deleteM3U8Dir()
    }


    fun onDestroy() {
        cachedThreadPool.shutdown()
    }

    open class OnDownloadListener {

        open fun onNetSpeedChanged(speedSec: Long) {}

        open fun onProgress(progress: Int, currentLength: Long, fullLength: Long) {}

        open fun onCanceled() {}

        open fun onPaused() {}

        open fun onFailed(error: String) {}

        open fun onSuccess() {}

        open fun onConvertToMp4() {

        }


    }

    data class TS(
        var name: String = "",
        var timeSec: String = "",
        var url: String = "",
        var realUrl: String = ""
    ) {
        override fun toString(): String {
            return modelToString(this)
        }
    }

    data class M3U8(
        var header: String = "",
        var tsList: MutableList<TS> = mutableListOf()
    ) {

        override fun toString(): String {
            return modelToString(this)
        }

        companion object {
            const val TS_TIME_TAG = "#EXTINF:"
            const val M3U8_END_TAG = "#EXT-X-ENDLIST"
        }
    }

}

fun main() {

    val url = "https://hls.aoxtv.com/v3.szjal.cn/20200219/4DUy3GDV/index.m3u8"
    val start = System.currentTimeMillis()
    val m3U8Downloader = M3U8Downloader()
    m3U8Downloader.downloadDirPath = "../"
    m3U8Downloader.threadCount = 3
    m3U8Downloader.isNeedConvertToMp4 = false
    m3U8Downloader.onDownloadListener = object : M3U8Downloader.OnDownloadListener() {

        override fun onNetSpeedChanged(speedSec: Long) {
            super.onNetSpeedChanged(speedSec)
            println(
                SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()) +
                        "onNetSpeedChanged:${StorageSizeUtil.formatStorageSize(speedSec)}/s"
            )
        }

        override fun onCanceled() {
            super.onCanceled()
            println(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()) + "onCanceled")
        }

        override fun onPaused() {
            super.onPaused()
            println(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()) + "onPaused")
        }

        override fun onFailed(error: String) {
            super.onFailed(error)
            println(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()) + "onFailed:$error")
        }

        override fun onSuccess() {
            super.onSuccess()
            println(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()) + "onSuccess")
        }

        override fun onConvertToMp4() {
            super.onConvertToMp4()
            println(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()) + "正在转换成MP4")
        }

        override fun onProgress(progress: Int, currentLength: Long, fullLength: Long) {
            super.onProgress(progress, currentLength, fullLength)
            println(
                SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()) + "onProgress:" +
                        "progress:$progress" +
                        " currentLength:${StorageSizeUtil.formatStorageSize(currentLength)}" +
                        " fullLength:$fullLength"
            )

        }

    }

    m3U8Downloader.download(url, "测试2")

    val sum = System.currentTimeMillis() - start
    println((sum / 1000).toString() + " 秒\n" + sum + " 毫秒")
}