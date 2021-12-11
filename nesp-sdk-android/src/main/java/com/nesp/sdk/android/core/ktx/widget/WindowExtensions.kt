/*
 *
 *   Copyright (c) 2020  NESP Technology Corporation. All rights reserved.
 *
 *   This program is not free software; you can't redistribute it and/or modify it
 *   without the permit of team manager.
 *
 *   Unless required by applicable law or agreed to in writing.
 *
 *   If you have any questions or if you find a bug,
 *   please contact the author by email or ask for Issues.
 *
 *   Author:JinZhaolu <1756404649@qq.com>
 */

package com.nesp.sdk.android.core.ktx.widget

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorRes

/**
 *
 *
 * @team NESP Technology
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @time: Created 19-7-26 下午1:01
 * @project FishMovie
 **/


/**
 * 设置全屏
 *
 */
fun Window.setFullScreen() {
    setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun Window.setFullCutOutScreen() {
    //适配刘海屏
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//        attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
//        decorView.systemUiVisibility = (decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_FULLSCREEN)
//    }
}

/**
 * 取消全屏
 *
 * @param activity
 */
fun Window.clearFullScreen() {
    clearFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

/**
 * 隐藏虚拟按键，并且全屏
 */
fun Window.hideBottomVirtualKey() {
    //隐藏虚拟按键，并且全屏
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // lower api
        val v = decorView
        v.systemUiVisibility = View.GONE
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //for new api versions.
        val decorView = decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
        decorView.systemUiVisibility = uiOptions
    }
}

fun Window.showBottomVirtualKey() {
    //隐藏虚拟按键，并且全屏
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // lower api
        val v = decorView
        v.systemUiVisibility = View.VISIBLE
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //for new api versions.
        val decorView = decorView
        val uiOptions = View.SYSTEM_UI_FLAG_VISIBLE
        decorView.systemUiVisibility = uiOptions
    }
}

fun Window.setStatusBarColor(@ColorRes colorResId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = context.resources.getColor(colorResId)

        //底部导航栏
        //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
    }

}
