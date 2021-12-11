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

package com.nesp.sdk.android.utils;


/**
 * Team: NESP Technology
 *
 * @author <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * @version 1.0
 * Time: Created 2021/12/11 0:11
 * Description:
 **/
public final class CurrencyUtil {

    private CurrencyUtil() {
        //no instance
    }

    private static final String TAG = "CurrencyUtil";

    /**
     * @param amount 总钱数分
     * @return 带单位的字符串
     */
    public static String toAmountString(long amount) {
        if (amount >= (100 * 10000 * 10 * 10)) {
            return amount / (100 * 10000 * 10 * 10) + "百万";
        }

        if (amount >= (10000 * 10 * 10)) {
            return amount / (10000 * 10 * 10) + "万元";
        }

        if (amount >= (1000 * 10 * 10)) {
            return amount / (1000 * 10 * 10) + "千元";
        }

        if (amount >= (10 * 10)) {
            return amount / (10 * 10) + "元";
        }

        if (amount >= 10) {
            return amount / 10 + "角";
        }

        return amount + "分";
    }

}
