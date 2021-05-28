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

package com.nesp.sdk.android.smooth.app

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mmin18.widget.RealtimeBlurView
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.content.getColorCompat
import com.nesp.sdk.android.core.ktx.widget.gone
import com.nesp.sdk.android.core.ktx.widget.visible
import com.nesp.sdk.android.text.Text
import com.nesp.sdk.android.util.AttrUtil

/**
 *
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/10/13 12:45 PM
 * Project: NespAndroidSdk
 * Description:
 **/
@Suppress("DEPRECATION")
class SmoothAlertDialog : Dialog, ISmoothDialog {

    class Builder {

        internal var context: Context? = null

        @StyleRes
        internal var themeResId: Int = 0
        internal var cancelable: Boolean = true
        internal var onCancelListener: DialogInterface.OnCancelListener? = null
        internal val actions: ArrayList<ISmoothDialog.Action> = arrayListOf()
        internal var title: Text = Text.empty()
        internal var message: Text = Text.empty()

        constructor(context: Context) : this(context, 0)

        constructor(context: Context, @StyleRes themeResId: Int) : this(context, true, null) {
            this.context = context
            this.themeResId = themeResId
        }

        constructor(
            context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?
        ) {
            this.context = context
            this.cancelable = cancelable
            this.onCancelListener = cancelListener
        }

        fun addAction(action: ISmoothDialog.Action): Builder {
            this.actions.add(action)
            return this
        }

        fun addAction(index: Int, action: ISmoothDialog.Action): Builder {
            this.actions.add(index, action)
            return this
        }

        fun addActions(vararg actions: ISmoothDialog.Action): Builder {
            this.actions.addAll(actions)
            return this
        }

        fun addActions(actions: ArrayList<ISmoothDialog.Action>): Builder {
            this.actions.addAll(actions)
            return this
        }

        fun removeAction(action: ISmoothDialog.Action): Builder {
            this.actions.remove(action)
            return this
        }

        fun removeActionAt(index: Int): Builder {
            this.actions.removeAt(index)
            return this
        }

        fun getActions(): ArrayList<ISmoothDialog.Action> {
            return this.actions
        }

        fun setTitle(text: String): Builder {
            this.title = Text(text)
            return this
        }

        fun setTitle(id: Int): Builder {
            this.title = Text(context!!.getString(id))
            return this
        }

        fun setTitle(text: Text): Builder {
            this.title = text
            return this
        }

        fun getTitle(): Text {
            return title
        }

        fun getTitleText(): String {
            return title.content
        }

        fun setMessage(text: String): Builder {
            this.message = Text(text)
            return this
        }

        fun setMessage(id: Int): Builder {
            this.message = Text(context!!.getString(id))
            return this
        }

        fun setMessage(text: Text): Builder {
            this.message = text
            return this
        }

        fun getMessage(): Text {
            return this.message
        }

        fun getMessageText(): String {
            return this.message.content
        }

        fun setTheme(id: Int): Builder {
            this.themeResId = id
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        fun getCancelable(): Boolean {
            return this.cancelable
        }

        fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener?): Builder {
            this.onCancelListener = onCancelListener
            return this
        }

        fun create(): SmoothAlertDialog {
            if (themeResId == 0) {
                themeResId =
                    AttrUtil.getAttrOutTypeValue(context!!, R.attr.smoothDialogStyle)
                        .resourceId
            }
            val smoothAlertDialog = SmoothAlertDialog(context!!, themeResId, this)
            smoothAlertDialog.setCancelable(cancelable)
            smoothAlertDialog.setOnCancelListener(onCancelListener)
            return smoothAlertDialog
        }
    }

    private var builder: Builder
    private val tvTitle: TextView by lazy { findViewById(R.id.tvTitle) }
    private val tvMessage: TextView by lazy { findViewById(R.id.tvMessage) }
    private val actionsRecyclerView: RecyclerView by lazy { findViewById(R.id.actionsRecyclerView) }
    private val cardView: CardView by lazy { findViewById(R.id.cardView) }
    private val realtimeBlurView: RealtimeBlurView by lazy { findViewById(R.id.realtimeBlurView) }
    private val rlActionContainer: RelativeLayout by lazy { findViewById(R.id.rlActionContainer) }
    private val llActionContainer: LinearLayout by lazy { findViewById(R.id.llActionContainer) }
    private val vActionDivider: View by lazy { findViewById(R.id.vActionDivider) }
    private val tvAction1: TextView by lazy { findViewById(R.id.tvAction1) }
    private val tvAction2: TextView by lazy { findViewById(R.id.tvAction2) }
    private var actionAdapter: ActionAdapter? = null

