package com.nesp.sdk.android.app;

import androidx.fragment.app.DialogFragment;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/9/1 9:54
 **/
public abstract class BasicDialogFragment extends DialogFragment {
    private static final String TAG = "BasicDialogFragment";

    private boolean mIsFirstStart = true;

    public BasicDialogFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isFirstStart()) {
            onFirstStart();
            mIsFirstStart = false;
        }
    }

    public void onFirstStart() {

    }

    public boolean isFirstStart() {
        return mIsFirstStart;
    }


}
