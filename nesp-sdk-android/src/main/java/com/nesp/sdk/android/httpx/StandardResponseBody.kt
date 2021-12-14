package com.nesp.sdk.android.httpx

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/13 11:26
 **/
open class StandardResponseBody<D : StandardResponseBody.IData> : IResponseBody {
    val msg: String = ""
    val code: Int = -1
    val data: D? = null

    interface IData

    companion object {
        private const val TAG = "StandardResponseBody"
    }
}