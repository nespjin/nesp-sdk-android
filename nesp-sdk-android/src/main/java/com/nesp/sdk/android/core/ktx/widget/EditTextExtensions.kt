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

package com.nesp.sdk.android.core.ktx.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 *
 * @@author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/7/9 9:18 AM
 * Project: NespAndroidSdk
 * Description:
 **/

fun EditText.autoShowSoftInputFromWindow() {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, 0)
}

fun EditText.autoHideSoftInputFromWindow() {
    clearFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}

fun EditText.addEditTextChangedListener(editTextTextWatcher: EditTextTextWatcher) {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            editTextTextWatcher.afterTextChanged(this@addEditTextChangedListener, s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            editTextTextWatcher.beforeTextChanged(this@addEditTextChangedListener, s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            editTextTextWatcher.onTextChanged(this@addEditTextChangedListener, s, start, before, count)
        }

    }
    addTextChangedListener(textWatcher)
}

fun EditText.setTextWatcher(text: String, textWatcher: TextWatcher) {
    if (this.tag is TextWatcher) this.removeTextChangedListener(this.tag as TextWatcher)
    this.setText(text)
    this.addTextChangedListener(textWatcher)
    this.tag = textWatcher
}

interface EditTextTextWatcher  {

    fun beforeTextChanged(editText: EditText, s: CharSequence?, start: Int, count: Int, after: Int)

    fun onTextChanged(editText: EditText, s: CharSequence?, start: Int, before: Int, count: Int)

    fun afterTextChanged(editText: EditText, s: Editable?)

}