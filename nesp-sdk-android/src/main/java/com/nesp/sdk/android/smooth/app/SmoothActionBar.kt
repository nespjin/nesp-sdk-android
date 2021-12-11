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

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.*
import android.widget.*
import androidx.annotation.MenuRes
import androidx.core.view.isVisible
import com.github.mmin18.widget.RealtimeBlurView
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.content.getColorCompat
import com.nesp.sdk.android.core.ktx.content.getColorStateListCompat
import com.nesp.sdk.android.core.ktx.content.getDrawableCompat
import com.nesp.sdk.android.core.ktx.widget.gone
import com.nesp.sdk.android.core.ktx.widget.visible
import com.nesp.sdk.android.databinding.SmoothActionBarBinding
import com.nesp.sdk.android.smooth.widget.SmoothActionMenuView
import com.nesp.sdk.android.utils.AttrUtil
import com.nesp.sdk.android.utils.WindowUtil

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/10/11 9:10
 * Project: NespAndroidSdk
 **/
class SmoothActionBar : RelativeLayout, ISmoothActionBar {

    private var viewBinding: SmoothActionBarBinding =
        SmoothActionBarBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    override fun setBackgroundColor(color: Int) {
        this.viewBinding.rlSmoothActionBarContainer.setBackgroundColor(color)
    }

    override fun setBackgroundColorRes(id: Int) {
        this.viewBinding.rlSmoothActionBarContainer.setBackgroundColor(context.getColorCompat(id))
    }

    override fun setBackgroundColorStateList(colorStateList: ColorStateList) {
        this.viewBinding.rlSmoothActionBarContainer.backgroundTintList = colorStateList
    }

    override fun setBackgroundColorStateList(id: Int) {
        this.viewBinding.rlSmoothActionBarContainer.backgroundTintList =
            context.getColorStateListCompat(id)
    }

    override fun disableRealtimeBlur() {
        this.viewBinding.realtimeBlurView.setBlurRadius(0F)
        this.viewBinding.realtimeBlurView.gone()
        this.viewBinding.rlSmoothActionBarContainer.removeView(this.viewBinding.realtimeBlurView)
        this.viewBinding.rlSmoothActionBarContainer.setBackgroundColor(
            context.getColorCompat(
                AttrUtil.getAttrOutTypeValue(
                    context,
                    R.attr.smoothActionBarBackground
                ).resourceId
            )
        )
    }

    override fun getRealtimeBlurView(): RealtimeBlurView {
        return this.viewBinding.realtimeBlurView
    }

    override fun setBackIndicatorDrawable(drawable: Drawable) {
        viewBinding.ivBackIndicator.setImageDrawable(drawable)
    }

    override fun setBackIndicatorDrawable(id: Int) {
        viewBinding.ivBackIndicator.setImageResource(id)
    }

    override fun setBackIndicatorDrawable(stateListDrawable: StateListDrawable) {
        viewBinding.ivBackIndicator.setImageState(stateListDrawable.state, false)
    }

    override fun setBackIndicatorColor(color: Int) {
        viewBinding.ivBackIndicator.imageTintList = ColorStateList.valueOf(color)
    }

    override fun setBackIndicatorColorStateList(colorStateList: ColorStateList) {
        viewBinding.ivBackIndicator.imageTintList = colorStateList
    }

    override fun setBackIndicatorColorStateList(id: Int) {
        viewBinding.ivBackIndicator.imageTintList = context.getColorStateListCompat(id)
    }

    override fun getBackIndicator(): ImageView {
        return viewBinding.ivBackIndicator
    }

    override fun backIndicatorIsHidden(): Boolean {
        return !viewBinding.ivBackIndicator.isVisible
    }

    override fun hideBackIndicator() {
        hideView(viewBinding.ivBackIndicator)
    }

    override fun showBackIndicator() {
        showView(viewBinding.ivBackIndicator)
    }

    override fun toggleBackIndicatorHidden() {
        if (backIndicatorIsHidden()) {
            showBackIndicator()
        } else {
            hideBackIndicator()
        }
    }

    override fun setBackIndicatorColorRes(id: Int) {
        setBackIndicatorColor(context.getColorCompat(id))
    }

    override fun setOnBackIndicatorClickListener(onBackIndicatorClickListener: OnClickListener) {
        viewBinding.ivBackIndicator.setOnClickListener(onBackIndicatorClickListener)
    }

