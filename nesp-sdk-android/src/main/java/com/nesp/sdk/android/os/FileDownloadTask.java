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

package com.nesp.sdk.android.os;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.nesp.sdk.java.util.StorageSizeUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/12/9 23:46
 * Description:
 **/
public final class FileDownloadTask extends AsyncTask<String, Long, FileDownloadTask.DownloadResult> {

    private static final String TAG = "FileDownloadTask";

    public static final class Builder {
        private File mDestinationDirectory;
        private String mDestinationFileName;
        private String mDownloadUrl;
        private OnDownloadListener mOnDownloadListener;

        /**
         * 下载的总长度
         */
        private long mTotalLength;

        public File getDestinationDirectory() {
            return mDestinationDirectory;
        }

        public Builder setDestinationDirectory(final File destinationDirectory) {
            this.mDestinationDirectory = destinationDirectory;
            return this;
        }

        public String getDestinationFileName() {
            return mDestinationFileName;
        }

        public Builder setDestinationFileName(final String destinationFileName) {
            mDestinationFileName = destinationFileName;
            return this;
        }

        public String getDownloadUrl() {
            return mDownloadUrl;
        }

        public Builder setDownloadUrl(final String downloadUrl) {
            mDownloadUrl = downloadUrl;
            return this;
        }

        private OnDownloadListener getOnDownloadListener() {
            return mOnDownloadListener;
        }

        public Builder setOnDownloadListener(final OnDownloadListener onDownloadListener) {
            mOnDownloadListener = onDownloadListener;
            return this;
        }

        public long getTotalLength() {
            return mTotalLength;
        }

        public Builder setTotalLength(final long totalLength) {
            mTotalLength = totalLength;
            return this;
        }

        public FileDownloadTask build() {
            return new FileDownloadTask(this);
        }
    }

    /**
     * 还未进行下载
     */
    private static final byte DOWNLOAD_STATE_NONE = 0x00;

    /**
     * 下载成功
     */
    private static final byte DOWNLOAD_STATE_SUCCESS = 0x01;

    /**
     * 下载失败
     */
    private static final byte DOWNLOAD_STATE_FAILED = 0x02;

    /**
     * 下载暂停
     */
    private static final byte DOWNLOAD_STATE_PAUSE = 0x03;

    /**
     * 下载取消
     */
    private static final byte DOWNLOAD_STATE_CANCELED = 0x04;


    /**
     * 当前的下载状态
     * <p>
     * {@link #DOWNLOAD_STATE_NONE},{@link #DOWNLOAD_STATE_SUCCESS},{@link #DOWNLOAD_STATE_FAILED},
     * {@link #DOWNLOAD_STATE_PAUSE},{@link #DOWNLOAD_STATE_CANCELED}
     */
    private byte mCurrentDownloadState = DOWNLOAD_STATE_NONE;
    private File mDestinationFile;
    private final Builder mBuilder;
    private final OkHttpClient mHttpClient;

