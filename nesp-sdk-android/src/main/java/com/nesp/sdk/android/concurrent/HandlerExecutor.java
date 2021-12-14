package com.nesp.sdk.android.concurrent;

import android.os.Handler;

import java.util.concurrent.Executor;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/9/30 下午3:44
 **/
public class HandlerExecutor implements Executor {
    private static final String TAG = "HandlerExecutor";

    private final Handler mHandler;

    public HandlerExecutor(final Handler handler) {
        mHandler = handler;
    }

    @Override
    public void execute(final Runnable command) {
        mHandler.post(command);
    }
}
