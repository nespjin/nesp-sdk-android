package com.nesp.sdk.android.smooth.app

import android.content.DialogInterface
import android.view.View
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import com.nesp.sdk.android.text.Text
import com.nesp.sdk.android.text.TextStyle

/**
 *
 * @@author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/13 3:41 PM
 * Project: NespAndroidSdk
 * Description:
 **/
interface ISmoothDialog {

    fun addAction(action: Action)

    fun addAction(index: Int, action: Action)

    fun addActions(vararg actions: Action)

    fun addActions(actions: ArrayList<Action>)

    fun removeAction(action: Action)

    fun removeActionAt(index: Int)

    fun getActions(): ArrayList<Action>

    fun setTitle(text: String)

    fun setTitle(@StringRes id: Int)

    fun setTitle(text: Text)

    fun getTitle(): Text

    fun getTitleText(): String

    fun setMessage(text: String)

    fun setMessage(@StringRes id: Int)

    fun setMessage(text: Text)

    fun getMessage(): Text

    fun getMessageText(): String

    fun setCancelable(cancelable: Boolean)

    fun getCancelable(): Boolean

    fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener?)

    class Action {
        var text: String = ""

        @ActionType
        var type: Int = ActionType.NORMAL
        var textStyle: TextStyle? = null
        var onActionClickListener: OnActionClickListener? = null

        constructor(text: String) {
            this.text = text
        }

        constructor(text: String, onActionClickListener: OnActionClickListener) {
            this.text = text
            this.onActionClickListener = onActionClickListener
        }

        constructor(text: String, @ActionType type: Int) {
            this.text = text
            this.type = type
        }

        constructor(text: String, textStyle: TextStyle) {
            this.text = text
            this.textStyle = textStyle
        }

        constructor(
            text: String, @ActionType type: Int, onActionClickListener: OnActionClickListener?
        ) {
            this.text = text
            this.type = type
            this.onActionClickListener = onActionClickListener
        }

        constructor(
            text: String, style: TextStyle,
            onActionClickListener: OnActionClickListener
        ) {
            this.text = text
            this.textStyle = style
            this.onActionClickListener = onActionClickListener

        }

    }

    interface OnActionClickListener {
        fun onClick(view: View, action: Action, position: Int)
    }

    @IntDef(ActionType.NORMAL, ActionType.DANGER)
    annotation class ActionType {
        companion object {
            const val NORMAL = 0
            const val DANGER = 1
        }
    }
}