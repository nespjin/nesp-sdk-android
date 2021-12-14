package com.nesp.sdk.android.app;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.Executor;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/23 14:54
 **/
public interface IComponentJava extends IComponent {

    @Override
    default void showShortToast(@NonNull final String message) {
        DefaultImpls.showShortToast(this, message);
    }

    @Override
    default void showLongToast(@NonNull final String message) {
        DefaultImpls.showLongToast(this, message);
    }

    @Override
    default void showSuccessTipDialog(@NonNull final String msg) {
        DefaultImpls.showSuccessTipDialog(this, msg);
    }

    @Override
    default void showWarningTipDialog(@NonNull final String msg) {
        DefaultImpls.showWarningTipDialog(this, msg);
    }

    @Override
    default void showErrorTipDialog(@NonNull final String msg) {
        DefaultImpls.showErrorTipDialog(this, msg);
    }

    @Nullable
    @Override
    default TipDialog showLoadingDialog(@NonNull final String msg) {
        return DefaultImpls.showLoadingDialog(this, msg);
    }

    @Override
    default void registerLocalBroadcastReceiver(@NonNull final BroadcastReceiver receiver,
                                                @NonNull final IntentFilter filter) {
        DefaultImpls.registerLocalBroadcastReceiver(this, receiver, filter);
    }

    @Override
    default void unregisterLocalBroadcastReceiver(@NonNull final BroadcastReceiver receiver) {
        DefaultImpls.unregisterLocalBroadcastReceiver(this, receiver);
    }

    @Override
    default void sendLocalBroadcast(@NonNull final Intent intent) {
        DefaultImpls.sendLocalBroadcast(this, intent);
    }

    @NonNull
    @Override
    default Executor getMainExecutorCompat() {
        return DefaultImpls.getMainExecutorCompat(this);
    }
}
