package com.nesp.sdk.android.httpx

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/7/28 10:04
 **/
abstract class AbsResponseBody : IResponseBody {
    val msg: String? = null
    val code: Int? = null
}