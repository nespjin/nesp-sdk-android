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

package com.nesp.sdk.android.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nesp.sdk.android.widget.NespRecyclerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-2-1 下午10:51
 * @project FishMovie
 **/
@Deprecated
public class SettingsGroupRecyclerView extends NespRecyclerView {

    private Context context;
    private TextView textViewTitle;

    public SettingsGroupRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public SettingsGroupRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingsGroupRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        setRefreshEnable(false);
        textViewTitle = new TextView(context);
         setHeaderView(textViewTitle);
    }

    //================================API=======================================

    public void setTitle(String title) {
        textViewTitle.setText(title);
    }

    public TextView getTextViewTitle() {
        return textViewTitle;
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private RecyclerView.Adapter originAdapter;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
