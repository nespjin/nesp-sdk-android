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

package com.nesp.sdk.android.smooth.app

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.*
import com.github.mmin18.widget.RealtimeBlurView
import com.nesp.sdk.android.smooth.widget.SmoothActionMenuView

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/10/11 12:31
 * Project: NespAndroidSdk
 **/
interface ISmoothActionBar {

    fun setBackgroundColor(@ColorInt color: Int)

    fun setBackgroundColorRes(@ColorRes id: Int)

    fun setBackgroundColorStateList(colorStateList: ColorStateList)

    fun setBackgroundColorStateList(id: Int)

    fun disableRealtimeBlur()

    fun getRealtimeBlurView(): RealtimeBlurView

    fun setBackIndicatorDrawable(drawable: Drawable)

    fun setBackIndicatorDrawable(@DrawableRes id: Int)

    fun setBackIndicatorDrawable(stateListDrawable: StateListDrawable)

    fun setBackIndicatorColor(@ColorInt color: Int)

    fun setBackIndicatorColorRes(@ColorRes id: Int)

    fun setBackIndicatorColorStateList(colorStateList: ColorStateList)

    fun setBackIndicatorColorStateList(id: Int)

    fun getBackIndicator(): ImageView

    fun backIndicatorIsHidden(): Boolean

    fun hideBackIndicator()

    fun showBackIndicator()

    fun toggleBackIndicatorHidden()

    fun setOnBackIndicatorClickListener(onBackIndicatorClickListener: View.OnClickListener)

    fun getLeftActionContainer(): LinearLayout

    fun hideLeftActionContainer()

    fun showLeftActionContainer()

    fun getLeftActionInnerContainer(): FrameLayout

    fun hideLeftActionInnerContainer()

    fun showLeftActionInnerContainer()

    fun setLeftAction(text: CharSequence, onLeftActionClickListener: View.OnClickListener)

    fun setLeftAction(
        @StringRes @DrawableRes id: Int,
        onLeftActionClickListener: View.OnClickListener
    )

    fun setLeftAction(drawable: Drawable, onLeftActionClickListener: View.OnClickListener)

    fun setLeftAction(
        stateListDrawable: StateListDrawable,
        onLeftActionClickListener: View.OnClickListener
    )

    fun setLeftActionText(text: CharSequence)

    fun setLeftActionText(@StringRes id: Int)

    fun setLeftActionDrawable(drawable: Drawable)

    fun setLeftActionDrawable(@DrawableRes id: Int)

    fun setLeftActionDrawable(stateListDrawable: StateListDrawable)

    fun setLeftActionTextColor(@ColorInt color: Int)

    fun setLeftActionTextColorRes(@ColorRes id: Int)

    fun setLeftActionTextColorStateList(colorStateList: ColorStateList)

    fun setLeftActionTextColorStateList(id: Int)

    fun setOnLeftActionClickListener(onLeftActionClickListener: View.OnClickListener)

    fun getLeftActionTextView(): TextView

    fun getLeftActionImageView(): ImageView

    fun hideLeftAction()

    fun showLeftAction()

    fun leftActionIsHidden(): Boolean

    fun toggleLeftActionHidden()

    fun getRightActionContainer(): LinearLayout

    fun hideRightActionContainer()

    fun showRightActionContainer()

    fun getRightActionInnerContainer(): FrameLayout

    fun hideRightActionInnerContainer()

    fun showRightActionInnerContainer()

    fun setRightAction(text: CharSequence, onRightActionClickListener: View.OnClickListener)

    fun setRightAction(
        @StringRes @DrawableRes id: Int,
        onRightActionClickListener: View.OnClickListener
    )

    fun setRightAction(drawable: Drawable, onRightActionClickListener: View.OnClickListener)

    fun setRightAction(
        stateListDrawable: StateListDrawable,
        onRightActionClickListener: View.OnClickListener
    )

    fun setRightActionText(text: CharSequence)

    fun setRightActionText(@StringRes id: Int)

    fun setRightActionDrawable(drawable: Drawable)

    fun setRightActionDrawable(@DrawableRes id: Int)

    fun setRightActionDrawable(stateListDrawable: StateListDrawable)

    fun setRightActionTextColor(@ColorInt color: Int)

    fun setRightActionTextColorRes(@ColorRes id: Int)

    fun setRightActionTextColorStateList(colorStateList: ColorStateList)

    fun setRightActionTextColorStateList(id: Int)

    fun setOnRightActionClickListener(onRightActionClickListener: View.OnClickListener)

    fun getRightActionTextView(): TextView

    fun getRightActionImageView(): ImageView

    fun hideRightAction()

    fun showRightAction()

    fun rightActionIsHidden(): Boolean

    fun toggleRightActionHidden()

    fun setCustomActionBarView(view: View)

    fun setCustomActionBarView(@LayoutRes id: Int)

    fun getCustomViewContainer(): FrameLayout

    fun hideCustomViewContainer()

    fun showCustomViewContainer()

    fun getCenterTitleContainer(): LinearLayout

    fun hideCenterTitleContainer()

    fun showCenterTitleContainer()

    fun setTitle(text: CharSequence)

    fun setTitle(@StringRes id: Int)

    fun setTitleTextColor(@ColorInt color: Int)

    fun setTitleTextColorRes(@ColorRes id: Int)

    fun setTitleTextColorStateList(colorStateList: ColorStateList)

    fun setTitleTextColorStateList(id: Int)

    fun getTitleView(): TextView

    fun hideTitle()

    fun showTitle()

    fun titleIsHidden(): Boolean

    fun toggleTitleHidden()

    fun setSubtitle(text: CharSequence)

    fun setSubtitle(@StringRes id: Int)

    fun setSubtitleTextColor(@ColorInt color: Int)

    fun setSubtitleTextColorRes(@ColorRes id: Int)

    fun setSubtitleTextColorStateList(colorStateList: ColorStateList)

    fun setSubtitleTextColorStateList(id: Int)

    fun getSubtitleView(): TextView

    fun hideSubtitle()

    fun showSubtitle()

    fun subtitleViewIsHidden(): Boolean

    fun toggleSubtitleHidden()

    fun hideAllTitle()

    fun showAllTitle()

    fun createRightMenu(@MenuRes id: Int, menuInflater: MenuInflater)

    fun setMenu(menu: Menu)

    fun getMenuView(): SmoothActionMenuView

    fun hideMenu()

    fun showMenu()

    fun menuIsHidden(): Boolean

    fun toggleMenuHidden()

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

    fun showLeftActionBarActivityIndicator()

    fun hideLeftActionBarActivityIndicator()

    fun showCenterActionBarActivityIndicator()

    fun hideCenterActionBarActivityIndicator()

    fun showRightActionBarActivityIndicator()

    fun hideRightActionBarActivityIndicator()
}