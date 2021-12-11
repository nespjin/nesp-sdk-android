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
 * Time: Created 2021/12/11 12:22
 * Description:
 **/
public class PayException extends IllegalArgumentException {

    private static final String TAG = "PayException";

    public PayException() {
    }

    public PayException(final String message) {
        super(message);
    }

    public PayException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PayException(final Throwable cause) {
        super(cause);
    }

}
