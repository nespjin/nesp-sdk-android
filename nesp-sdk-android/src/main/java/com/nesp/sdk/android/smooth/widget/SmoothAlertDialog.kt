package com.nesp.sdk.android.smooth.widget

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nesp.sdk.android.R
import com.nesp.sdk.android.text.Text
import com.nesp.sdk.android.text.TextStyle
import com.nesp.sdk.android.util.AttrUtil
import kotlinx.android.synthetic.main.smooth_action_menu_list.*

/**
 *
 * Author: <a href="mailto:jinzhaolu@numob.com">Jack Email:jinzhaolu@numob.com</a>
 * Time: Created 2020/10/13 12:45 PM
 * Project: NespAndroidSdk
 * Description:
 **/
class SmoothAlertDialog : Dialog, ISmoothDialog {

    class Builder : ISmoothDialog {

        internal var context: Context? = null

        @StyleRes
        internal var themeResId: Int = 0
        internal var cancelable: Boolean = true
        internal var onCancelListener: DialogInterface.OnCancelListener? = null
        internal val actions: ArrayList<ISmoothDialog.Action> = arrayListOf()
        internal var title: Text = Text.empty()
        internal var message: Text = Text.empty()

        @ConstructorType
        private var constructorType = ConstructorType.ONE

        @IntDef(ConstructorType.ONE, ConstructorType.TWO, ConstructorType.THREE)
        annotation class ConstructorType {
            companion object {
                const val ONE = 0
                const val TWO = 1
                const val THREE = 2
            }
        }

        constructor(context: Context) {
            this.context = context
            this.constructorType = ConstructorType.ONE
        }

        constructor(context: Context, @StyleRes themeResId: Int) {
            this.context = context
            this.themeResId = themeResId
            this.constructorType = ConstructorType.TWO
        }

        constructor(
            context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?
        ) {
            this.context = context
            this.cancelable = cancelable
            this.onCancelListener = cancelListener
            this.constructorType = ConstructorType.THREE
        }

        override fun addAction(action: ISmoothDialog.Action) {
            this.actions.add(action)
        }

        override fun addAction(index: Int, action: ISmoothDialog.Action) {
            this.actions.add(index, action)
        }

        override fun addActions(vararg actions: ISmoothDialog.Action) {
            this.actions.addAll(actions)
        }

        override fun addActions(actions: ArrayList<ISmoothDialog.Action>) {
            this.actions.addAll(actions)
        }

        override fun removeAction(action: ISmoothDialog.Action) {
            this.actions.remove(action)
        }

        override fun removeActionAt(index: Int) {
            this.actions.removeAt(index)
        }

        override fun getActions(): ArrayList<ISmoothDialog.Action> {
            return this.actions
        }

        override fun setTitle(text: String) {
            this.title = Text(text)
        }

        override fun setTitle(id: Int) {
            this.title = Text(context!!.getString(id))
        }

        override fun setTitle(text: Text) {
            this.title = text
        }

        override fun getTitle(): Text {
            return title
        }

        override fun getTitleText(): String {
            return title.content
        }

        override fun setMessage(text: String) {
            this.message = Text(text)
        }

        override fun setMessage(id: Int) {
            this.message = Text(context!!.getString(id))
        }

        override fun setMessage(text: Text) {
            this.message = text
        }

        override fun getMessage(): Text {
            return this.message
        }

        override fun getMessageText(): String {
            return this.message.content
        }

        fun setTheme(id: Int) {
            this.themeResId = id
        }

        override fun setCancelable(cancelable: Boolean) {
            this.cancelable = cancelable
        }

        override fun getCancelable(): Boolean {
            return this.cancelable
        }

        override fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener?) {
            this.onCancelListener = onCancelListener
        }

        fun create(): SmoothAlertDialog {
            return when (constructorType) {
                ConstructorType.ONE -> SmoothAlertDialog(context!!, this)
                ConstructorType.TWO -> {
                    if (themeResId == 0) {
                        themeResId =
                            AttrUtil.getAttrOutTypeValue(context!!, R.attr.smoothDialogStyle)
                                .resourceId
                    }
                    SmoothAlertDialog(context!!, themeResId, this)
                }
                ConstructorType.THREE -> SmoothAlertDialog(
                    context!!, cancelable, onCancelListener, this
                )
                else -> SmoothAlertDialog(context!!, this)
            }
        }
    }

    private var builder: Builder
    private val tvTitle: TextView by lazy { findViewById(R.id.tvTitle) }
    private val tvMessage: TextView by lazy { findViewById(R.id.tvMessage) }
    private val actionsRecyclerView: RecyclerView by lazy { findViewById(R.id.actionsRecyclerView) }
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
        setContentView(R.layout.alert_dialog_smooth)
        initActions()
    }

    private fun initActions() {
        val orientation =
            if (builder.actions.size > 2) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
        recyclerView.layoutManager = LinearLayoutManager(context, orientation, false)
        actionAdapter = ActionAdapter(builder.actions)
        recyclerView.adapter = actionAdapter
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
        return builder.actions
    }

    override fun setTitle(text: String) {
        tvTitle.text = text
    }

    override fun setTitle(text: Text) {
        tvTitle.text = text.content
        if (text.textStyle != null) {
            if (text.textStyle!!.textSize != TextStyle.TEXT_SIZE_NONE) {
                tvTitle.textSize = text.textStyle!!.textSize
            }

            if (text.textStyle!!.textColor != TextStyle.TEXT_COLOR_NONE) {
                tvTitle.setTextColor(text.textStyle!!.textColor)
            }

            if (text.textStyle!!.textColorStateList != null) {
                tvTitle.setTextColor(text.textStyle!!.textColorStateList)
            }
        }
    }

    override fun getTitle(): Text {
        TODO("Not yet implemented")
    }

    override fun getTitleText(): String {
        TODO("Not yet implemented")
    }

    override fun setMessage(text: String) {
        TODO("Not yet implemented")
    }

    override fun setMessage(id: Int) {
        TODO("Not yet implemented")
    }

    override fun setMessage(text: Text) {
        TODO("Not yet implemented")
    }

    override fun getMessage(): Text {
        TODO("Not yet implemented")
    }

    override fun getMessageText(): String {
        TODO("Not yet implemented")
    }

    override fun getCancelable(): Boolean {
        TODO("Not yet implemented")
    }

    private class ActionAdapter(private val actions: ArrayList<ISmoothDialog.Action>) :
        RecyclerView.Adapter<ActionViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
            return ActionViewHolder(parent)
        }

        override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
            val action = actions[position]
            holder.tvAction.text = action.text
            if (action.textStyle != null) {
                if (action.textStyle!!.textSize != TextStyle.TEXT_SIZE_NONE) {
                    holder.tvAction.textSize = action.textStyle!!.textSize
                }

                if (action.textStyle!!.textColor != TextStyle.TEXT_COLOR_NONE) {
                    holder.tvAction.setTextColor(action.textStyle!!.textColor)
                }

                if (action.textStyle!!.textColorStateList != null) {
                    holder.tvAction.setTextColor(action.textStyle!!.textColorStateList)
                }
            }

            holder.itemView.setOnClickListener {
                action.onActionClickListener?.onClick(it, action, position)
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
        val tvAction = itemView.findViewById<TextView>(R.id.tvAction)
    }

}