    private constructor(context: Context, builder: Builder) : super(context) {
        this.builder = builder
    }

    private constructor(context: Context, @StyleRes themeResId: Int, builder: Builder) : super(
        context, themeResId
    ) {
        this.builder = builder
    }

    private constructor(
        context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?,
        builder: Builder
    ) : super(context, cancelable, cancelListener) {
        this.builder = builder
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.decorView?.setBackgroundColor(Color.TRANSPARENT)

        setContentView(R.layout.smooth_alert_dialog)

        tvTitle.text = builder.title.content
        builder.title.textStyle?.setToTextView(tvTitle)

        tvMessage.text = builder.message.content
        builder.message.textStyle?.setToTextView(tvMessage)
        initActions()

        cardView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        realtimeBlurView.layoutParams.apply {
            height = cardView.measuredHeight
        }

        rlActionContainer.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        vActionDivider.layoutParams.apply {
            height = rlActionContainer.measuredHeight
        }
    }

    private fun initActions() {
        if (builder.actions.size > 2) {
            val orientation =
                if (builder.actions.size > 2) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
            actionsRecyclerView.layoutManager = LinearLayoutManager(context, orientation, false)
            actionAdapter = ActionAdapter(this, builder.actions)
            actionsRecyclerView.adapter = actionAdapter
            actionsRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER
            actionsRecyclerView.addItemDecoration(ActionListDividerItemDecoration())

            actionsRecyclerView.visible()
            rlActionContainer.gone()
            vActionDivider.gone()
        } else {
            if (builder.actions.size == 1 || builder.actions.size == 2) {
                if (builder.actions.size == 1) {
                    tvAction1.text = builder.actions[0].text
                    if (builder.actions[0].type == ISmoothActionSheetDialog.ActionType.DANGER) {
                        val resourceId = AttrUtil.getAttrOutTypeValue(
                            tvAction1.context,
                            R.attr.smoothActionSheetDialogDangerActionStyle
                        ).resourceId
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tvAction1.setTextAppearance(resourceId)
                        } else {
                            tvAction1.setTextAppearance(tvAction1.context, resourceId)
                        }
                    }
                    builder.actions[0].textStyle?.setToTextView(tvAction1)
                    tvAction1.setOnClickListener {
                        builder.actions[0].onActionClickListener?.onClick(it, builder.actions[0], 0)
                        dismiss()
                    }
                    tvAction1.visible()
                    tvAction2.gone()
                    vActionDivider.gone()
                } else {
                    tvAction1.text = builder.actions[0].text
                    if (builder.actions[0].type == ISmoothActionSheetDialog.ActionType.DANGER) {
                        val resourceId = AttrUtil.getAttrOutTypeValue(
                            tvAction1.context,
                            R.attr.smoothActionSheetDialogDangerActionStyle
                        ).resourceId
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tvAction1.setTextAppearance(resourceId)
                        } else {
                            tvAction1.setTextAppearance(tvAction1.context, resourceId)
                        }
                    }
                    builder.actions[0].textStyle?.setToTextView(tvAction1)
                    tvAction1.setOnClickListener {
                        builder.actions[0].onActionClickListener?.onClick(it, builder.actions[0], 0)
                        dismiss()
                    }
                    tvAction2.text = builder.actions[1].text
                    if (builder.actions[1].type == ISmoothActionSheetDialog.ActionType.DANGER) {
                        val resourceId = AttrUtil.getAttrOutTypeValue(
                            tvAction2.context,
                            R.attr.smoothActionSheetDialogDangerActionStyle
                        ).resourceId
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tvAction2.setTextAppearance(resourceId)
                        } else {
                            tvAction2.setTextAppearance(tvAction2.context, resourceId)
                        }
                    }
                    builder.actions[1].textStyle?.setToTextView(tvAction2)
                    tvAction2.setOnClickListener {
                        builder.actions[1].onActionClickListener?.onClick(it, builder.actions[1], 1)
                        dismiss()
                    }
                    tvAction1.visible()
                    tvAction2.visible()
                    vActionDivider.visible()
                }
                llActionContainer.weightSum = builder.actions.size.toFloat()
                actionsRecyclerView.gone()
                rlActionContainer.visible()
            } else {
                actionsRecyclerView.gone()
                rlActionContainer.gone()
                vActionDivider.gone()
            }
        }
    }

    private fun refreshActions() {
        actionAdapter?.notifyDataSetChanged()
    }

    override fun addAction(action: ISmoothDialog.Action) {
        builder.addAction(action)
        refreshActions()
    }

    override fun addAction(index: Int, action: ISmoothDialog.Action) {
        builder.addAction(index, action)
        refreshActions()
    }

    override fun addActions(vararg actions: ISmoothDialog.Action) {
        builder.actions.addAll(actions)
        refreshActions()
    }

    override fun addActions(actions: ArrayList<ISmoothDialog.Action>) {
        builder.addActions(actions)
        refreshActions()
    }

    override fun removeAction(action: ISmoothDialog.Action) {
        builder.removeAction(action)
        refreshActions()
    }

    override fun removeActionAt(index: Int) {
        builder.removeActionAt(index)
        refreshActions()
    }

    override fun getActions(): ArrayList<ISmoothDialog.Action> {
        return builder.getActions()
    }

    override fun setTitle(text: String) {
        tvTitle.text = text
    }

    override fun setTitle(id: Int) {
        tvTitle.text = context.getString(id)
    }

    override fun setTitle(text: Text) {
        tvTitle.text = text.content
        text.textStyle?.setToTextView(tvTitle)
    }

    override fun getTitle(): Text {
        return builder.getTitle()
    }

    override fun getTitleText(): String {
        return builder.getTitleText()
    }

    override fun setMessage(text: String) {
        tvMessage.text = text
    }

    override fun setMessage(id: Int) {
        tvMessage.text = context.getString(id)
    }

    override fun setMessage(text: Text) {
        tvMessage.text = text.content
        text.textStyle?.setToTextView(tvMessage)
    }

    override fun getMessage(): Text {
        return builder.getMessage()
    }

    override fun getMessageText(): String {
        return builder.getMessageText()
    }

    override fun getCancelable(): Boolean {
        return builder.getCancelable()
    }

    private class ActionAdapter(
        private val smoothAlertDialog: SmoothAlertDialog,
        private val actions: ArrayList<ISmoothDialog.Action>
    ) :
        RecyclerView.Adapter<ActionViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
            return ActionViewHolder(parent)
        }

        override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
            val action = actions[position]
            holder.tvAction.text = action.text
            if (action.type == ISmoothDialog.ActionType.DANGER) {
                val resourceId = AttrUtil.getAttrOutTypeValue(
                    holder.itemView.context,
                    R.attr.smoothDialogDangerActionStyle
                ).resourceId
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.tvAction.setTextAppearance(resourceId)
                } else {
                    holder.tvAction.setTextAppearance(holder.itemView.context, resourceId)
                }
            }
            action.textStyle?.setToTextView(holder.tvAction)
            holder.itemView.setOnClickListener {
                action.onActionClickListener?.onClick(it, action, position)
                smoothAlertDialog.dismiss()
            }
        }

        override fun getItemCount(): Int {
            return actions.size
        }

    }


    private class ActionViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.smooth_alert_dialog_action_item, parent, false)
    ) {
        val tvAction: TextView = itemView.findViewById(R.id.tvAction)
    }

    private class ActionListDividerItemDecoration : RecyclerView.ItemDecoration() {
        private val dividerPaint = Paint()
        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            dividerPaint.color = parent.context.getColorCompat(
                AttrUtil.getAttrOutTypeValue(parent.context, R.attr.smoothDialogDividerLineColor)
                    .resourceId
            )
            val childCount = parent.childCount
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight

            for (i in 0 until childCount - 1) {
                val view: View = parent.getChildAt(i)
                val top: Float =
                    view.bottom.toFloat() - AttrUtil.getDimensionAttrValue(
                        parent.context,
                        R.attr.smoothDialogDividerLineWidth
                    )
                val bottom: Float = view.bottom.toFloat()
                c.drawRect(left.toFloat(), top, right.toFloat(), bottom, dividerPaint)
            }
        }
    }

}