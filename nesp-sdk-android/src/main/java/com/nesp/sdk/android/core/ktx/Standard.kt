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

package com.nesp.sdk.android.core.ktx

import com.google.gson.Gson


/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/10/11 8:07
 * Project: NespAndroidSdk
 **/


/********************************     Boolean     **************************************/

fun Boolean.toInt() = if (this) 1 else 0

fun Boolean.toBooleanString() = if (this) "true" else "false"

/********************************     String     **************************************/

fun String.toBoolean() = this == "true"

fun String.isUrl() = this.startsWith("http://", true) || this.startsWith("https://", true)

fun String.removeUnusedDecimalPoint(): String {
    var str = this
    if (str.indexOf(".") > 0) {
        str = str.replace("0+?$".toRegex(), "")
        str = str.replace("[.]$".toRegex(), "")
    }
    return str
}

/********************************     ArrayList<>     **************************************/

fun <E> List<E>.toArrayString(): String {
    var result = ""
    for (i in this) {
        result += i
        if (this.indexOf(i) != this.lastIndex) result += ","
    }
    return result
}

/********************************     MutableList<>     **************************************/

fun <E> MutableList<E>.toArrayList(): ArrayList<E> {
    val result = arrayListOf<E>()
    result.addAll(this)
    return result
}

/********************************     Int     **************************************/

fun Int.toBoolean() = this == 1


val <T : Any> T.TAG: String
    get() {
        return javaClass.simpleName
    }


fun <T : Any?> T.toJson(): String {
    return Gson().toJson(this) as String
}


fun <T : Any> T.fromJson(json: String): T {
    return Gson().fromJson(json, this::class.java)
}


/**
 * 三目
 */
fun <T : Any> threeEyes(condition: Boolean, ifTrue: T, ifFalse: T): T =
    if (condition) ifTrue else ifFalse

fun <T : Any> T.threeEyes(condition: Boolean, ifTrue: T.() -> Unit, ifFalse: T.() -> Unit): T {
    if (condition) ifTrue() else ifFalse()
    return this
}

inline fun <T : Any> T.threeEyes(
    condition: T.() -> Boolean, noinline ifTrue: (T.() -> Unit)? = null,
    noinline ifFalse: (T.() -> Unit)? = null
): T {
    if (condition()) {
        ifTrue?.invoke(this)
    } else {
        ifFalse?.invoke(this)
    }
    return this
}

inline fun <T : Any?> T.ifNotNull(run: (T) -> Unit): T {
    if (this != null) run(this)
    return this
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> Any.cast(): T {
    return this as T
}


@Suppress("UNCHECKED_CAST")
fun <T : Any> Any?.castIfNull(ifNull: T): T {
    if (this == null) return ifNull
    return this as T
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> Any?.castCanNull(): T? {
    if (this == null) return null
    return this as T
}






