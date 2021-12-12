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

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.cardview.widget.CardView
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mmin18.widget.RealtimeBlurView
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.content.getColorCompat
import com.nesp.sdk.android.core.ktx.widget.goneIfTextEmpty
import com.nesp.sdk.android.text.Text
import com.nesp.sdk.android.utils.AttrUtil

/**
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/19 2:18 PM
 * Project: NespAndroidSdkSample
 * Description:
 **/
class SmoothActionSheetDialog : Dialog, ISmoothActionSheetDialog {

    class Builder {

        internal var context: Context? = null

        @StyleRes
        internal var themeResId: Int = 0

        internal var cancelable: Boolean = true
        internal var onCancelListener: DialogInterface.OnCancelListener? = null
        internal var actions: ArrayList<ISmoothActionSheetDialog.Action> = arrayListOf()
        internal var title: Text = Text.empty()
        internal var description: Text = Text.empty()
        internal var cancel: ISmoothActionSheetDialog.Action? = null

        constructor(context: Context) : this(context, 0)

        constructor(context: Context, @StyleRes themeResId: Int) : this(context, true, null) {
            this.context = context
            this.themeResId = themeResId
        }

        constructor(
            context: Context, cancelable: Boolean,
            onCancelListener: DialogInterface.OnCancelListener?
        ) {
            this.context = context
            this.cancelable = cancelable
            this.onCancelListener = onCancelListener
        }

        fun addAction(action: ISmoothActionSheetDialog.Action): Builder {
            this.actions.add(action)
            return this
        }

        fun addAction(index: Int, action: ISmoothActionSheetDialog.Action): Builder {
            this.actions.add(index, action)
            return this
        }

        fun addActions(vararg actions: ISmoothActionSheetDialog.Action): Builder {
            this.actions.addAll(actions)
            return this
        }

        fun addActions(actions: ArrayList<ISmoothActionSheetDialog.Action>): Builder {
            this.actions.addAll(actions)
            return this
        }

        fun removeAction(action: ISmoothActionSheetDialog.Action): Builder {
            this.actions.remove(action)
            return this
        }

        fun removeActionAt(index: Int): Builder {
            this.actions.removeAt(index)
            return this
        }

        fun getActions(): ArrayList<ISmoothActionSheetDialog.Action> {
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
            return this.title
        }

        fun getTitleText(): String {
            return title.content
        }

        fun setDescription(text: String): Builder {
            this.description = Text(text)
            return this
        }

        fun setDescription(id: Int): Builder {
            this.description = Text(context!!.getString(id))
            return this
        }

        fun setDescription(text: Text): Builder {
            this.description = text
            return this
        }

        fun getDescription(): Text {
            return this.description
        }

        fun getDescriptionText(): String {
            return description.content
        }

        fun setCancel(action: ISmoothActionSheetDialog.Action): Builder {
            this.cancel = action
            return this
        }

        fun setCancel(@StringRes id: Int): Builder {
            this.cancel = ISmoothActionSheetDialog.Action(context!!.getString(id))
            return this
        }

        fun setCancel(text: String): Builder {
            this.cancel = ISmoothActionSheetDialog.Action(text)
            return this
        }


        fun setCancel(
            @StringRes id: Int,
            onActionClickListener: ISmoothActionSheetDialog.OnActionClickListener?
        ): Builder {
            this.cancel =
                ISmoothActionSheetDialog.Action(context!!.getString(id), onActionClickListener)
            return this
        }

        fun setCancel(
            text: String,
            onActionClickListener: ISmoothActionSheetDialog.OnActionClickListener?
        ): Builder {
            this.cancel =
                ISmoothActionSheetDialog.Action(text, onActionClickListener)
            return this
        }


        fun getCancel(): ISmoothActionSheetDialog.Action? {
            return this.cancel
        }

        fun getCancelText(): String {
            return this.cancel?.text ?: ""
        }

        fun setCancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        fun getCancelable(): Boolean {
            return this.cancelable
        }

        fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener): Builder {
            this.onCancelListener = onCancelListener
            return this
        }

