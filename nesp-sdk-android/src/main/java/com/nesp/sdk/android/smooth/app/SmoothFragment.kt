package com.nesp.sdk.android.smooth.app

import androidx.annotation.CallSuper

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 7:55
 * Project: NespAndroidSdk
 **/
open class SmoothFragment : SmoothBaseFragment() {

    private var dataLazyLoaded = false

    override fun onResume() {
        super.onResume()
        if (!dataLazyLoaded) lazyLoadData()
    }

    @CallSuper
    protected open fun lazyLoadData() {
        dataLazyLoaded = true
    }

}