package com.nesp.sdk.android.httpx

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/7/28 11:43
 **/
data class Response<B : IResponseBody>(
    val code: Int = -1,
    val message: String = "",
    val headers: Map<String, List<String>> = emptyMap(),
    val body: B? = null
)