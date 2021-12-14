package com.nesp.sdk.android.httpx

import com.google.gson.Gson
import com.nesp.sdk.android.utils.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Headers.Companion.toHeaders
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.lang.reflect.Type

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/7/28 10:06
 **/
abstract class AbsHttpClient {

    protected val httpClientCore: OkHttpClient = OkHttpClient.Builder().build()
    protected val gson = Gson()
    protected val mainCoroutineScope = CoroutineScope(Dispatchers.Main)
    open var onResponseHandler: IResponseHandler? = null

    fun <B : IResponseBody> request(
        request: IRequest<*>,
        responseBodyType: Type,
        onRequestListener: OnRequestListener<B>? = null
    ): Call {
        return request(
            request.url,
            request.method,
            request.headers,
            request.params,
            request.body,
            responseBodyType,
            onRequestListener
        )
    }

    fun <B : IResponseBody> request(
        url: String,
        method: IRequest.RequestMethod,
        headers: Map<String, String>,
        params: Map<String, String?>,
        body: Any?,
        responseBodyType: Type,
        onRequestListener: OnRequestListener<B>? = null
    ): Call {
        val request = createRequest(url, method, headers, params, body)

        val call = httpClientCore.newCall(request)

        onRequestListener?.onPreRequest()
        call.enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                mainCoroutineScope.launch {
                    onRequestListener?.onFailure(call, e)
                    onRequestListener?.onPostRequest()
                }
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val okhttpResponseConvert = okhttpResponseConvert<B>(
                    url,
                    response,
                    responseBodyType
                )
                if (okhttpResponseConvert[1] != null) {
                    onRequestListener?.onParseJsonFailure(
                        call,
                        okhttpResponseConvert[1] as Exception
                    )
                    return
                }

                mainCoroutineScope.launch {
                    onRequestListener?.onSuccess(
                        call,
                        okhttpResponseConvert[0] as Response<B>
                    )

                    onRequestListener?.onPostRequest()
                }
            }

        })

        return call
    }

    private fun <B : IResponseBody> okhttpResponseConvert(
        requestUrl: String,
        response: okhttp3.Response,
        responseBodyType: Type
    ): Array<Any?> {
        val responseHeaders = response.headers
        val responseCode = response.code
        val responseMessage = response.message
        val responseBodyString = try {
            response.body?.string()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (LOG_ENABLE) {
            Log.i(TAG, "okhttpResponseConvert:url = $requestUrl")
            Log.i(TAG, "okhttpResponseConvert:response headers  = $responseHeaders")
            Log.i(TAG, "okhttpResponseConvert:response code = $responseCode")
            Log.i(TAG, "okhttpResponseConvert: origin body = $responseBodyString")
        }
        val responseBody: B? = if (responseBodyString != null) {
            try {
                gson.fromJson<B>(responseBodyString, responseBodyType)
            } catch (e: Exception) {
                e.printStackTrace()
                onResponseHandler?.handle(0, "", true)
                return arrayOf(null, e)

            }
        } else null

        return arrayOf(
            Response(
                responseCode,
                responseMessage,
                responseHeaders.toMultimap(),
                responseBody
            ), null
        )
    }

    fun <B : IResponseBody> requestSync(
        request: IRequest<*>,
        responseBodyType: Type
    ): Response<B>? {
        val requestSync = requestSync(request) ?: return null
        return okhttpResponseConvert<B>(
            request.url,
            requestSync,
            responseBodyType
        )[0] as Response<B>?
    }

    fun requestSync(
        request: IRequest<*>,
    ): okhttp3.Response? {
        return requestSync(
            request.url,
            request.method,
            request.headers,
            request.params,
            request.body
        )
    }

    fun requestSync(
        url: String,
        method: IRequest.RequestMethod,
        headers: Map<String, String>,
        params: Map<String, String?>,
        body: Any?
    ): okhttp3.Response? {
        val request = createRequest(url, method, headers, params, body)
        return try {
            httpClientCore.newCall(request).execute()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun createRequest(
        url: String,
        method: IRequest.RequestMethod,
        headers: Map<String, String>,
        params: Map<String, String?>,
        body: Any?
    ): Request {
        if (LOG_ENABLE) {
            Log.i(TAG, "AbsHttpClient.requestSync:url= $url")
            Log.i(TAG, "AbsHttpClient.requestSync:headers= $headers")
            Log.i(TAG, "AbsHttpClient.requestSync:params= $params")
        }
        val requestBuilder = Request.Builder()
        requestBuilder.headers(headers.toHeaders())

        if (method == IRequest.RequestMethod.GET ||
            method == IRequest.RequestMethod.DELETE
        ) {
            val httpUrlBuilder = url.toHttpUrlOrNull()!!.newBuilder()
            for (param in params) {
                httpUrlBuilder.addQueryParameter(param.key, param.value)
            }
            val queryUrl = httpUrlBuilder.build()
            Log.i(TAG, "AbsHttpClient.requestSync:query url= $queryUrl")
            requestBuilder.url(queryUrl)
        } else if (method == IRequest.RequestMethod.POST ||
            method == IRequest.RequestMethod.PUT
        ) {
            requestBuilder.url(url)
        }
        // method == IRequest.RequestMethod.PUT

        val bodyJsonString =
            if (body is RequestBodyWrapper<*>) gson.toJson(body.body) else gson.toJson(body)
        requestBuilder.method(
            method.name,
            if (body != null) {
                bodyJsonString
                    .toRequestBody("application/json".toMediaTypeOrNull())
            } else null
        )

        if (LOG_ENABLE) {
            Log.i(TAG, "AbsHttpClient.requestSync:bodyJsonString= $bodyJsonString")
        }
        return requestBuilder.build()
    }

    abstract class OnRequestListener<B : IResponseBody> {

        fun onPreRequest() {}

        abstract fun onSuccess(call: Call, response: Response<B>)

        abstract fun onFailure(call: Call, e: Exception)

        abstract fun onParseJsonFailure(call: Call, e: Exception)

        fun onPostRequest() {}
    }

    companion object {
        private const val TAG = "AbsHttpClient"
        var LOG_ENABLE = false
    }
}