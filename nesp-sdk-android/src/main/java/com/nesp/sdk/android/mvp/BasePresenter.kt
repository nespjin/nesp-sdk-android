package com.nesp.sdk.android.mvp

import java.lang.ref.SoftReference

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/23 19:55
 * Project: FishAccountingAndroid
 **/
class BasePresenter<V : IView> : IPresenter<V> {

    private var mReferenceView: SoftReference<V>? = null

    override fun attach(view: V) {
        mReferenceView = SoftReference(view)
    }

    override fun detach() {
        mReferenceView?.clear()
        mReferenceView = null
    }
}