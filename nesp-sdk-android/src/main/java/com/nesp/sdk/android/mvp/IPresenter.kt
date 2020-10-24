package com.nesp.sdk.android.mvp

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/23 19:55
 * Project: NespAndroidSdk
 **/
interface IPresenter<V : IView> {

    fun attach(view: V)

    fun getView(): V?

    fun detach()

}