    override fun getLeftActionContainer(): LinearLayout {
        return viewBinding.llLeftActionContainer
    }

    override fun hideLeftActionContainer() {
        hideView(viewBinding.llLeftActionContainer)
    }

    override fun showLeftActionContainer() {
        showView(viewBinding.llLeftActionContainer)
    }

    override fun getLeftActionInnerContainer(): FrameLayout {
        return viewBinding.flLeftActionContainer
    }

    override fun hideLeftActionInnerContainer() {
        hideView(viewBinding.flLeftActionContainer)
    }

    override fun showLeftActionInnerContainer() {
        showView(viewBinding.flLeftActionContainer)
    }

    override fun setLeftAction(text: CharSequence, onLeftActionClickListener: OnClickListener) {
        viewBinding.tvLeftAction.visible()
        viewBinding.ivLeftAction.gone()
        viewBinding.tvLeftAction.text = text
        viewBinding.tvLeftAction.setOnClickListener(onLeftActionClickListener)
    }

    override fun setLeftAction(id: Int, onLeftActionClickListener: OnClickListener) {
        try {
            val drawableCompat = context.getDrawableCompat(id)
            if (drawableCompat != null) {
                viewBinding.tvLeftAction.gone()
                viewBinding.ivLeftAction.visible()
                viewBinding.ivLeftAction.setImageDrawable(drawableCompat)
                viewBinding.ivLeftAction.setOnClickListener(onLeftActionClickListener)
            } else {
                viewBinding.tvLeftAction.visible()
                viewBinding.ivLeftAction.gone()
                viewBinding.tvLeftAction.text = context.getString(id)
                viewBinding.tvLeftAction.setOnClickListener(onLeftActionClickListener)
            }
        } catch (e: Exception) {
            viewBinding.tvLeftAction.visible()
            viewBinding.ivLeftAction.gone()
            viewBinding.tvLeftAction.text = context.getString(id)
            viewBinding.tvLeftAction.setOnClickListener(onLeftActionClickListener)
        }
    }

    override fun setLeftAction(drawable: Drawable, onLeftActionClickListener: OnClickListener) {
        viewBinding.tvLeftAction.gone()
        viewBinding.ivLeftAction.visible()
        viewBinding.ivLeftAction.setImageDrawable(drawable)
        viewBinding.ivLeftAction.setOnClickListener(onLeftActionClickListener)
    }

    override fun setLeftAction(
        stateListDrawable: StateListDrawable,
        onLeftActionClickListener: OnClickListener
    ) {
        viewBinding.tvLeftAction.gone()
        viewBinding.ivLeftAction.visible()
        viewBinding.ivLeftAction.setImageState(stateListDrawable.state, false)
        viewBinding.ivLeftAction.setOnClickListener(onLeftActionClickListener)
    }

    override fun setLeftActionText(text: CharSequence) {
        viewBinding.tvLeftAction.visible()
        viewBinding.ivLeftAction.gone()
        viewBinding.tvLeftAction.text = text
    }

    override fun setLeftActionText(id: Int) {
        viewBinding.tvLeftAction.visible()
        viewBinding.ivLeftAction.gone()
        viewBinding.tvLeftAction.text = context.getString(id)
    }

    override fun setLeftActionDrawable(drawable: Drawable) {
        viewBinding.tvLeftAction.gone()
        viewBinding.ivLeftAction.visible()
        viewBinding.ivLeftAction.setImageDrawable(drawable)
    }

    override fun setLeftActionDrawable(id: Int) {
        viewBinding.tvLeftAction.gone()
        viewBinding.ivLeftAction.visible()
        viewBinding.ivLeftAction.setImageDrawable(context.getDrawableCompat(id))
    }

    override fun setLeftActionDrawable(stateListDrawable: StateListDrawable) {
        viewBinding.tvLeftAction.gone()
        viewBinding.ivLeftAction.visible()
        viewBinding.ivLeftAction.setImageState(stateListDrawable.state, false)
    }

    override fun setLeftActionTextColor(color: Int) {
        viewBinding.tvLeftAction.visible()
        viewBinding.ivLeftAction.gone()
        viewBinding.tvLeftAction.setTextColor(color)
    }

