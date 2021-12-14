package com.nesp.sdk.android.concurrent;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/9/18 10:56
 **/
public final class ThreadManager {

    private volatile static ThreadManager instance;

    private ThreadManager() {
    }

    public static ThreadManager getInstance() {
        if (instance == null) {
            synchronized (ThreadManager.class) {
                if (instance == null) {
                    instance = new ThreadManager();
                }
            }
        }
        return instance;
    }

    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    private final ListeningExecutorService mDefaultWorkerThreadPool =
            MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    public ListeningExecutorService getDefaultWorkerThreadPool() {
        return mDefaultWorkerThreadPool;
    }

    public ThreadManager runOnWorkThread(Runnable runnable) {
        if (runnable == null) return this;
        mDefaultWorkerThreadPool.submit(runnable);
        return this;
    }

    public <R> ListenableFuture<R> runOnWorkThread(Callable<R> callable) {
        return mDefaultWorkerThreadPool.submit(callable);
    }

    public <R> MainListenableFuture<R> runOnWorkThreadCallbackMain(Callable<R> callable) {
        return MainListenableFuture.of(mDefaultWorkerThreadPool.submit(callable));
    }

    public void delay(long delayMilliseconds, Runnable runnable) {
        if (runnable == null) return;
        mMainHandler.postDelayed(runnable, delayMilliseconds);
    }

    public <R> ListenableFuture<R> delay(long delayMilliseconds, Callable<R> callable) {
        return mDefaultWorkerThreadPool.submit(() -> {
            SystemClock.sleep(delayMilliseconds);
            return callable.call();
        });
    }

    public ThreadManager runOnUIThread(Runnable runnable) {
        if (runnable == null) return this;
        if (isMainThread()) {
            runnable.run();
            return this;
        }
        getMainHandler().post(runnable);
        return this;
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public Handler getMainHandler() {
        return mMainHandler;
    }

    public Executor getMainExecutor() {
        return new HandlerExecutor(mMainHandler);
    }

}
