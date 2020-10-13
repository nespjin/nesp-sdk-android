package com.nesp.sdk.android.smooth.widget

import android.content.DialogInterface
import android.view.View
import androidx.annotation.StringRes
import com.nesp.sdk.android.text.Text
import com.nesp.sdk.android.text.TextStyle

/**
 *
 * Author: <a href="mailto:jinzhaolu@numob.com">Jack Email:jinzhaolu@numob.com</a>
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
        var textStyle: TextStyle? = null
        var onActionClickListener: OnActionClickListener? = null

        constructor(text: String, onActionClickListener: OnActionClickListener) {
            this.text = text
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

}