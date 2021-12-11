/*
 *
 *   Copyright (c) 2021  NESP Technology Corporation. All rights reserved.
 *
 *   This program is not free software; you can't redistribute it and/or modify it
 *   without the permit of team manager.
 *
 *   Unless required by applicable law or agreed to in writing.
 *
 *   If you have any questions or if you find a bug,
 *   please contact the author by email or ask for Issues.
 *
 *   Author:JinZhaolu <1756404649@qq.com>
 */

package com.nesp.sdk.android.pay;

/**
 * Team: NESP Technology
 *
 * @author <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * @version 1.0
 * Time: Created 2021/12/11 11:31
 * Description:
 **/
public final class PayOptions {

    /**
     * 商品名称
     */
    private String mTradeName;

    /**
     * 商户订单号
     */
    private String mTradeOrderNo;

    /**
     * 商品价格 分
     */
    private Long mAmount;

    /**
     * 商户系统回调参数
     */
    private String mCallbackParam;

    /**
     * 商户系统回调地址
     */
    private String mCallbackUrl;

    /**
     * 商户系统用户ID (商户系统内唯一)
     */
    private String mUserId;

    public String getTradeName() {
        return mTradeName;
    }

    public void setTradeName(final String tradeName) {
        mTradeName = tradeName;
    }

    public String getTradeOrderNo() {
        return mTradeOrderNo;
    }

    public void setTradeOrderNo(final String tradeOrderNo) {
        mTradeOrderNo = tradeOrderNo;
    }

    public Long getAmount() {
        return mAmount;
    }

    public void setAmount(final Long amount) {
        mAmount = amount;
    }

    public String getCallbackParam() {
        return mCallbackParam;
    }

    public void setCallbackParam(final String callbackParam) {
        mCallbackParam = callbackParam;
    }

    public String getCallbackUrl() {
        return mCallbackUrl;
    }

    public void setCallbackUrl(final String callbackUrl) {
        mCallbackUrl = callbackUrl;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(final String userId) {
        mUserId = userId;
    }
}
