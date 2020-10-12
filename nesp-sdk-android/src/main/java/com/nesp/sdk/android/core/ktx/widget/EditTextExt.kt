package com.nesp.sdk.android.core.ktx.widget

import android.text.TextWatcher
import android.widget.EditText

/**
 *
 * Author: <a href="mailto:jinzhaolu@numob.com">Jack Email:jinzhaolu@numob.com</a>
 * Time: Created 2020/7/9 9:18 AM
 * Project: NespAndroidSdk
 * Description:
 **/

fun EditText.setTextWatcher(text: String, textWatcher: TextWatcher) {
    if (this.tag is TextWatcher) this.removeTextChangedListener(this.tag as TextWatcher)
    this.setText(text)
    this.addTextChangedListener(textWatcher)
    this.tag = textWatcher
}