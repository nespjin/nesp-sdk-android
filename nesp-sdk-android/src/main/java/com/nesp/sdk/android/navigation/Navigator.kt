package com.nesp.sdk.android.navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.io.Serializable
import java.util.*


class Navigator : LifecycleEventObserver {

    private var from: Activity? = null
    private var dest: Class<*>? = null
    private var lifeCycleCallBack: LifeCycleCallBack? = null

    /**
     * [extras] removed when [from] destroy
     */
    private val extras: WeakHashMap<String, Any?> = WeakHashMap()
    private val extrasReturn: WeakHashMap<String, Any?> = WeakHashMap()

    private var activityReqCode = -1

    fun from(from: Activity): Navigator {
        this.from = from
        if (from is AppCompatActivity) {
            from.lifecycle.addObserver(this)
        }
        return this
    }

    fun dest(dest: Class<*>): Navigator {
        this.dest = dest
        return this
    }

    fun withShort(name: String, value: Short): Navigator {
        extras[name] = value
        return this
    }

    fun withByte(name: String, value: Byte): Navigator {
        extras[name] = value
        return this
    }

    fun withInt(name: String, value: Int): Navigator {
        extras[name] = value
        return this
    }

    fun withBoolean(name: String, value: Boolean): Navigator {
        extras[name] = value
        return this
    }

    fun withLong(name: String, value: Long): Navigator {
        extras[name] = value
        return this
    }

    fun withFloat(name: String, value: Float): Navigator {
        extras[name] = value
        return this
    }

    fun withString(name: String, value: String): Navigator {
        extras[name] = value
        return this
    }

    fun withSerializable(name: String, value: Serializable?): Navigator {
        extras[name] = value
        return this
    }

    //    Return

    fun returnShort(name: String, value: Short): Navigator {
        extrasReturn[name] = value
        return this
    }

    fun returnByte(name: String, value: Byte): Navigator {
        extrasReturn[name] = value
        return this
    }

    fun returnInt(name: String, value: Int): Navigator {
        extrasReturn[name] = value
        return this
    }

    fun returnBoolean(name: String, value: Boolean): Navigator {
        extrasReturn[name] = value
        return this
    }

    fun returnLong(name: String, value: Long): Navigator {
        extrasReturn[name] = value
        return this
    }

    fun returnFloat(name: String, value: Float): Navigator {
        extrasReturn[name] = value
        return this
    }

    fun returnString(name: String, value: String): Navigator {
        extrasReturn[name] = value
        return this
    }

    fun returnSerializable(name: String, value: Serializable?): Navigator {
        extrasReturn[name] = value
        return this
    }


    fun requestCode(requestCode: Int): Navigator {
        this.activityReqCode = requestCode
        return this
    }

    fun lifeCycleCallBack(lifeCycleCallBack: LifeCycleCallBack): Navigator {
        this.lifeCycleCallBack = lifeCycleCallBack
        return this
    }

    fun navigation() {
        if (from == null || dest == null) return

        val intent = Intent(from, dest)

        for (extra in extras) {
            if (extra.value is Short) intent.putExtra(extra.key, extra.value as Short)
            if (extra.value is ShortArray?) intent.putExtra(extra.key, extra.value as ShortArray?)
            if (extra.value is Byte) intent.putExtra(extra.key, extra.value as Byte)
            if (extra.value is ByteArray?) intent.putExtra(extra.key, extra.value as ByteArray?)
            if (extra.value is Char) intent.putExtra(extra.key, extra.value as Char)
            if (extra.value is CharArray?) intent.putExtra(extra.key, extra.value as CharArray?)
            if (extra.value is Boolean) intent.putExtra(extra.key, extra.value as Boolean)
            if (extra.value is BooleanArray?) intent.putExtra(extra.key, extra.value as BooleanArray?)
            if (extra.value is Int) intent.putExtra(extra.key, extra.value as Int)
            if (extra.value is IntArray?) intent.putExtra(extra.key, extra.value as IntArray?)
            if (extra.value is Long) intent.putExtra(extra.key, extra.value as Long)
            if (extra.value is LongArray?) intent.putExtra(extra.key, extra.value as LongArray?)
            if (extra.value is Double) intent.putExtra(extra.key, extra.value as Double)
            if (extra.value is DoubleArray?) intent.putExtra(extra.key, extra.value as DoubleArray?)
            if (extra.value is Float) intent.putExtra(extra.key, extra.value as Float)
            if (extra.value is FloatArray?) intent.putExtra(extra.key, extra.value as FloatArray?)
            if (extra.value is String?) intent.putExtra(extra.key, extra.value as String?)
            if (extra.value is Serializable?) intent.putExtra(extra.key, extra.value as Serializable?)
            if (extra.value is Parcelable?) intent.putExtra(extra.key, extra.value as Parcelable?)
            if (extra.value is Bundle) intent.putExtra(extra.key, extra.value as Bundle)
            if (extra.value is CharSequence?) intent.putExtra(extra.key, extra.value as CharSequence?)

            if (extra.value is Array<*>?) {
                val items = extra.value as Array<*>?
                if (items?.isNotEmpty() == true) {
                    if (items[0] is Parcelable) intent.putExtra(extra.key, extra.value as Array<Parcelable>?)
                    if (items[0] is CharSequence) intent.putExtra(extra.key, extra.value as Array<CharSequence>?)
                    if (items[0] is String) intent.putExtra(extra.key, extra.value as Array<String>?)
                }
            }
        }

        if (activityReqCode == -1) {
            from!!.startActivity(intent)
        } else {
            from!!.startActivityForResult(intent, activityReqCode)
            activityReqCode = -1
        }

        NavigatorManager.regist(this)
    }

    fun getDest(): Class<*>? {
        return dest
    }

    fun getValue(name: String): Any? {
        return extras[name]
    }

    fun getReturnValue(name: String): Any? {
        return extrasReturn[name]
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_DESTROY -> onDestroy()
        }
    }

    private fun onDestroy() {
        destroy()
    }

    /**
     * Call when from Activity destroyed
     */
    fun destroy() {
        from = null
        dest = null
        extras.clear()
        extrasReturn.clear()
        activityReqCode = -1
        NavigatorManager.unregist(this)
    }


    interface LifeCycleCallBack {
        fun onDestroy()
    }
}