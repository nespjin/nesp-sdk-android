package com.nesp.sdk.android.smooth.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.content.getColorCompat
import com.nesp.sdk.android.util.AttrUtil
import org.w3c.dom.Attr

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/18 19:19
 * Project: NespAndroidSdk
 **/
class SmoothRecyclerView : RecyclerView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) :
            this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {

    }

    fun addDefaultListDividerItemDecoration(){
        addItemDecoration(ListDividerItemDecoration())
    }

    class ListDividerItemDecoration : RecyclerView.ItemDecoration() {

        private val dividerPaint = Paint()

        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: State) {
            val context = parent.context
            dividerPaint.color = context.getColorCompat(
                AttrUtil.getAttrOutTypeValue(
                    context, R.attr.listDividerColor
                ).resourceId
            )
            val childCount = parent.childCount
            val left =
                AttrUtil.getDimensionAttrValue(context, R.attr.smoothActivityHorizontalPadding)
            val right = parent.width - parent.paddingRight

            for (i in 0 until childCount - 1) {
                val view: View = parent.getChildAt(i)
                val top: Float =
                    view.bottom.toFloat() - AttrUtil.getDimensionAttrValue(
                        context, R.attr.listDividerWidth
                    )
                val bottom: Float = view.bottom.toFloat()
                c.drawRect(left, top, right.toFloat(), bottom, dividerPaint)
            }
        }

    }

}