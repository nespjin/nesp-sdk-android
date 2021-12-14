package com.nesp.sdk.android.httpx

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/10/6 上午10:56
 **/
class RequestBodyWrapper<T>(
    val body: T
) : IRequestBody