    private FileDownloadTask(final Builder builder) {
        super();
        mBuilder = builder;

        if (mBuilder.getDestinationDirectory() == null) {
            throw new IllegalArgumentException("DestinationDirectory must not be null");
        }

        if (TextUtils.isEmpty(mBuilder.getDownloadUrl())) {
            throw new IllegalArgumentException("DownloadUrl must not be null");
        }

        mHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    private void setCurrentDownloadStateAndNotify(final byte currentDownloadState) {
        setCurrentDownloadStateAndNotify(currentDownloadState, "");
    }

    private void setCurrentDownloadStateAndNotify(final byte currentDownloadState, final String error) {
        mCurrentDownloadState = currentDownloadState;
        notifyDownloadStateChanged(error);
    }

    public void setCurrentDownloadState(final byte currentDownloadState) {
        mCurrentDownloadState = currentDownloadState;
    }

    private void notifyDownloadStateChanged(final String error) {
        if (mBuilder.getOnDownloadListener() != null) {
            switch (getCurrentDownloadState()) {
                case DOWNLOAD_STATE_SUCCESS: {
                    mBuilder.getOnDownloadListener().onSuccess();
                    break;
                }
                case DOWNLOAD_STATE_FAILED: {
                    mBuilder.getOnDownloadListener().onFailed(error);
                    break;
                }
                case DOWNLOAD_STATE_PAUSE: {
                    mBuilder.getOnDownloadListener().onPaused();
                    break;
                }
                case DOWNLOAD_STATE_CANCELED: {
                    mBuilder.getOnDownloadListener().onCanceled();
                    break;
                }
            }
        }
    }

    public byte getCurrentDownloadState() {
        return mCurrentDownloadState;
    }

    public boolean isCanceled() {
        return getCurrentDownloadState() == DOWNLOAD_STATE_CANCELED;
    }

    public boolean isPause() {
        return getCurrentDownloadState() == DOWNLOAD_STATE_PAUSE;
    }

    public void pause() {
        setCurrentDownloadState(DOWNLOAD_STATE_PAUSE);
    }

    public void cancel() {
        setCurrentDownloadState(DOWNLOAD_STATE_CANCELED);
        deleteDestinationFile();
    }

    private void deleteDestinationFile() {
        if (mDestinationFile != null) {
            mDestinationFile.deleteOnExit();
        }
    }

    @Override
    protected DownloadResult doInBackground(final String... strings) {
        final String downloadUrl = mBuilder.getDownloadUrl();
        final String destinationFileName = TextUtils.isEmpty(mBuilder.getDestinationFileName())
                ? downloadUrl.substring(downloadUrl.lastIndexOf("/"))
                : mBuilder.getDestinationFileName();
        if (!mBuilder.getDestinationDirectory().exists()) {
            if (!mBuilder.getDestinationDirectory().mkdirs()) {
                return new DownloadResult(DOWNLOAD_STATE_FAILED, "创建目标文件夹失败");
            }
        }

        mDestinationFile = new File(mBuilder.getDestinationDirectory(), destinationFileName);
        long downloadedLength = 0; // 文件已经下载的大小
        if (mDestinationFile.exists()) {
            //文件已存在，则读取已下载的文件字节数（用于断点续传）
            downloadedLength = mDestinationFile.length();
        }

        InputStream inputStream = null;
        RandomAccessFile destinationRandomAccessFile = null;

        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try {
            Response response;
            ResponseBody responseBody;
            long contentLength = mBuilder.getTotalLength();

            if (contentLength == 0) {
                response = mHttpClient.newCall(request).execute();
                if (!response.isSuccessful()) {
                    return new DownloadResult(DOWNLOAD_STATE_FAILED, "网络访问失败");
                }
                responseBody = response.body();
                if (responseBody == null) {
                    return new DownloadResult(DOWNLOAD_STATE_FAILED, "网络访问失败");
                }
                contentLength = responseBody.contentLength();
                response.close();
            }

            if (contentLength == 0) {
                return new DownloadResult(DOWNLOAD_STATE_FAILED, "网络访问失败");
            } else if (contentLength == downloadedLength) {
                return new DownloadResult(DOWNLOAD_STATE_SUCCESS);
            } else if (contentLength < downloadedLength) {
                mDestinationFile.deleteOnExit();
                downloadedLength = 0;
            }

            request = request.newBuilder()
                    //指定从那个字节开始下载，即【断点下载】
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                    .build();
            response = mHttpClient.newCall(request).execute();
            if (!response.isSuccessful() || (responseBody = response.body()) == null) {
                return new DownloadResult(DOWNLOAD_STATE_FAILED, "网络访问失败");
            }

            inputStream = responseBody.byteStream();
            destinationRandomAccessFile = new RandomAccessFile(mDestinationFile, "rw");
            destinationRandomAccessFile.seek(downloadedLength); // 跳转到未下载的字节节点
            final byte[] buffer = new byte[1024];
            int readLength;
            long lastPublishProgressTime = 0;
            long downloadSpeed = 0;
            while ((readLength = inputStream.read(buffer)) != -1) {
                //在下载的过程中，判断下载是否被取消或者被暂停
                if (isCanceled()) {
                    return new DownloadResult(DOWNLOAD_STATE_CANCELED);
                } else if (isPause()) {
                    while (isPause()) {
                        // 等待下载
                        Thread.sleep(200);
                    }
                } else {
                    downloadedLength += readLength;
                    downloadSpeed += readLength;
                    destinationRandomAccessFile.write(buffer, 0, readLength);
                    if (System.currentTimeMillis() - lastPublishProgressTime >= 1000) {
                        final int progress = (int) (downloadedLength * 100 / contentLength);
                        publishProgress((long) progress,
                                downloadSpeed * 1000 / (System.currentTimeMillis() - lastPublishProgressTime));
                        lastPublishProgressTime = System.currentTimeMillis();
                        downloadSpeed = 0;
                    }
                }
            }
            return new DownloadResult(DOWNLOAD_STATE_SUCCESS);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new DownloadResult(DOWNLOAD_STATE_FAILED, "网络访问失败");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignore) {
                }
            }

            if (destinationRandomAccessFile != null) {
                try {
                    destinationRandomAccessFile.close();
                } catch (IOException ignore) {
                }
            }

            if (isCanceled()) {
                deleteDestinationFile();
            }
        }
    }

    @Override
    protected void onProgressUpdate(final Long... values) {
        super.onProgressUpdate(values);
        if (values == null || values.length < 2) return;
        final Long progress = values[0];
        final Long downloadSpeed = values[1];
        if (mBuilder.getOnDownloadListener() != null) {
            mBuilder.getOnDownloadListener().onProgress(progress.intValue(), downloadSpeed,
                    StorageSizeUtil.format(downloadSpeed) + "/s");
        }
    }

    @Override
    protected void onPostExecute(final DownloadResult downloadResult) {
        super.onPostExecute(downloadResult);
        if (downloadResult.getDownloadState() == DOWNLOAD_STATE_FAILED) {
            deleteDestinationFile();
        }
        setCurrentDownloadStateAndNotify(downloadResult.getDownloadState(), downloadResult.getError());
    }

    protected static class DownloadResult {
        private final byte mDownloadState;
        private final String mError;

        public DownloadResult(final byte downloadState, final String error) {
            mDownloadState = downloadState;
            mError = error;
        }

        public DownloadResult(final byte downloadStateSuccess) {
            this(downloadStateSuccess, "");
        }

        public byte getDownloadState() {
            return mDownloadState;
        }

        public String getError() {
            return mError;
        }
    }

    public interface OnDownloadListener {
        /**
         * 当前下载进度
         *
         * @param progress 下载进度 max = 100
         */
        void onProgress(final int progress, final long speed, final String speedString);

        /**
         * 下载成功
         */
        void onSuccess();

        /**
         * 下载失败
         *
         * @param error 失败信息
         */
        void onFailed(final String error);

        /**
         * 暂停下载
         */
        void onPaused();

        /**
         * 取消下载
         */
        void onCanceled();
    }

}