    override fun setLeftActionTextColorRes(id: Int) {
        viewBinding.tvLeftAction.visible()
        viewBinding.ivLeftAction.gone()
        viewBinding.tvLeftAction.setTextColor(context.getColorCompat(id))
    }

    override fun setLeftActionTextColorStateList(colorStateList: ColorStateList) {
        viewBinding.tvLeftAction.visible()
        viewBinding.ivLeftAction.gone()
        viewBinding.tvLeftAction.setTextColor(colorStateList)
    }

    override fun setLeftActionTextColorStateList(id: Int) {
        viewBinding.tvLeftAction.visible()
        viewBinding.ivLeftAction.gone()
        viewBinding.tvLeftAction.setTextColor(context.getColorStateListCompat(id))
    }

    override fun setOnLeftActionClickListener(onLeftActionClickListener: OnClickListener) {
        viewBinding.tvLeftAction.setOnClickListener(onLeftActionClickListener)
        viewBinding.ivLeftAction.setOnClickListener(onLeftActionClickListener)
        if (viewBinding.tvLeftAction.text.isEmpty()) {
            viewBinding.tvLeftAction.gone()
            viewBinding.ivLeftAction.visible()
        } else {
            viewBinding.tvLeftAction.visible()
            viewBinding.ivLeftAction.gone()
        }
    }

    override fun getLeftActionTextView(): TextView {
        return viewBinding.tvLeftAction
    }

    override fun getLeftActionImageView(): ImageView {
        return viewBinding.ivLeftAction
    }

    override fun hideLeftAction() {
        hideView(viewBinding.tvLeftAction)
        hideView(viewBinding.ivLeftAction)
    }

    override fun showLeftAction() {
        if (viewBinding.tvLeftAction.text.isEmpty()) {
            viewBinding.tvLeftAction.gone()
            showView(viewBinding.ivLeftAction)
        } else {
            viewBinding.ivLeftAction.gone()
            showView(viewBinding.tvLeftAction)
        }
    }

    override fun leftActionIsHidden(): Boolean {
        return !viewBinding.tvLeftAction.isVisible && !viewBinding.ivLeftAction.isVisible
    }

    override fun toggleLeftActionHidden() {
        if (leftActionIsHidden()) {
            showLeftAction()
        } else {
            hideLeftAction()
        }
    }

    override fun getRightActionContainer(): LinearLayout {
        return viewBinding.llRightActionContainer
    }

    override fun hideRightActionContainer() {
        hideView(viewBinding.llRightActionContainer)
    }

    override fun showRightActionContainer() {
        showView(viewBinding.llRightActionContainer)
    }

    override fun getRightActionInnerContainer(): FrameLayout {
        return viewBinding.flRightActionContainer
    }

    override fun hideRightActionInnerContainer() {
        hideView(viewBinding.flRightActionContainer)
    }

    override fun showRightActionInnerContainer() {
        showView(viewBinding.flRightActionContainer)
    }

    override fun setRightAction(text: CharSequence, onRightActionClickListener: OnClickListener) {
        viewBinding.tvRightAction.visible()
        viewBinding.ivRightAction.gone()
        viewBinding.tvRightAction.text = text
        viewBinding.tvRightAction.setOnClickListener(onRightActionClickListener)
    }

    override fun setRightAction(id: Int, onRightActionClickListener: OnClickListener) {
        try {
            val drawableCompat = context.getDrawableCompat(id)
            if (drawableCompat != null) {
                viewBinding.tvRightAction.gone()
                viewBinding.ivRightAction.visible()
                viewBinding.ivRightAction.setImageDrawable(drawableCompat)
                viewBinding.ivRightAction.setOnClickListener(onRightActionClickListener)
            } else {
                viewBinding.tvRightAction.visible()
                viewBinding.ivRightAction.gone()
                viewBinding.tvRightAction.text = context.getString(id)
                viewBinding.tvRightAction.setOnClickListener(onRightActionClickListener)
            }
        } catch (e: Exception) {
            viewBinding.tvRightAction.visible()
            viewBinding.ivRightAction.gone()
            viewBinding.tvRightAction.text = context.getString(id)
            viewBinding.tvRightAction.setOnClickListener(onRightActionClickListener)
        }
    }

