package com.nesp.sdk.android.text

class Text {
    var content: String = ""
    var textStyle: TextStyle? = null

    private constructor()

    constructor(text: String) {
        this.content = text
    }

    constructor(text: String, textStyle: TextStyle) {
        this.content = text
        this.textStyle = textStyle
    }

    companion object {
        fun empty(): Text {
            return Text()
        }
    }
}