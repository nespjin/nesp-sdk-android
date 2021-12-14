package com.nesp.sdk.android.httpx

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/7/28 11:42
 **/
interface IRequest<B> {
    val url: String
    val method: RequestMethod
    val headers: Map<String, String>
    val params: Map<String, String>
    val body: B?

    enum class RequestMethod { POST, GET, PUT, DELETE }
}