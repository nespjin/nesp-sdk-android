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

package com.nesp.sdk.android.mvp2;

import java.lang.ref.SoftReference;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-12-1 上午1:18
 * @project FishMovie
 **/
public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    private SoftReference<IBaseView> mReferenceView;

    @SuppressWarnings("unchecked")
    @Override
    public void attach(IBaseView view) {
        mReferenceView = new SoftReference<>(view);
    }

    @SuppressWarnings("unchecked")
    public V getView() {
        return (V) mReferenceView.get();
    }

    @Override
    public void detach() {
        mReferenceView.clear();
        mReferenceView = null;
    }
}
