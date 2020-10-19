package com.nesp.sdk.android.smooth.app

import android.view.View
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import com.nesp.sdk.android.text.Text
import com.nesp.sdk.android.text.TextStyle

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/18 19:19
 * Project: NespAndroidSdk
 **/
interface ISmoothActionSheetDialog {

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

    fun setDescription(text: String)

    fun setDescription(@StringRes id: Int)

    fun setDescription(text: Text)

    fun getDescription(): Text

    fun getDescriptionText(): String

    fun setCancelable(cancelable: Boolean)

    fun getCancelable(): Boolean

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
            text: String, textStyle: TextStyle, onActionClickListener: OnActionClickListener?
        ) {
            this.text = text
            this.textStyle = textStyle
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