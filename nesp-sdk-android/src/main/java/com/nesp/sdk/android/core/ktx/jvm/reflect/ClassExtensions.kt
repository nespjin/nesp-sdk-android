package com.nesp.sdk.android.core.ktx.jvm.reflect

import java.lang.reflect.Field

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/4/21 1:03
 * Project: NespAndroidSdkSample
 **/

fun Class<*>.getFieldValue(instance: Any, fieldName: String): Any? {
    try {
        val field: Field = this.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(instance)
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
    return null
}

fun Class<*>.setFieldValue(instance: Any, fieldName: String, value: Any) {
    try {
        val field: Field = this.getDeclaredField(fieldName)
        field.isAccessible = true
        field.set(instance, value)
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}