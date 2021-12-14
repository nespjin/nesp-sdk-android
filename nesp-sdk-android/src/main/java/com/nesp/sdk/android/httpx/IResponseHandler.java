package com.nesp.sdk.android.httpx;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/23 15:53
 **/
public interface IResponseHandler {
    void handle(int code, String message, boolean isConvertJsonError);
}
