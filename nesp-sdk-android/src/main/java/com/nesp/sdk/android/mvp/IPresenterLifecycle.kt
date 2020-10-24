package com.nesp.sdk.android.mvp

import androidx.lifecycle.LifecycleOwner

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/23 19:45
 * Project: NespAndroidSdk
 **/
interface IPresenterLifecycle<V : IView> {

    fun attach(view: V, source: LifecycleOwner)

    fun getView(): V?

    fun detach(source: LifecycleOwner)

}