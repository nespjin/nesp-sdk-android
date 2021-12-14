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

package com.nesp.sdk.android.core.ktx.widget

import androidx.recyclerview.widget.RecyclerView
import com.nesp.sdk.android.app.DividerItemDecoration

/**
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 12/14/21 4:33 PM
 * Description:
 **/

fun RecyclerView.addDivider() {
    /* addItemDecoration(
         DividerItemDecoration(
             context,
             RecyclerView.HORIZONTAL
         ).apply {
             setDrawable(context.getDrawableCompat(R.drawable.shape_list_divider)!!)
         }
     )*/
    addItemDecoration(
        DividerItemDecoration(
            context, 2F, 0,
        )
    )
}