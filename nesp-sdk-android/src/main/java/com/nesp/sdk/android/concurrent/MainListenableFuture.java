package com.nesp.sdk.android.concurrent;

import androidx.annotation.NonNull;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/10/6 上午10:30
 **/
public final class MainListenableFuture<V> implements ListenableFuture<V> {

    @NonNull
    private final ListenableFuture<V> mListenableFuture;

    private MainListenableFuture(@NonNull ListenableFuture<V> listenableFuture) {
        this.mListenableFuture = listenableFuture;
    }

    public static <V> MainListenableFuture<V> of(ListenableFuture<V> listenableFuture) {
        return new MainListenableFuture<>(listenableFuture);
    }

    public void addListener(final Runnable listener) {
        ListenableFutureExtensions.addMainListener(mListenableFuture, listener);
    }

    @Override
    public void addListener(final Runnable listener, final Executor executor) {
        mListenableFuture.addListener(listener, executor);
    }

    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        return mListenableFuture.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return mListenableFuture.isCancelled();
    }

    @Override
    public boolean isDone() {
        return mListenableFuture.isDone();
    }

    @Override
    public V get() throws ExecutionException, InterruptedException {
        return mListenableFuture.get();
    }

    @Override
    public V get(final long timeout, final TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        return mListenableFuture.get(timeout, unit);
    }
}
