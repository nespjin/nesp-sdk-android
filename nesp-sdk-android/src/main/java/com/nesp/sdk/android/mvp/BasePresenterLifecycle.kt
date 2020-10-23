package com.nesp.sdk.android.mvp

import androidx.lifecycle.LifecycleOwner
import com.nesp.sdk.android.lifecycle.DefaultLifecycleObserver
import java.lang.ref.SoftReference

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/23 19:48
 * Project: NespAndroidSdk
 **/
open class BasePresenterLifecycle<V : IView> : IPresenterLifecycle<V>, DefaultLifecycleObserver {

    private var mReferenceView: SoftReference<V>? = null

    override fun attach(view: V, source: LifecycleOwner) {
        mReferenceView = SoftReference(view)
        source.lifecycle.addObserver(this)
    }

    override fun detach(source: LifecycleOwner) {
        source.lifecycle.removeObserver(this)

        mReferenceView?.clear()
        mReferenceView = null
    }

    override fun onCreate(source: LifecycleOwner) {

    }

    override fun onStart(source: LifecycleOwner) {

    }

    override fun onResume(source: LifecycleOwner) {

    }

    override fun onPause(source: LifecycleOwner) {

    }

    override fun onStop(source: LifecycleOwner) {

    }

    override fun onDestroy(source: LifecycleOwner) {

    }

    override fun onAny(source: LifecycleOwner) {

    }
}