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

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/12/10 18:01
 * Project: FishMovieAndroid
 * Description:
 **/
@RunWith(AndroidJUnit4.class)
public class FileDownloadTaskTest {

    private static final String TAG = "FileDownloadTaskTest";

   /* @Test
    public void testDownload() {
        final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        final FileDownloadTask fileDownloadTask = new FileDownloadTask.Builder()
                .setDownloadUrl("https://releases.ubuntu.com/20.04/ubuntu-20.04.3-desktop-amd64.iso")
                .setDestinationDirectory(appContext.getCacheDir())
                .setOnDownloadListener(new FileDownloadTask.OnDownloadListener() {

                    @Override
                    public void onProgress(final int progress, final long speed, final String speedString) {
                        Log.i("FileDownloadTaskTest", "onProgress: progress = " + progress +
                                " speed = " + speed+ " speedString = " + speedString);
                    }

                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onFailed(final String error) {
                        Log.i(TAG, "onFailed: error = " + error);
                    }

                    @Override
                    public void onPaused() {
                        Log.i(TAG, "OnPaused");
                    }

                    @Override
                    public void onCanceled() {
                        Log.i(TAG, "OnCanceled");
                    }
                })
                .build();
        fileDownloadTask.execute();
        while (true) {
        }
    }*/
}