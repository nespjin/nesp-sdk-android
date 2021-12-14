package com.nesp.sdk.android.httpx

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/7/28 12:56
 **/
interface IAPI<REQ_B : IRequestBody, REQ : IRequest<REQ_B>, RES_B : IResponseBody>

abstract class GetAPI<REQ : GetRequest, RES_B : IResponseBody>
    : IAPI<IRequestBody, REQ, RES_B>

abstract class DeleteAPI<REQ : DeleteRequest, RES_B : IResponseBody>
    : IAPI<IRequestBody, REQ, RES_B>
