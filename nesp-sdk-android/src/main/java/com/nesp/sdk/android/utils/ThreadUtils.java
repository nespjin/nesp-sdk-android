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

package com.nesp.sdk.android.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-4-6 下午8:21
 * @project NVPlayerDemo
 **/
@Deprecated
public class ThreadUtils {

    private static class ThreadHandler extends Handler {

        ThreadHandler(final OnThreadRunningListener onThreadRunningListener) {
            super();
            mOnThreadRunningListener = onThreadRunningListener;
        }

        private OnThreadRunningListener mOnThreadRunningListener;

        @Override
        public void handleMessage(@NonNull final Message msg) {
            if (mOnThreadRunningListener != null) {
                mOnThreadRunningListener.onResult(msg);
            }
        }
    }

    private OnThreadRunningListener onThreadRunningListener;

    private List<Thread> mThreadList = new ArrayList<>();
    private List<ThreadHandler> mThreadHandlerList = new ArrayList<>();

    public Thread startNewThread(final OnThreadRunningListener onThreadRunningListener) {
        this.onThreadRunningListener = onThreadRunningListener;
        ThreadHandler handler = new ThreadHandler(onThreadRunningListener);
        Thread thread = new Thread(() -> {
            Looper.prepare();
            if (onThreadRunningListener != null) {
                onThreadRunningListener.onStart(handler);
            }
            Looper.loop();
        });
        thread.start();
        mThreadList.add(thread);
        mThreadHandlerList.add(handler);
        return thread;
    }

    public interface OnThreadRunningListener {

        void onStart(Handler handler);

        void onResult(Message message);

    }

    public void destroy() {
        onThreadRunningListener = null;
        for (final Thread thread1 : mThreadList) {
            if (thread1 != null) {
                thread1.interrupt();
            }
        }
        mThreadList.clear();

        for (final ThreadHandler threadHandler : mThreadHandlerList) {
            if (threadHandler != null) {
                threadHandler.removeCallbacksAndMessages(null);
            }
        }
        mThreadHandlerList.clear();
    }

}
