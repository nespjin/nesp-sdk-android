/*
 * Copyright (C) 2021 The NESP Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nesp.sdk.android.smooth.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.content.getColorCompat
import com.nesp.sdk.android.utils.AttrUtil

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/10/18 19:19
 * Project: NespAndroidSdk
 **/
class SmoothRecyclerView : RecyclerView {

    private var isScrollTop = false
    private var isScrollBottom = false
    private var isScrollUp = false
    private var isScrollDown = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) :
            this(context, attrs, androidx.recyclerview.R.attr.recyclerViewStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {

    }

    fun addDefaultListDividerItemDecoration(paddingStart: Float = -1F) {
        addItemDecoration(ListDividerItemDecoration(paddingStart))
    }

    fun addDefaultListDividerItemDecorationWithoutPaddingStart() {
        addItemDecoration(ListDividerItemDecoration(0F))
    }

    fun isScrollTop(): Boolean {
        return isScrollTop
    }

    fun isScrollBottom(): Boolean {
        return isScrollBottom
    }

    fun isScrollUp(): Boolean {
        return isScrollUp
    }

    fun isScrollDown(): Boolean {
        return isScrollDown
    }

    override fun onScrolled(dx: Int, dy: Int) {
        isScrollTop = !canScrollVertically(-1)
        isScrollBottom = !canScrollVertically(1)
        isScrollUp = dy > 0
        isScrollDown = dy < 0
    }

    class ListDividerItemDecoration(private val paddingStart: Float = -1F) :
        RecyclerView.ItemDecoration() {

        private val dividerPaint = Paint()

        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: State) {
            val context = parent.context
            dividerPaint.color = context.getColorCompat(
                AttrUtil.getAttrOutTypeValue(
                    context, R.attr.listDividerColor
                ).resourceId
            )
            val dividerLineWidth = AttrUtil.getDimensionAttrValue(
                context, R.attr.listDividerWidth
            )
            val childCount = parent.childCount

            val layoutManager = parent.layoutManager
            if (layoutManager is LinearLayoutManager) {
                val left =
                    if (paddingStart == -1F)
                        AttrUtil.getDimensionAttrValue(context,
                            R.attr.smoothActivityHorizontalPadding)
                    else
                        paddingStart
                val right = parent.width - parent.paddingRight

                for (i in 0 until childCount - 1) {
                    val view: View = parent.getChildAt(i)
                    val top: Float =
                        view.bottom.toFloat() - dividerLineWidth
                    val bottom: Float = view.bottom.toFloat()
                    c.drawRect(left, top, right.toFloat(), bottom, dividerPaint)
                }
            } else if (layoutManager is GridLayoutManager) {
                for (i in 0 until childCount) {
                    val view: View = parent.getChildAt(i)

                    //DrawBottom
                    c.drawRect(view.left.toFloat(),
                        view.bottom.toFloat(),
                        view.right.toFloat(),
                        view.bottom + dividerLineWidth,
                        dividerPaint)

                    //DrawLeft
                    c.drawRect(view.left.toFloat(),
                        view.top.toFloat(),
                        view.left + dividerLineWidth,
                        view.bottom.toFloat(),
                        dividerPaint)
                }
            }
        }
    }
}