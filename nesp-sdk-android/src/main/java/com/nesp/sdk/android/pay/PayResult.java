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
public final class PayResult {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAILED = 1;

    /**
     * 商品名称
     */
    private String mTradeName;

    /**
     * 商户订单号
     */
    private String mTradeOrderNo;

    /**
     * 支付金额 分
     */
    private Long mAmount;

    /**
     * 支付类型 {@link Pay#TYPE_ALIPAY} {@link Pay#TYPE_WECHAT} {@link Pay#TYPE_UNION}
     */
    private int mType;

    /**
     * 支付状态 {@link #CODE_SUCCESS} {@link #CODE_FAILED}
     */
    private int mCode;

    /**
     * 支付结果
     */
    private String mMessage;

    public static int getCodeSuccess() {
        return CODE_SUCCESS;
    }

    public static int getCodeFailed() {
        return CODE_FAILED;
    }

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

    public int getType() {
        return mType;
    }

    public void setType(final int type) {
        mType = type;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(final int code) {
        mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(final String message) {
        mMessage = message;
    }
}
