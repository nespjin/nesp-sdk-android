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

package com.nesp.sdk.android.app;

import com.nesp.sdk.android.widget.Toast;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @team NESP Technology
 * @time: Created 19-10-20 下午6:27
 * @project FishMovie
 **/
public class Fragment extends androidx.fragment.app.Fragment {

    protected void showLongToast(String msg) {
        Toast.showLong(getContext(), msg);
    }

    protected void showShortToast(String msg) {
        Toast.showShort(getContext(), msg);
    }

}
