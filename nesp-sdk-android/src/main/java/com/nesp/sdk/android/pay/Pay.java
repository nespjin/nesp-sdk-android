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

import java.util.ArrayList;
import java.util.List;

/**
 * Team: NESP Technology
 *
 * @author <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * @version 1.0
 * Time: Created 2021/12/11 11:29
 * Description: 聚合支付
 **/
public abstract class Pay {

    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_ALIPAY = 0;
    public static final int TYPE_WECHAT = 1;

    /**
     * 银联
     */
    public static final int TYPE_UNION = 2;

    private OnPayResultListener mOnPayResultListener;
    private final List<OnPayResultListener> mOnPayResultListeners = new ArrayList<>();

    public void pay(final int type, final PayOptions payOptions) {
        switch (type) {
            case TYPE_ALIPAY:
                payWithAliPay(payOptions);
                break;
            case TYPE_WECHAT:
                payWithWechat(payOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    /**
     * 支付宝支付
     *
     * @param payOptions 支付参数
     */
    public abstract void payWithAliPay(final PayOptions payOptions);

    /**
     * 微信支付
     *
     * @param payOptions 支付参数
     */
    public abstract void payWithWechat(final PayOptions payOptions);


    private void notifyPayResult(final PayResult payResult) {
        if (mOnPayResultListener != null) {
            mOnPayResultListener.onResult(payResult);
        }

        for (int i = mOnPayResultListeners.size() - 1; i >= 0; i--) {
            final OnPayResultListener onPayResultListener = mOnPayResultListeners.get(i);
            onPayResultListener.onResult(payResult);
        }
    }

    public void setOnPayResultListener(final OnPayResultListener onPayResultListener) {
        mOnPayResultListener = onPayResultListener;
    }

    public void addOnPayResultListener(final OnPayResultListener onPayResultListener) {
        if (onPayResultListener == null || mOnPayResultListeners.contains(onPayResultListener)) {
            return;
        }
        mOnPayResultListeners.add(onPayResultListener);
    }

    public void removeOnPayResultListener(final OnPayResultListener onPayResultListener) {
        mOnPayResultListeners.remove(onPayResultListener);
    }

}
