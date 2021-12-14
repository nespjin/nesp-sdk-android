package com.nesp.sdk.android.app;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/26 13:37
 **/
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "BaseViewHolder";

    public BaseViewHolder(@NonNull final ViewGroup parent, @LayoutRes final int layoutRes) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
    }
}
