/*
 *
 *   Copyright (c) 2020  NESP Technology Corporation. All rights reserved.
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

import android.app.Activity;

import com.base.bj.paysdk.domain.TrPayResult;
import com.base.bj.paysdk.listener.PayResultListener;
import com.base.bj.paysdk.utils.TrPay;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-2-6 下午2:41
 * @project FishMovie
 **/
public class NespPay extends Pay {

    private TrPay mTrPay;

    private volatile static NespPay singleton;

    private final PayResultListener mPayResultListener =
            (context, outtradeno, resultCode, resultString, payType, amount, tradename) -> {
                final PayResult payResult = new PayResult();
                payResult.setTradeOrderNo(outtradeno);
                final int code = resultCode == TrPayResult.RESULT_CODE_SUCC.getId() ?
                        PayResult.CODE_SUCCESS : PayResult.CODE_FAILED;
                payResult.setCode(code);
                payResult.setMessage(resultString);
                int type = Pay.TYPE_UNKNOWN;
                switch (payType) {
                    case 1:
                        type = Pay.TYPE_ALIPAY;
                        break;
                    case 2:
                        type = Pay.TYPE_WECHAT;
                        break;
                    case 3:
                        type = Pay.TYPE_UNION;
                        break;
                }
                payResult.setType(type);
                payResult.setAmount(amount);
                payResult.setTradeName(tradename);
            };

    public static void initialize(Activity activity, String appKey, String channel) {
        (shared().mTrPay = TrPay.getInstance(activity)).initPaySdk(appKey, channel);
    }

    /**
     * @return Shared instance
     * @throws PayException if not call initialize
     */
    public static NespPay shared() throws PayException {
        if (singleton == null) {
            synchronized (NespPay.class) {
                if (singleton == null) {
                    singleton = new NespPay();
                }
            }
        }
        return singleton;
    }

    private NespPay() {

    }

    private void checkInitialize() throws PayException {
        if (mTrPay == null) {
            throw new PayException("Need to initialize Pay at first.");
        }
    }

    @Override
    public void payWithAliPay(final PayOptions payOptions) {
        checkInitialize();
        mTrPay.callAlipay(payOptions.getTradeName(), payOptions.getTradeOrderNo(),
                payOptions.getAmount(), payOptions.getCallbackParam(), payOptions.getCallbackUrl(),
                payOptions.getUserId(), mPayResultListener);
    }

    @Override
    public void payWithWechat(final PayOptions payOptions) {
        checkInitialize();
        mTrPay.callWxPay(payOptions.getTradeName(), payOptions.getTradeOrderNo(),
                payOptions.getAmount(), payOptions.getCallbackParam(), payOptions.getCallbackUrl(),
                payOptions.getUserId(), mPayResultListener);
    }

}
