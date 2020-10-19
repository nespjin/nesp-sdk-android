package com.nesp.sdk.android.smooth.app

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import androidx.annotation.StyleRes
import com.nesp.sdk.android.text.Text

/**
 *
 * Author: <a href="mailto:jinzhaolu@numob.com">Jack Email:jinzhaolu@numob.com</a>
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
            this.title = text

            return this
        }

        fun getDescription(): Text {
            return this.title
        }

        fun getDescriptionText(): String {
            return title.content
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

    }

    private constructor(context: Context, builder: Builder) : super(context)

    override fun addAction(action: ISmoothActionSheetDialog.Action) {

    }

    override fun addAction(index: Int, action: ISmoothActionSheetDialog.Action) {

    }

    override fun addActions(vararg actions: ISmoothActionSheetDialog.Action) {

    }

    override fun addActions(actions: ArrayList<ISmoothActionSheetDialog.Action>) {

    }

    override fun removeAction(action: ISmoothActionSheetDialog.Action) {

    }

    override fun removeActionAt(index: Int) {

    }

    override fun getActions(): ArrayList<ISmoothActionSheetDialog.Action> {

    }

    override fun setTitle(text: String) {

    }

    override fun setTitle(text: Text) {

    }

    override fun getTitle(): Text {

    }

    override fun getTitleText(): String {

    }

    override fun setDescription(text: String) {

    }

    override fun setDescription(id: Int) {

    }

    override fun setDescription(text: Text) {

    }

    override fun getDescription(): Text {

    }

    override fun getDescriptionText(): String {

    }

    override fun getCancelable(): Boolean {

    }


}