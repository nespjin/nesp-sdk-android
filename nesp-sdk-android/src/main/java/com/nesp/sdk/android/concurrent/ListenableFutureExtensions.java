package com.nesp.sdk.android.concurrent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/9/30 下午4:01
 **/
public class ListenableFutureExtensions {
    private static final String TAG = "ListenableFutureExtensions";

    public static <R> void addMainListener(ListenableFuture<R> $this,
                                           Runnable listener) {
        $this.addListener(listener, ThreadManager.getInstance().getMainExecutor());
    }

    public static <R> void addMainCallback(ListenableFuture<R> $this,
                                           FutureCallback<? super R> callback) {
        Futures.addCallback($this, callback, ThreadManager.getInstance().getMainExecutor());
    }
}
