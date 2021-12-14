package com.nesp.sdk.android.httpx.sample

import com.nesp.sdk.android.httpx.AbsHttpClient

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/7/28 11:40
 **/
class SampleAppHttpClient private constructor() : AbsHttpClient() {


    companion object {
        val instance: SampleAppHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SampleAppHttpClient()
        }

    }
}