    override fun setRightAction(drawable: Drawable, onRightActionClickListener: OnClickListener) {
        viewBinding.tvRightAction.gone()
        viewBinding.ivRightAction.visible()
        viewBinding.ivRightAction.setImageDrawable(drawable)
        viewBinding.ivRightAction.setOnClickListener(onRightActionClickListener)
    }

    override fun setRightAction(
        stateListDrawable: StateListDrawable,
        onRightActionClickListener: OnClickListener
    ) {
        viewBinding.tvRightAction.gone()
        viewBinding.ivRightAction.visible()
        viewBinding.ivRightAction.setImageState(stateListDrawable.state, false)
        viewBinding.ivRightAction.setOnClickListener(onRightActionClickListener)
    }

    override fun setRightActionText(text: CharSequence) {
        viewBinding.tvRightAction.visible()
        viewBinding.ivRightAction.gone()
        viewBinding.tvRightAction.text = text
    }

    override fun setRightActionText(id: Int) {
        viewBinding.tvRightAction.visible()
        viewBinding.ivRightAction.gone()
        viewBinding.tvRightAction.text = context.getString(id)
    }

    override fun setRightActionDrawable(drawable: Drawable) {
        viewBinding.tvRightAction.gone()
        viewBinding.ivRightAction.visible()
        viewBinding.ivRightAction.setImageDrawable(drawable)
    }

    override fun setRightActionDrawable(id: Int) {
        viewBinding.tvRightAction.gone()
        viewBinding.ivRightAction.visible()
        viewBinding.ivRightAction.setImageDrawable(context.getDrawableCompat(id))
    }

    override fun setRightActionDrawable(stateListDrawable: StateListDrawable) {
        viewBinding.tvRightAction.gone()
        viewBinding.ivRightAction.visible()
        viewBinding.ivRightAction.setImageState(stateListDrawable.state, false)
    }

    override fun setRightActionTextColor(color: Int) {
        viewBinding.tvRightAction.visible()
        viewBinding.ivRightAction.gone()
        viewBinding.tvRightAction.setTextColor(color)
    }

    override fun setRightActionTextColorRes(id: Int) {
        viewBinding.tvRightAction.visible()
        viewBinding.ivRightAction.gone()
        viewBinding.tvRightAction.setTextColor(context.getColorCompat(id))
    }

    override fun setRightActionTextColorStateList(colorStateList: ColorStateList) {
        viewBinding.tvRightAction.visible()
        viewBinding.ivRightAction.gone()
        viewBinding.tvRightAction.setTextColor(colorStateList)
    }

    override fun setRightActionTextColorStateList(id: Int) {
        viewBinding.tvRightAction.visible()
        viewBinding.ivRightAction.gone()
        viewBinding.tvRightAction.setTextColor(context.getColorStateListCompat(id))
    }

    override fun setOnRightActionClickListener(onRightActionClickListener: OnClickListener) {
        viewBinding.tvRightAction.setOnClickListener(onRightActionClickListener)
        viewBinding.ivRightAction.setOnClickListener(onRightActionClickListener)
        if (viewBinding.tvRightAction.text.isEmpty()) {
            viewBinding.tvRightAction.gone()
            viewBinding.ivRightAction.visible()
        } else {
            viewBinding.tvRightAction.visible()
            viewBinding.ivRightAction.gone()
        }
    }

    override fun getRightActionTextView(): TextView {
        return viewBinding.tvRightAction
    }

    override fun getRightActionImageView(): ImageView {
        return viewBinding.ivRightAction
    }

    override fun hideRightAction() {
        hideView(viewBinding.tvRightAction)
        hideView(viewBinding.ivRightAction)
    }

    override fun showRightAction() {
        if (viewBinding.tvRightAction.text.isEmpty()) {
            viewBinding.tvRightAction.gone()
            showView(viewBinding.ivRightAction)
        } else {
            viewBinding.ivRightAction.gone()
            showView(viewBinding.tvRightAction)
        }
    }

    override fun rightActionIsHidden(): Boolean {
        return !viewBinding.tvRightAction.isVisible && !viewBinding.ivRightAction.isVisible
    }

    override fun toggleRightActionHidden() {
        if (rightActionIsHidden()) {
            showRightAction()
        } else {
            hideRightAction()
        }
    }

    override fun setCustomActionBarView(view: View) {
        viewBinding.flCustomViewContainer.removeAllViews()
        viewBinding.flCustomViewContainer.visible()
        viewBinding.flCustomViewContainer.addView(view)
    }

