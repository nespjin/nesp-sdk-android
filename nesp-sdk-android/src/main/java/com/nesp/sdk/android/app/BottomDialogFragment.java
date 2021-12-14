package com.nesp.sdk.android.app;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nesp.sdk.android.R;


/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/9/1 19:23
 **/
public abstract class BottomDialogFragment extends BaseDialogFragment {
    private static final String TAG = "AppBottomDialogFragment";

    @Nullable
    @Override
    public Activity getAct() {
        return getActivity();
    }

    @NonNull
    @Override
    public Context getCtx() {
        return requireContext();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.NespAppDialog_Bottom);
        return dialog;
    }

    @Override
    protected boolean dispatchTouchEvent(@NonNull final MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