        fun create(): SmoothActionSheetDialog {
            if (themeResId == 0) {
                themeResId =
                    AttrUtil.getAttrOutTypeValue(context!!, R.attr.smoothActionSheetDialogStyle)
                        .resourceId
            }
            val smoothActionSheetDialog = SmoothActionSheetDialog(context!!, themeResId, this)
            smoothActionSheetDialog.setCancelable(cancelable)
            smoothActionSheetDialog.setOnCancelListener(onCancelListener)
            return smoothActionSheetDialog
        }

    }


    private val builder: Builder

    private lateinit var cardViewContent: CardView
    private lateinit var cardViewCancel: CardView
    private lateinit var realtimeBlurViewContent: RealtimeBlurView
    private lateinit var realtimeBlurViewCancel: RealtimeBlurView
    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var actionsRecyclerView: RecyclerView
    private lateinit var tvActionCancel: TextView
    private lateinit var vActionDivider: View
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
        window!!.decorView.setPadding(0, 0, 0, 15)
        window!!.setGravity(Gravity.BOTTOM)
        window?.decorView?.setBackgroundColor(Color.TRANSPARENT)

        setContentView(R.layout.smooth_action_sheet_dialog)

        cardViewContent = findViewById(R.id.cardViewContent) as CardView
        cardViewCancel = findViewById(R.id.cardViewCancel) as CardView
        realtimeBlurViewContent = findViewById(R.id.realtimeBlurViewContent) as RealtimeBlurView
        realtimeBlurViewCancel = findViewById(R.id.realtimeBlurViewCancel) as RealtimeBlurView
        tvTitle = findViewById(R.id.tvTitle) as TextView
        tvDescription = findViewById(R.id.tvDescription) as TextView
        actionsRecyclerView = findViewById(R.id.actionsRecyclerView) as RecyclerView
        tvActionCancel = findViewById(R.id.tvActionCancel) as TextView
        vActionDivider = findViewById(R.id.vActionDivider) as View

        tvTitle.text = builder.title.content
        builder.title.textStyle?.setToTextView(tvTitle)

        tvDescription.text = builder.description.content
        builder.description.textStyle?.setToTextView(tvDescription)

        tvTitle.goneIfTextEmpty()
        tvDescription.goneIfTextEmpty()

        vActionDivider.isGone =
            builder.title.content.isEmpty() && builder.description.content.isEmpty()

        initActions()

        cardViewContent.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        realtimeBlurViewContent.layoutParams.apply {
            height = cardViewContent.measuredHeight
        }

        cardViewCancel.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        realtimeBlurViewCancel.layoutParams.apply {
            height = cardViewCancel.measuredHeight
        }

        if (builder.cancel?.text?.isNotEmpty() == true) {
            tvActionCancel.text = builder.cancel!!.text
        }
        builder.cancel?.textStyle?.setToTextView(tvActionCancel)
        tvActionCancel.setOnClickListener {
            builder.cancel
            dismiss()
        }

    }

    override fun show() {
        super.show()
        setDialogWidth()
    }

    private fun setDialogWidth() {
        val dm = DisplayMetrics()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            context.display?.getMetrics(dm)
//        } else {
//            val windowManager = (builder.context as Activity).windowManager
//            windowManager.defaultDisplay.getMetrics(dm)
//        }
        // TODO: Resolve for R
        val windowManager = (builder.context as Activity).windowManager
        windowManager.defaultDisplay.getMetrics(dm)

        val lp = window!!.attributes
        lp.width = dm.widthPixels * 99 / 100
        window!!.attributes = lp
    }

    private fun initActions() {
        actionsRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        actionAdapter = ActionAdapter(this, builder.actions)
        actionsRecyclerView.adapter = actionAdapter
        actionsRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        actionsRecyclerView.addItemDecoration(ActionListDividerItemDecoration())
    }

    private fun refreshActions() {
        actionAdapter?.notifyDataSetChanged()
    }

    override fun addAction(action: ISmoothActionSheetDialog.Action) {
        builder.addAction(action)
        refreshActions()
    }

    override fun addAction(index: Int, action: ISmoothActionSheetDialog.Action) {
        builder.addAction(index, action)
        refreshActions()
    }

    override fun addActions(vararg actions: ISmoothActionSheetDialog.Action) {
        builder.actions.addAll(actions)
        refreshActions()
    }

    override fun addActions(actions: ArrayList<ISmoothActionSheetDialog.Action>) {
        builder.addActions(actions)
        refreshActions()
    }

    override fun removeAction(action: ISmoothActionSheetDialog.Action) {
        builder.removeAction(action)
        refreshActions()
    }

    override fun removeActionAt(index: Int) {
        builder.removeActionAt(index)
        refreshActions()
    }

    override fun getActions(): ArrayList<ISmoothActionSheetDialog.Action> {
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

    override fun setDescription(text: String) {
        tvDescription.text = text
    }

    override fun setDescription(id: Int) {
        tvDescription.text = context.getString(id)
    }

    override fun setDescription(text: Text) {
        tvDescription.text = text.content
        text.textStyle?.setToTextView(tvDescription)
    }

    override fun getDescription(): Text {
        return builder.getDescription()
    }

    override fun getDescriptionText(): String {
        return builder.getDescriptionText()
    }

    override fun setCancel(action: ISmoothActionSheetDialog.Action) {
        tvActionCancel.text = action.text
        action.textStyle?.setToTextView(tvActionCancel)
    }

    override fun setCancel(text: String) {
        tvActionCancel.text = text
    }

    override fun setCancel(id: Int) {
        tvActionCancel.text = context.getString(id)
    }

    override fun setCancel(
        id: Int,
        onActionClickListener: ISmoothActionSheetDialog.OnActionClickListener?
    ) {
        val text = context.getString(id)
        tvActionCancel.text = text
        if (onActionClickListener != null) {
            tvActionCancel.setOnClickListener {
                onActionClickListener.onClick(
                    it,
                    ISmoothActionSheetDialog.Action(text, onActionClickListener), -1
                )
            }
        }
    }

    override fun setCancel(
        text: String,
        onActionClickListener: ISmoothActionSheetDialog.OnActionClickListener?
    ) {
        tvActionCancel.text = text
        if (onActionClickListener != null) {
            tvActionCancel.setOnClickListener {
                onActionClickListener.onClick(
                    it,
                    ISmoothActionSheetDialog.Action(text, onActionClickListener), -1
                )
            }
        }
    }


    override fun getCancel(): ISmoothActionSheetDialog.Action? {
        return builder.getCancel()
    }

    override fun getCancelText(): String {
        return builder.getCancelText()
    }

    override fun getCancelable(): Boolean {
        return builder.getCancelable()
    }


    @Suppress("DEPRECATION")
    private class ActionAdapter(
        private val smoothActionSheetDialog: SmoothActionSheetDialog,
        private val actions: ArrayList<ISmoothActionSheetDialog.Action>
    ) : RecyclerView.Adapter<ActionViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
            return ActionViewHolder(parent)
        }

        override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
            val action = actions[position]
            holder.tvAction.text = action.text
            if (action.type == ISmoothActionSheetDialog.ActionType.DANGER) {
                val resourceId = AttrUtil.getAttrOutTypeValue(
                    holder.itemView.context,
                    R.attr.smoothActionSheetDialogDangerActionStyle
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
                smoothActionSheetDialog.dismiss()
            }
        }

        override fun getItemCount(): Int {
            return actions.size
        }

    }


    private class ActionViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.smooth_action_sheet_dialog_action_item, parent, false)
    ) {
        val tvAction: TextView = itemView.findViewById(R.id.tvAction) as TextView
    }

    private class ActionListDividerItemDecoration : RecyclerView.ItemDecoration() {
        private val dividerPaint = Paint()
        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            dividerPaint.color = parent.context.getColorCompat(
                AttrUtil.getAttrOutTypeValue(
                    parent.context,
                    R.attr.smoothActionSheetDialogDividerLineColor
                ).resourceId
            )
            val childCount = parent.childCount
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight

            for (i in 0 until childCount - 1) {
                val view: View = parent.getChildAt(i)
                val top: Float =
                    view.bottom.toFloat() - AttrUtil.getDimensionAttrValue(
                        parent.context,
                        R.attr.smoothActionSheetDialogDividerLineWidth
                    )
                val bottom: Float = view.bottom.toFloat()
                c.drawRect(left.toFloat(), top, right.toFloat(), bottom, dividerPaint)
            }
        }
    }

}