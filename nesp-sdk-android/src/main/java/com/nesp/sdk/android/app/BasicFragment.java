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

package com.nesp.sdk.android.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nesp.sdk.android.mvp.base.IBasicView;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-10-20 下午6:17
 **/
public abstract class BasicFragment extends Fragment implements IBasicView, IComponentJava {

    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

    protected void initData() {
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initView(view, savedInstanceState);
//        initData();
    }

    @Nullable
    @Override
    public Activity getAct() {
        return requireActivity();
    }

    @NonNull
    @Override
    public Context getCtx() {
        return requireContext();
    }

    protected void invisibleView(View view) {
        if (view == null) return;
        view.setVisibility(View.INVISIBLE);
    }

    protected void showView(View view) {
        if (view == null) return;
        view.setVisibility(View.VISIBLE);
    }

    protected void hideView(View view) {
        if (view == null) return;
        view.setVisibility(View.GONE);
    }

}
