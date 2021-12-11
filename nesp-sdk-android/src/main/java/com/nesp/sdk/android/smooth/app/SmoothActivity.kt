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
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.github.mmin18.widget.RealtimeBlurView
import com.nesp.sdk.android.R
import com.nesp.sdk.android.databinding.ActivitySmoothBinding
import com.nesp.sdk.android.smooth.widget.SmoothActionMenuView
import com.nesp.sdk.android.utils.AttrUtil

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/10/10 20:39
 * Project: NespAndroidSdk
 **/
open class SmoothActivity : SmoothSwipeBackActivity(), MenuItem.OnMenuItemClickListener,
    ISmoothActionBar {

    protected val viewBindingRoot: ActivitySmoothBinding by lazy {
        ActivitySmoothBinding.inflate(layoutInflater, null, false)
    }

    private val _smoothActionBar: SmoothActionBar by lazy { viewBindingRoot.smoothActionBar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(view: View?) {
        setContentView(view, null)
    }

    override fun setContentView(layoutResID: Int) {
        val view = layoutInflater.inflate(
            layoutResID, null, false
        )
        setContentView(view)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        val smoothActivityContentView = setSmoothActivityContentView(view)
        if (params == null) {
            super.setContentView(smoothActivityContentView)
            return
        }
        super.setContentView(smoothActivityContentView, params)
    }

    protected fun setSmoothActivityContentView(view: View?): View {
        val smoothActivityRootView = getSmoothActivityRootView()
        val contentContainer = viewBindingRoot.flContainer
        contentContainer.addView(view)
        return smoothActivityRootView
    }

    override fun onStart() {
        super.onStart()
        initSmoothActionBar()
    }

    private fun initSmoothActionBar() {
        _smoothActionBar.resolveActionBarHeight()
        _smoothActionBar.setOnRightMenuItemClickListener(this)
        _smoothActionBar.setOnBackIndicatorClickListener { onBackPressed() }
    }

    private fun getSmoothActivityRootView(): View {
        return viewBindingRoot.root
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return false
    }

    protected fun getSmoothActionBar(): SmoothActionBar {
        return _smoothActionBar
    }

    override fun setBackgroundColor(color: Int) {
        _smoothActionBar.setBackgroundColor(color)
    }

    override fun setBackgroundColorRes(id: Int) {
        _smoothActionBar.setBackgroundColorRes(id)
    }

    override fun setBackgroundColorStateList(colorStateList: ColorStateList) {
        _smoothActionBar.setBackgroundColorStateList(colorStateList)
    }

    override fun setBackgroundColorStateList(id: Int) {
        _smoothActionBar.setBackgroundColorStateList(id)
    }

    override fun disableRealtimeBlur() {
        _smoothActionBar.disableRealtimeBlur()
    }

    override fun getRealtimeBlurView(): RealtimeBlurView {
        return _smoothActionBar.getRealtimeBlurView()
    }

    override fun setBackIndicatorDrawable(drawable: Drawable) {
        _smoothActionBar.setBackIndicatorDrawable(drawable)
    }

    override fun setBackIndicatorDrawable(id: Int) {
        _smoothActionBar.setBackIndicatorDrawable(id)
    }

    override fun setBackIndicatorDrawable(stateListDrawable: StateListDrawable) {
        _smoothActionBar.setBackIndicatorDrawable(stateListDrawable)
    }

    override fun setBackIndicatorColor(color: Int) {
        _smoothActionBar.setBackIndicatorColor(color)
    }

    override fun setBackIndicatorColorRes(id: Int) {
        _smoothActionBar.setBackIndicatorColorRes(id)
    }

    override fun setBackIndicatorColorStateList(colorStateList: ColorStateList) {
        _smoothActionBar.setBackIndicatorColorStateList(colorStateList)
    }

    override fun setBackIndicatorColorStateList(id: Int) {
        _smoothActionBar.setBackIndicatorColorStateList(id)
    }

    override fun getBackIndicator(): ImageView {
        return _smoothActionBar.getBackIndicator()
    }

    override fun backIndicatorIsHidden(): Boolean {
        return _smoothActionBar.backIndicatorIsHidden()
    }

    override fun hideBackIndicator() {
        _smoothActionBar.hideBackIndicator()
    }

    override fun showBackIndicator() {
        _smoothActionBar.showBackIndicator()
    }

    override fun toggleBackIndicatorHidden() {
        _smoothActionBar.toggleBackIndicatorHidden()
    }

    override fun setOnBackIndicatorClickListener(onBackIndicatorClickListener: View.OnClickListener) {
        _smoothActionBar.setOnBackIndicatorClickListener(onBackIndicatorClickListener)
    }

    override fun getLeftActionContainer(): LinearLayout {
        return _smoothActionBar.getLeftActionContainer()
    }

    override fun hideLeftActionContainer() {
        _smoothActionBar.hideLeftActionContainer()
    }

    override fun showLeftActionContainer() {
        _smoothActionBar.showLeftActionContainer()
    }

    override fun getLeftActionInnerContainer(): FrameLayout {
        return _smoothActionBar.getLeftActionInnerContainer()
    }

    override fun hideLeftActionInnerContainer() {
        _smoothActionBar.hideLeftActionInnerContainer()
    }

    override fun showLeftActionInnerContainer() {
        _smoothActionBar.showLeftActionInnerContainer()
    }

    override fun setLeftAction(
        text: CharSequence,
        onLeftActionClickListener: View.OnClickListener
    ) {
        _smoothActionBar.setLeftAction(text, onLeftActionClickListener)
    }

    override fun setLeftAction(id: Int, onLeftActionClickListener: View.OnClickListener) {
        _smoothActionBar.setLeftAction(id, onLeftActionClickListener)
    }

    override fun setLeftAction(
        drawable: Drawable,
        onLeftActionClickListener: View.OnClickListener
    ) {
        _smoothActionBar.setLeftAction(drawable, onLeftActionClickListener)
    }

    override fun setLeftAction(
        stateListDrawable: StateListDrawable,
        onLeftActionClickListener: View.OnClickListener
    ) {
        _smoothActionBar.setLeftAction(stateListDrawable, onLeftActionClickListener)
    }

    override fun setLeftActionText(text: CharSequence) {
        _smoothActionBar.setLeftActionText(text)
    }

    override fun setLeftActionText(id: Int) {
        _smoothActionBar.setLeftActionText(id)
    }

    override fun setLeftActionDrawable(drawable: Drawable) {
        _smoothActionBar.setLeftActionDrawable(drawable)
    }

    override fun setLeftActionDrawable(id: Int) {
        _smoothActionBar.setLeftActionDrawable(id)
    }

    override fun setLeftActionDrawable(stateListDrawable: StateListDrawable) {
        _smoothActionBar.setLeftActionDrawable(stateListDrawable)
    }

    override fun setLeftActionTextColor(color: Int) {
        _smoothActionBar.setLeftActionTextColor(color)
    }

    override fun setLeftActionTextColorRes(id: Int) {
        _smoothActionBar.setLeftActionTextColorRes(id)
    }

    override fun setLeftActionTextColorStateList(colorStateList: ColorStateList) {
        _smoothActionBar.setLeftActionTextColorStateList(colorStateList)
    }

    override fun setLeftActionTextColorStateList(id: Int) {
        _smoothActionBar.setLeftActionTextColorStateList(id)
    }

    override fun setOnLeftActionClickListener(onLeftActionClickListener: View.OnClickListener) {
        _smoothActionBar.setOnLeftActionClickListener(onLeftActionClickListener)
    }

    override fun getLeftActionTextView(): TextView {
        return _smoothActionBar.getLeftActionTextView()
    }

    override fun getLeftActionImageView(): ImageView {
        return _smoothActionBar.getLeftActionImageView()
    }

    override fun hideLeftAction() {
        _smoothActionBar.hideLeftAction()
    }

    override fun showLeftAction() {
        _smoothActionBar.showLeftAction()
    }

    override fun leftActionIsHidden(): Boolean {
        return _smoothActionBar.leftActionIsHidden()
    }

    override fun toggleLeftActionHidden() {
        _smoothActionBar.toggleLeftActionHidden()
    }

    override fun getRightActionContainer(): LinearLayout {
        return _smoothActionBar.getRightActionContainer()
    }

    override fun hideRightActionContainer() {
        _smoothActionBar.hideRightActionContainer()
    }

    override fun showRightActionContainer() {
        _smoothActionBar.showRightActionContainer()
    }

    override fun getRightActionInnerContainer(): FrameLayout {
        return _smoothActionBar.getRightActionInnerContainer()
    }

    override fun hideRightActionInnerContainer() {
        _smoothActionBar.hideRightActionInnerContainer()
    }

    override fun showRightActionInnerContainer() {
        _smoothActionBar.showRightActionInnerContainer()
    }

    override fun setRightAction(
        text: CharSequence,
        onRightActionClickListener: View.OnClickListener
    ) {
        _smoothActionBar.setRightAction(text, onRightActionClickListener)
    }

    override fun setRightAction(id: Int, onRightActionClickListener: View.OnClickListener) {
        _smoothActionBar.setRightAction(id, onRightActionClickListener)
    }

    override fun setRightAction(
        drawable: Drawable,
        onRightActionClickListener: View.OnClickListener
    ) {
        _smoothActionBar.setRightAction(drawable, onRightActionClickListener)
    }

    override fun setRightAction(
        stateListDrawable: StateListDrawable,
        onRightActionClickListener: View.OnClickListener
    ) {
        _smoothActionBar.setRightAction(stateListDrawable, onRightActionClickListener)
    }

    override fun setRightActionText(text: CharSequence) {
        _smoothActionBar.setRightActionText(text)
    }

    override fun setRightActionText(id: Int) {
        _smoothActionBar.setRightActionText(id)
    }

    override fun setRightActionDrawable(drawable: Drawable) {
        _smoothActionBar.setRightActionDrawable(drawable)
    }

    override fun setRightActionDrawable(id: Int) {
        _smoothActionBar.setRightActionDrawable(id)
    }

    override fun setRightActionDrawable(stateListDrawable: StateListDrawable) {
        _smoothActionBar.setRightActionDrawable(stateListDrawable)
    }

    override fun setRightActionTextColor(color: Int) {
        _smoothActionBar.setRightActionTextColor(color)
    }

    override fun setRightActionTextColorRes(id: Int) {
        _smoothActionBar.setRightActionTextColorRes(id)
    }

    override fun setRightActionTextColorStateList(colorStateList: ColorStateList) {
        _smoothActionBar.setRightActionTextColorStateList(colorStateList)
    }

    override fun setRightActionTextColorStateList(id: Int) {
        _smoothActionBar.setRightActionTextColorStateList(id)
    }

    override fun setOnRightActionClickListener(onRightActionClickListener: View.OnClickListener) {
        _smoothActionBar.setOnRightActionClickListener(onRightActionClickListener)
    }

    override fun getRightActionTextView(): TextView {
        return _smoothActionBar.getRightActionTextView()
    }

    override fun getRightActionImageView(): ImageView {
        return _smoothActionBar.getRightActionImageView()
    }

    override fun hideRightAction() {
        _smoothActionBar.hideRightAction()
    }

    override fun showRightAction() {
        _smoothActionBar.showRightAction()
    }

    override fun rightActionIsHidden(): Boolean {
        return _smoothActionBar.rightActionIsHidden()
    }

    override fun toggleRightActionHidden() {
        _smoothActionBar.toggleRightActionHidden()
    }

    override fun setCustomActionBarView(view: View) {
        _smoothActionBar.setCustomActionBarView(view)
    }

    override fun setCustomActionBarView(id: Int) {
        _smoothActionBar.setCustomActionBarView(id)
    }

    override fun getCustomViewContainer(): FrameLayout {
        return _smoothActionBar.getCustomViewContainer()
    }

    override fun hideCustomViewContainer() {
        _smoothActionBar.hideCustomViewContainer()
    }

    override fun showCustomViewContainer() {
        _smoothActionBar.showCustomViewContainer()
    }

    override fun getCenterTitleContainer(): LinearLayout {
        return _smoothActionBar.getCenterTitleContainer()
    }

    override fun hideCenterTitleContainer() {
        _smoothActionBar.hideCenterTitleContainer()
    }

    override fun showCenterTitleContainer() {
        _smoothActionBar.showCenterTitleContainer()
    }

    override fun setTitle(text: CharSequence) {
        _smoothActionBar.setTitle(text)
    }

    override fun setTitle(id: Int) {
        _smoothActionBar.setTitle(id)
    }

    override fun setTitleTextColor(color: Int) {
        _smoothActionBar.setTitleTextColor(color)
    }

    override fun setTitleTextColorRes(id: Int) {
        _smoothActionBar.setTitleTextColorRes(id)
    }

    override fun setTitleTextColorStateList(colorStateList: ColorStateList) {
        _smoothActionBar.setTitleTextColorStateList(colorStateList)
    }

    override fun setTitleTextColorStateList(id: Int) {
        _smoothActionBar.setTitleTextColorStateList(id)
    }

    override fun getTitleView(): TextView {
        return _smoothActionBar.getTitleView()
    }

    override fun hideTitle() {
        _smoothActionBar.hideTitle()
    }

    override fun showTitle() {
        _smoothActionBar.showTitle()
    }

    override fun titleIsHidden(): Boolean {
        return _smoothActionBar.titleIsHidden()
    }

    override fun toggleTitleHidden() {
        _smoothActionBar.toggleTitleHidden()
    }

    override fun setSubtitle(text: CharSequence) {
        _smoothActionBar.setSubtitle(text)
    }

    override fun setSubtitle(id: Int) {
        _smoothActionBar.setSubtitle(id)
    }

    override fun setSubtitleTextColor(color: Int) {
        _smoothActionBar.setSubtitleTextColor(color)
    }

    override fun setSubtitleTextColorRes(id: Int) {
        _smoothActionBar.setSubtitleTextColorRes(id)
    }

    override fun setSubtitleTextColorStateList(colorStateList: ColorStateList) {
        _smoothActionBar.setSubtitleTextColorStateList(colorStateList)
    }

    override fun setSubtitleTextColorStateList(id: Int) {
        _smoothActionBar.setSubtitleTextColorStateList(id)
    }

    override fun getSubtitleView(): TextView {
        return _smoothActionBar.getSubtitleView()
    }

    override fun hideSubtitle() {
        _smoothActionBar.hideSubtitle()
    }

    override fun showSubtitle() {
        _smoothActionBar.showSubtitle()
    }

    override fun subtitleViewIsHidden(): Boolean {
        return _smoothActionBar.subtitleViewIsHidden()
    }

    override fun toggleSubtitleHidden() {
        _smoothActionBar.toggleSubtitleHidden()
    }

    override fun hideAllTitle() {
        _smoothActionBar.hideAllTitle()
    }

    override fun showAllTitle() {
        _smoothActionBar.showAllTitle()
    }

    override fun createRightMenu(id: Int, menuInflater: MenuInflater) {
        _smoothActionBar.createRightMenu(id, menuInflater)
    }

    override fun setMenu(menu: Menu) {
        _smoothActionBar.setMenu(menu)
    }

    override fun getMenuView(): SmoothActionMenuView {
        return _smoothActionBar.getMenuView()
    }

    override fun hideMenu() {
        _smoothActionBar.hideMenu()
    }

    override fun showMenu() {
        _smoothActionBar.showMenu()
    }

    override fun menuIsHidden(): Boolean {
        return _smoothActionBar.menuIsHidden()
    }

    override fun toggleMenuHidden() {
        _smoothActionBar.toggleMenuHidden()
    }

    override fun setOnRightMenuItemClickListener(onMenuItemClickListener: MenuItem.OnMenuItemClickListener) {
        _smoothActionBar.setOnRightMenuItemClickListener(onMenuItemClickListener)
    }

    override fun setMenuIndicatorDrawable(drawable: Drawable) {
        _smoothActionBar.setMenuIndicatorDrawable(drawable)
    }

    override fun setMenuIndicatorDrawable(id: Int) {
        _smoothActionBar.setMenuIndicatorDrawable(id)
    }

    override fun setMenuIndicatorDrawable(stateListDrawable: StateListDrawable) {
        _smoothActionBar.setMenuIndicatorDrawable(stateListDrawable)
    }

    override fun setMenuIndicatorColor(color: Int) {
        _smoothActionBar.setMenuIndicatorColor(color)
    }

    override fun setMenuIndicatorColorRes(id: Int) {
        _smoothActionBar.setMenuIndicatorColorRes(id)
    }

    override fun setMenuIndicatorColorStateList(colorStateList: ColorStateList) {
        _smoothActionBar.setMenuIndicatorColorStateList(colorStateList)
    }

    override fun setMenuIndicatorColorStateList(id: Int) {
        _smoothActionBar.setMenuIndicatorColorStateList(id)
    }

    override fun getInitActionBarHeight(): Float {
        return _smoothActionBar.getInitActionBarHeight()
    }

    override fun getRealActionBarHeight(): Float {
        return _smoothActionBar.getRealActionBarHeight()
    }

    override fun showLeftActionBarActivityIndicator() {
        _smoothActionBar.showLeftActionBarActivityIndicator()
    }

    override fun hideLeftActionBarActivityIndicator() {
        _smoothActionBar.hideLeftActionBarActivityIndicator()
    }

    override fun showCenterActionBarActivityIndicator() {
        _smoothActionBar.showCenterActionBarActivityIndicator()
    }

    override fun hideCenterActionBarActivityIndicator() {
        _smoothActionBar.hideCenterActionBarActivityIndicator()
    }

    override fun showRightActionBarActivityIndicator() {
        _smoothActionBar.showRightActionBarActivityIndicator()
    }

    override fun hideRightActionBarActivityIndicator() {
        _smoothActionBar.hideRightActionBarActivityIndicator()
    }

    fun adaptView(viewGroup: View, paddingTop: Int = 0) {
        viewGroup.setPadding(
            viewGroup.paddingLeft, getRealActionBarHeight().toInt() + paddingTop,
            viewGroup.paddingRight, viewGroup.paddingBottom
        )
    }

    fun adaptViewFitActivity(viewGroup: View, paddingTop: Int = 0) {
        val dimensionAttrValue = AttrUtil.getDimensionAttrValue(
            this, R.attr.smoothActivityVerticalPadding
        ).toInt()

        val realPaddingTop = paddingTop + dimensionAttrValue

        adaptView(viewGroup, realPaddingTop)
    }

    fun adaptScrollerView(view: View, paddingTop: Int = 0) {
        if (view is RecyclerView) {
            adaptRecyclerView(view, paddingTop)
        } else if (view is NestedScrollView) {
            adaptNestedScrollView(view, paddingTop)
        }
    }

    fun adaptScrollerViewFitActivity(view: View, paddingTop: Int = 0) {
        val dimensionAttrValue = AttrUtil.getDimensionAttrValue(
            this, R.attr.smoothActivityVerticalPadding
        ).toInt()

        val realPaddingTop = paddingTop + dimensionAttrValue

        if (view is RecyclerView) {
            adaptRecyclerView(view, realPaddingTop)
        } else if (view is NestedScrollView) {
            adaptNestedScrollView(view, realPaddingTop)
        }
    }

    private fun adaptRecyclerView(recyclerView: RecyclerView, paddingTop: Int) {
        recyclerView.clipToPadding = false
        recyclerView.setPadding(
            recyclerView.paddingLeft, getRealActionBarHeight().toInt() + paddingTop,
            recyclerView.paddingRight, recyclerView.paddingBottom
        )
    }

    private fun adaptNestedScrollView(nestedScrollView: NestedScrollView, paddingTop: Int) {
        nestedScrollView.clipToPadding = false
        nestedScrollView.setPadding(
            nestedScrollView.paddingLeft, getRealActionBarHeight().toInt() + paddingTop,
            nestedScrollView.paddingRight, nestedScrollView.paddingBottom
        )
    }


}