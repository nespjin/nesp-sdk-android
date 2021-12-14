package com.nesp.sdk.android.app;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/9/1 10:18
 **/
public abstract class FullScreenDialogFragment extends BaseDialogFragment {
    private static final String TAG = "AppDialogFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        setCancelable(false);
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    protected final int dialogWidth(final int screenWidth) {
        return screenWidth;
    }

    @Override
    protected final int dialogHeight(final int screenHeight) {
        return screenHeight;
    }
}