    override fun setCustomActionBarView(id: Int) {
        viewBinding.flCustomViewContainer.removeAllViews()
        viewBinding.flCustomViewContainer.visible()
        inflate(context, id, viewBinding.flCustomViewContainer)
    }

    override fun getCustomViewContainer(): FrameLayout {
        return viewBinding.flCustomViewContainer
    }

    override fun hideCustomViewContainer() {
        hideView(viewBinding.flCustomViewContainer)
    }

    override fun showCustomViewContainer() {
        showView(viewBinding.flCustomViewContainer)
    }

    override fun getCenterTitleContainer(): LinearLayout {
        return viewBinding.llCenterTitleContainer
    }

    override fun hideCenterTitleContainer() {
        hideView(viewBinding.llCenterTitleContainer)
    }

    override fun showCenterTitleContainer() {
        showView(viewBinding.llCenterTitleContainer)
    }

    override fun setTitle(text: CharSequence) {
        viewBinding.tvTitle.text = text
    }

    override fun setTitle(id: Int) {
        viewBinding.tvTitle.text = context.getString(id)
    }

    override fun setTitleTextColor(color: Int) {
        viewBinding.tvTitle.setTextColor(color)
    }

    override fun setTitleTextColorStateList(colorStateList: ColorStateList) {
        viewBinding.tvTitle.setTextColor(colorStateList)
    }

    override fun setTitleTextColorStateList(id: Int) {
        viewBinding.tvTitle.setTextColor(context.getColorStateListCompat(id))
    }

    override fun getTitleView(): TextView {
        return viewBinding.tvTitle
    }

    override fun hideTitle() {
        hideView(viewBinding.tvTitle)
    }

    override fun showTitle() {
        showView(viewBinding.tvTitle)
    }

    override fun titleIsHidden(): Boolean {
        return !viewBinding.tvTitle.isVisible
    }

    override fun toggleTitleHidden() {
        if (titleIsHidden()) {
            showTitle()
        } else {
            hideTitle()
        }
    }

    override fun setTitleTextColorRes(id: Int) {
        viewBinding.tvTitle.setTextColor(context.getColorCompat(id))
    }

    override fun setSubtitle(text: CharSequence) {
        viewBinding.tvSubtitle.text = text
        if (text.isNotEmpty()) {
            viewBinding.tvSubtitle.visible()
        }
    }

    override fun setSubtitle(id: Int) {
        setSubtitle(context.getString(id))
    }

    override fun setSubtitleTextColor(color: Int) {
        viewBinding.tvSubtitle.setTextColor(color)
    }

    override fun setSubtitleTextColorStateList(colorStateList: ColorStateList) {
        viewBinding.tvSubtitle.setTextColor(colorStateList)
    }

    override fun setSubtitleTextColorStateList(id: Int) {
        viewBinding.tvSubtitle.setTextColor(context.getColorStateListCompat(id))
    }

    override fun getSubtitleView(): TextView {
        return viewBinding.tvSubtitle
    }

    override fun hideSubtitle() {
        hideView(viewBinding.tvSubtitle)
    }

    override fun showSubtitle() {
        showView(viewBinding.tvSubtitle)
    }

    override fun subtitleViewIsHidden(): Boolean {
        return !viewBinding.tvSubtitle.isVisible
    }

    override fun toggleSubtitleHidden() {
        if (subtitleViewIsHidden()) {
            showSubtitle()
        } else {
            hideSubtitle()
        }
    }

    override fun hideAllTitle() {
        hideView(viewBinding.tvTitle)
        hideView(viewBinding.tvSubtitle)
    }

    override fun showAllTitle() {
        showView(viewBinding.tvTitle)
        showView(viewBinding.tvSubtitle)
    }

    override fun setSubtitleTextColorRes(id: Int) {
        viewBinding.tvSubtitle.setTextColor(context.getColorCompat(id))
    }

    override fun createRightMenu(@MenuRes id: Int, menuInflater: MenuInflater) {
        val popupMenu = PopupMenu(context, viewBinding.ivBackIndicator)
        menuInflater.inflate(id, popupMenu.menu)
        setMenu(popupMenu.menu)
    }

    override fun setMenu(menu: Menu) {
        viewBinding.smoothActionMenuView.visible()
        viewBinding.smoothActionMenuView.setMenu(menu)
    }

