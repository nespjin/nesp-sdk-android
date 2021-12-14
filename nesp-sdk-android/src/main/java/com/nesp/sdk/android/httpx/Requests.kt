package com.nesp.sdk.android.httpx

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/7/28 13:02
 **/
abstract class GetRequest : IRequest<IRequestBody> {
    override val method: IRequest.RequestMethod = IRequest.RequestMethod.GET
    override val body: IRequestBody? = null
}

abstract class EmptyHeadersGetRequest : GetRequest() {
    override val headers: Map<String, String> = emptyMap()
}

abstract class EmptyParamsGetRequest : GetRequest() {
    override val params: Map<String, String> = emptyMap()
}

abstract class EmptyGetRequest : EmptyHeadersGetRequest() {
    override val params: Map<String, String> = emptyMap()
}

abstract class DeleteRequest : IRequest<IRequestBody> {
    override val method: IRequest.RequestMethod = IRequest.RequestMethod.DELETE
    override val body: IRequestBody? = null
}

abstract class EmptyHeadersDeleteRequest : DeleteRequest() {
    override val headers: Map<String, String> = emptyMap()
}

abstract class EmptyParamsDeleteRequest : DeleteRequest() {
    override val params: Map<String, String> = emptyMap()
}

abstract class EmptyDeleteRequest : EmptyHeadersDeleteRequest() {
    override val params: Map<String, String> = emptyMap()
}

abstract class PostRequest<B : IRequestBody> : IRequest<B> {
    override val method: IRequest.RequestMethod = IRequest.RequestMethod.POST
    override val params: Map<String, String> = emptyMap()
}

abstract class EmptyHeadersPostRequest<B : IRequestBody> : PostRequest<B>() {
    override val headers: Map<String, String> = emptyMap()
}

abstract class EmptyBodyPostRequest<B : IRequestBody> : PostRequest<B>() {
    override val body: B? = null
}

abstract class EmptyPostRequest<B : IRequestBody> : EmptyHeadersPostRequest<B>() {
    override val body: B? = null
}

abstract class PutRequest<B : IRequestBody> : IRequest<B> {
    override val method: IRequest.RequestMethod = IRequest.RequestMethod.PUT
    override val params: Map<String, String> = emptyMap()
}

abstract class EmptyHeadersPutRequest<B : IRequestBody> : PutRequest<B>() {
    override val headers: Map<String, String> = emptyMap()
}

abstract class EmptyBodyPutRequest<B : IRequestBody> : PutRequest<B>() {
    override val body: B? = null
}

abstract class EmptyPutRequest<B : IRequestBody> : EmptyHeadersPutRequest<B>() {
    override val body: B? = null
}

