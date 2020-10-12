package com.nesp.sdk.android.smooth.app

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.*

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 12:31
 * Project: NespAndroidSdk
 **/
interface ISmoothActionBar {

    fun setBackIndicatorDrawable(drawable: Drawable)

    fun setBackIndicatorDrawable(@DrawableRes id: Int)

    fun setBackIndicatorDrawable(stateListDrawable: StateListDrawable)

    fun setBackIndicatorColor(@ColorInt color: Int)

    fun setBackIndicatorColorRes(@ColorRes id: Int)

    fun setBackIndicatorColorStateList(colorStateList: ColorStateList)

    fun setBackIndicatorColorStateList(id: Int)

    fun setOnBackIndicatorClickListener(onBackIndicatorClickListener: View.OnClickListener)

    fun setLeftAction(text: CharSequence, onLeftActionClickListener: View.OnClickListener)

    fun setLeftAction(@StringRes id: Int, onLeftActionClickListener: View.OnClickListener)

    fun setLeftActionText(text: CharSequence)

    fun setLeftActionText(@StringRes id: Int)

    fun setLeftActionTextColor(@ColorInt color: Int)

    fun setLeftActionTextColorRes(@ColorRes id: Int)

    fun setLeftActionTextColorStateList(colorStateList: ColorStateList)

    fun setLeftActionTextColorStateList(id: Int)

    fun setOnLeftActionClickListener(onLeftActionClickListener: View.OnClickListener)

    fun setRightAction(text: CharSequence, onRightActionClickListener: View.OnClickListener)

    fun setRightAction(@StringRes id: Int, onRightActionClickListener: View.OnClickListener)

    fun setRightActionText(text: CharSequence)

    fun setRightActionText(@StringRes id: Int)

    fun setRightActionTextColor(@ColorInt color: Int)

    fun setRightActionTextColorRes(@ColorRes id: Int)

    fun setRightActionTextColorStateList(colorStateList: ColorStateList)

    fun setRightActionTextColorStateList(id: Int)

    fun setOnRightActionClickListener(onRightActionClickListener: View.OnClickListener)

    fun setTitle(text: CharSequence)

    fun setTitle(@StringRes id: Int)

    fun setTitleTextColor(@ColorInt color: Int)

    fun setTitleTextColorRes(@ColorRes id: Int)

    fun setTitleTextColorStateList(colorStateList: ColorStateList)

    fun setTitleTextColorStateList(id: Int)

    fun setSubtitle(text: CharSequence)

    fun setSubtitle(@StringRes id: Int)

    fun setSubtitleTextColor(@ColorInt color: Int)

    fun setSubtitleTextColorRes(@ColorRes id: Int)

    fun setSubtitleTextColorStateList(colorStateList: ColorStateList)

    fun setSubtitleTextColorStateList(id: Int)

    fun createRightMenu(@MenuRes id: Int, menuInflater: MenuInflater)

    fun setMenu(menu: Menu)

    fun setOnRightMenuItemClickListener(onMenuItemClickListener: MenuItem.OnMenuItemClickListener)

    fun setMenuIndicatorDrawable(drawable: Drawable)

    fun setMenuIndicatorDrawable(@DrawableRes id: Int)

    fun setMenuIndicatorDrawable(stateListDrawable: StateListDrawable)

    fun setMenuIndicatorColor(@ColorInt color: Int)

    fun setMenuIndicatorColorRes(@ColorRes id: Int)

    fun setMenuIndicatorColorStateList(colorStateList: ColorStateList)

    fun setMenuIndicatorColorStateList(id: Int)

    fun getInitActionBarHeight(): Float

    fun getRealActionBarHeight(): Float
}