    override fun getMenuView(): SmoothActionMenuView {
        return viewBinding.smoothActionMenuView
    }

    override fun hideMenu() {
        hideView(viewBinding.smoothActionMenuView)
    }

    override fun showMenu() {
        showView(viewBinding.smoothActionMenuView)
    }

    override fun menuIsHidden(): Boolean {
        return !viewBinding.smoothActionMenuView.isVisible
    }

    override fun toggleMenuHidden() {
        if (menuIsHidden()) {
            showMenu()
        } else {
            hideMenu()
        }
    }

    override fun setOnRightMenuItemClickListener(onMenuItemClickListener: MenuItem.OnMenuItemClickListener) {
        viewBinding.smoothActionMenuView.setOnMenuItemClickListener(onMenuItemClickListener)
    }

    override fun setMenuIndicatorDrawable(drawable: Drawable) {
        viewBinding.smoothActionMenuView.setMenuIndicatorDrawable(drawable)
    }

    override fun setMenuIndicatorDrawable(id: Int) {
        viewBinding.smoothActionMenuView.setMenuIndicatorDrawable(id)
    }

    override fun setMenuIndicatorDrawable(stateListDrawable: StateListDrawable) {
        viewBinding.smoothActionMenuView.setMenuIndicatorDrawable(stateListDrawable)
    }

    override fun setMenuIndicatorColor(color: Int) {
        viewBinding.smoothActionMenuView.setMenuIndicatorColor(color)
    }

    override fun setMenuIndicatorColorRes(id: Int) {
        viewBinding.smoothActionMenuView.setMenuIndicatorColorRes(id)
    }

    override fun setMenuIndicatorColorStateList(colorStateList: ColorStateList) {
        viewBinding.smoothActionMenuView.setMenuIndicatorColorStateList(colorStateList)
    }

    override fun setMenuIndicatorColorStateList(id: Int) {
        viewBinding.smoothActionMenuView.setMenuIndicatorColorStateList(id)
    }

    fun resolveActionBarHeight() {
        viewBinding.rlSmoothActionBarContainer.layoutParams.apply {
            height = getRealActionBarHeight().toInt()
        }
    }

    override fun getInitActionBarHeight(): Float {
        return AttrUtil.getDimensionAttrValue(context, R.attr.smoothActionBarHeight)
    }

    override fun getRealActionBarHeight(): Float {
        return getInitActionBarHeight() + WindowUtil.getStatusBarHeight(context)
    }

    override fun showLeftActionBarActivityIndicator() {
        viewBinding.smoothActivityIndicatorCenter.gone()
        viewBinding.smoothActivityIndicatorRight.gone()
        showView(viewBinding.smoothActivityIndicatorLeft)
    }

    override fun hideLeftActionBarActivityIndicator() {
        hideView(viewBinding.smoothActivityIndicatorLeft)
    }

    override fun showCenterActionBarActivityIndicator() {
        viewBinding.smoothActivityIndicatorLeft.gone()
        viewBinding.smoothActivityIndicatorRight.gone()
        showView(viewBinding.smoothActivityIndicatorCenter)
    }

    override fun hideCenterActionBarActivityIndicator() {
        hideView(viewBinding.smoothActivityIndicatorCenter)
    }

    override fun showRightActionBarActivityIndicator() {
        viewBinding.smoothActivityIndicatorLeft.gone()
        viewBinding.smoothActivityIndicatorCenter.gone()
        hideRightActionInnerContainer()
        showView(viewBinding.smoothActivityIndicatorRight)
    }

    override fun hideRightActionBarActivityIndicator() {
        hideView(viewBinding.smoothActivityIndicatorRight)
        showRightActionInnerContainer()
    }

    private fun showView(view: View) {
        if (view.isVisible) return
        view.alpha = 0f
        view.visible()
        view.animate()
            .setDuration(VIEW_ALPHA_ANIM_DURATION)
            .setListener(null)
            .alpha(1F)
            .start()
    }

    private fun hideView(view: View) {
        if (!view.isVisible) return
        view.animate()
            .setDuration(VIEW_ALPHA_ANIM_DURATION)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    view.gone()
                }
            })
            .alpha(0f)
            .start()
    }

    companion object {

        private const val VIEW_ALPHA_ANIM_DURATION = 200L

    }
}
