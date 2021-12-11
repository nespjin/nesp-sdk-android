package com.nesp.sdk.android.navigation

import android.app.Activity
import java.lang.ref.WeakReference
import kotlin.collections.HashMap


object NavigatorManager {

    private val navigators: HashMap<String, WeakReference<Navigator>> = hashMapOf()

    fun regist(navigator: Navigator) {
        if (navigator.getDest() == null) return
        navigators[navigator.getDest()!!.name] = WeakReference(navigator)
    }

    fun unregist(navigator: Navigator) {
        if (navigator.getDest() == null) return
        navigators.remove(navigator.getDest()!!.name)
    }


    fun getNavigatorByDest(dest: Activity): Navigator? {
        return navigators[dest.javaClass.name]?.get()
    }
}