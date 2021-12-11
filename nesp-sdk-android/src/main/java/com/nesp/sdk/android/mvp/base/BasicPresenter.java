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

package com.nesp.sdk.android.mvp.base;

import java.lang.ref.WeakReference;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-10-20 下午6:00
 * @project FishMovie
 **/
public class BasicPresenter<V extends IBasicView> {

    private WeakReference<V> mViewRef;

    private Boolean isAttachView = false;

    public final void attachView(V view) {
        mViewRef = new WeakReference<>(view);
        onAttachViewFinal();
    }

    public final void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
            onDetachViewFinal();
        }
    }

    protected V getView() {
        return mViewRef.get();
    }

    private void onAttachViewFinal(){
        isAttachView = true;
        onAttachView();
    }

    protected void onAttachView(){

    }

    private void onDetachViewFinal(){
        isAttachView = false;
        onDetachView();
    }

    protected void onDetachView(){

    }

    public void fetch() {

    }
}
