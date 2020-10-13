package com.nesp.sdk.android.smooth.app

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.nesp.sdk.android.R
import com.nesp.sdk.android.smooth.widget.SmoothActionMenuView
import com.nesp.sdk.android.util.AttrUtil
import kotlinx.android.synthetic.main.activity_smooth.*

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/10 20:39
 * Project: NespAndroidSdk
 **/
open class SmoothActivity : SmoothBaseActivity(), MenuItem.OnMenuItemClickListener,
    ISmoothActionBar {

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

    protected fun setSmoothActivityContentView(view: View?): View? {
        val smoothActivityRootView = getSmoothActivityRootView()
        val contentContainer = smoothActivityRootView.findViewById<FrameLayout>(R.id.flContainer)
        contentContainer.addView(view)
        return smoothActivityRootView
    }

    override fun onStart() {
        super.onStart()
        initSmoothActionBar()
    }

    private fun initSmoothActionBar() {
        val smoothActionBar = findViewById<SmoothActionBar>(R.id.smoothActionBar)
        if (smoothActionBar != null) {
            smoothActionBar.resolveActionBarHeight()
            smoothActionBar.setOnRightMenuItemClickListener(this)
            smoothActionBar.setOnBackIndicatorClickListener { onBackPressed() }
        }
    }

    private fun getSmoothActivityRootView(): View {
        return layoutInflater.inflate(R.layout.activity_smooth, null, false)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return false
    }

    protected fun getSmoothActionBar(): SmoothActionBar {
        return smoothActionBar
    }

    override fun setBackIndicatorDrawable(drawable: Drawable) {
        smoothActionBar.setBackIndicatorDrawable(drawable)
    }

    override fun setBackIndicatorDrawable(id: Int) {
        smoothActionBar.setBackIndicatorDrawable(id)
    }

    override fun setBackIndicatorDrawable(stateListDrawable: StateListDrawable) {
        smoothActionBar.setBackIndicatorDrawable(stateListDrawable)
    }

    override fun setBackIndicatorColor(color: Int) {
        smoothActionBar.setBackIndicatorColor(color)
    }

    override fun setBackIndicatorColorRes(id: Int) {
        smoothActionBar.setBackIndicatorColorRes(id)
    }

    override fun setBackIndicatorColorStateList(colorStateList: ColorStateList) {
        smoothActionBar.setBackIndicatorColorStateList(colorStateList)
    }

    override fun setBackIndicatorColorStateList(id: Int) {
        smoothActionBar.setBackIndicatorColorStateList(id)
    }

    override fun getBackIndicator(): ImageView {
        return smoothActionBar.getBackIndicator()
    }

    override fun backIndicatorIsHidden(): Boolean {
        return smoothActionBar.backIndicatorIsHidden()
    }

    override fun hideBackIndicator() {
        smoothActionBar.hideBackIndicator()
    }

    override fun showBackIndicator() {
        smoothActionBar.showBackIndicator()
    }

    override fun toggleBackIndicatorHidden() {
        smoothActionBar.toggleBackIndicatorHidden()
    }

    override fun setOnBackIndicatorClickListener(onBackIndicatorClickListener: View.OnClickListener) {
        smoothActionBar.setOnBackIndicatorClickListener(onBackIndicatorClickListener)
    }

    override fun setLeftAction(
        text: CharSequence,
        onLeftActionClickListener: View.OnClickListener
    ) {
        smoothActionBar.setLeftAction(text, onLeftActionClickListener)
    }

    override fun setLeftAction(id: Int, onLeftActionClickListener: View.OnClickListener) {
        smoothActionBar.setLeftAction(id, onLeftActionClickListener)
    }

    override fun setLeftAction(
        drawable: Drawable,
        onLeftActionClickListener: View.OnClickListener
    ) {
        smoothActionBar.setLeftAction(drawable, onLeftActionClickListener)
    }

    override fun setLeftAction(
        stateListDrawable: StateListDrawable,
        onLeftActionClickListener: View.OnClickListener
    ) {
        smoothActionBar.setLeftAction(stateListDrawable, onLeftActionClickListener)
    }

    override fun setLeftActionText(text: CharSequence) {
        smoothActionBar.setLeftActionText(text)
    }

    override fun setLeftActionText(id: Int) {
        smoothActionBar.setLeftActionText(id)
    }

    override fun setLeftActionDrawable(drawable: Drawable) {
        smoothActionBar.setLeftActionDrawable(drawable)
    }

    override fun setLeftActionDrawable(id: Int) {
        smoothActionBar.setLeftActionDrawable(id)
    }

    override fun setLeftActionDrawable(stateListDrawable: StateListDrawable) {
        smoothActionBar.setLeftActionDrawable(stateListDrawable)
    }

    override fun setLeftActionTextColor(color: Int) {
        smoothActionBar.setLeftActionTextColor(color)
    }

    override fun setLeftActionTextColorRes(id: Int) {
        smoothActionBar.setLeftActionTextColorRes(id)
    }

    override fun setLeftActionTextColorStateList(colorStateList: ColorStateList) {
        smoothActionBar.setLeftActionTextColorStateList(colorStateList)
    }

    override fun setLeftActionTextColorStateList(id: Int) {
        smoothActionBar.setLeftActionTextColorStateList(id)
    }

    override fun setOnLeftActionClickListener(onLeftActionClickListener: View.OnClickListener) {
        smoothActionBar.setOnLeftActionClickListener(onLeftActionClickListener)
    }

    override fun getLeftActionTextView(): TextView {
        return smoothActionBar.getLeftActionTextView()
    }

    override fun getLeftActionImageView(): ImageView {
        return smoothActionBar.getLeftActionImageView()
    }

    override fun hideLeftAction() {
        smoothActionBar.hideLeftAction()
    }

    override fun showLeftAction() {
        smoothActionBar.showLeftAction()
    }

    override fun leftActionIsHidden(): Boolean {
        return smoothActionBar.leftActionIsHidden()
    }

    override fun toggleLeftActionHidden() {
        smoothActionBar.toggleLeftActionHidden()
    }

    override fun setRightAction(
        text: CharSequence,
        onRightActionClickListener: View.OnClickListener
    ) {
        smoothActionBar.setRightAction(text, onRightActionClickListener)
    }

    override fun setRightAction(id: Int, onRightActionClickListener: View.OnClickListener) {
        smoothActionBar.setRightAction(id, onRightActionClickListener)
    }

    override fun setRightAction(
        drawable: Drawable,
        onRightActionClickListener: View.OnClickListener
    ) {
        smoothActionBar.setRightAction(drawable, onRightActionClickListener)
    }

    override fun setRightAction(
        stateListDrawable: StateListDrawable,
        onRightActionClickListener: View.OnClickListener
    ) {
        smoothActionBar.setRightAction(stateListDrawable, onRightActionClickListener)
    }

    override fun setRightActionText(text: CharSequence) {
        smoothActionBar.setRightActionText(text)
    }

    override fun setRightActionText(id: Int) {
        smoothActionBar.setRightActionText(id)
    }

    override fun setRightActionDrawable(drawable: Drawable) {
        smoothActionBar.setRightActionDrawable(drawable)
    }

    override fun setRightActionDrawable(id: Int) {
        smoothActionBar.setRightActionDrawable(id)
    }

    override fun setRightActionDrawable(stateListDrawable: StateListDrawable) {
        smoothActionBar.setRightActionDrawable(stateListDrawable)
    }

    override fun setRightActionTextColor(color: Int) {
        smoothActionBar.setRightActionTextColor(color)
    }

    override fun setRightActionTextColorRes(id: Int) {
        smoothActionBar.setRightActionTextColorRes(id)
    }

    override fun setRightActionTextColorStateList(colorStateList: ColorStateList) {
        smoothActionBar.setRightActionTextColorStateList(colorStateList)
    }

    override fun setRightActionTextColorStateList(id: Int) {
        smoothActionBar.setRightActionTextColorStateList(id)
    }

    override fun setOnRightActionClickListener(onRightActionClickListener: View.OnClickListener) {
        smoothActionBar.setOnRightActionClickListener(onRightActionClickListener)
    }

    override fun getRightActionTextView(): TextView {
        return smoothActionBar.getRightActionTextView()
    }

    override fun getRightActionImageView(): ImageView {
        return smoothActionBar.getRightActionImageView()
    }

    override fun hideRightAction() {
        smoothActionBar.hideRightAction()
    }

    override fun showRightAction() {
        smoothActionBar.showRightAction()
    }

    override fun rightActionIsHidden(): Boolean {
        return smoothActionBar.rightActionIsHidden()
    }

    override fun toggleRightActionHidden() {
        smoothActionBar.toggleRightActionHidden()
    }

    override fun setTitle(text: CharSequence) {
        smoothActionBar.setTitle(text)
    }

    override fun setTitle(id: Int) {
        smoothActionBar.setTitle(id)
    }

    override fun setTitleTextColor(color: Int) {
        smoothActionBar.setTitleTextColor(color)
    }

    override fun setTitleTextColorRes(id: Int) {
        smoothActionBar.setTitleTextColorRes(id)
    }

    override fun setTitleTextColorStateList(colorStateList: ColorStateList) {
        smoothActionBar.setTitleTextColorStateList(colorStateList)
    }

    override fun setTitleTextColorStateList(id: Int) {
        smoothActionBar.setTitleTextColorStateList(id)
    }

    override fun getTitleView(): TextView {
        return smoothActionBar.getTitleView()
    }

    override fun hideTitle() {
        smoothActionBar.hideTitle()
    }

    override fun showTitle() {
        smoothActionBar.showTitle()
    }

    override fun titleIsHidden(): Boolean {
        return smoothActionBar.titleIsHidden()
    }

    override fun toggleTitleHidden() {
        smoothActionBar.toggleTitleHidden()
    }

    override fun setSubtitle(text: CharSequence) {
        smoothActionBar.setSubtitle(text)
    }

    override fun setSubtitle(id: Int) {
        smoothActionBar.setSubtitle(id)
    }

    override fun setSubtitleTextColor(color: Int) {
        smoothActionBar.setSubtitleTextColor(color)
    }

    override fun setSubtitleTextColorRes(id: Int) {
        smoothActionBar.setSubtitleTextColorRes(id)
    }

    override fun setSubtitleTextColorStateList(colorStateList: ColorStateList) {
        smoothActionBar.setSubtitleTextColorStateList(colorStateList)
    }

    override fun setSubtitleTextColorStateList(id: Int) {
        smoothActionBar.setSubtitleTextColorStateList(id)
    }

    override fun getSubtitleView(): TextView {
        return smoothActionBar.getSubtitleView()
    }

    override fun hideSubtitle() {
        smoothActionBar.hideSubtitle()
    }

    override fun showSubtitle() {
        smoothActionBar.showSubtitle()
    }

    override fun subtitleViewIsHidden(): Boolean {
        return smoothActionBar.subtitleViewIsHidden()
    }

    override fun toggleSubtitleHidden() {
        smoothActionBar.toggleSubtitleHidden()
    }

    override fun hideAllTitle() {
        smoothActionBar.hideAllTitle()
    }

    override fun showAllTitle() {
        smoothActionBar.showAllTitle()
    }

    override fun createRightMenu(id: Int, menuInflater: MenuInflater) {
        smoothActionBar.createRightMenu(id, menuInflater)
    }

    override fun setMenu(menu: Menu) {
        smoothActionBar.setMenu(menu)
    }

    override fun getMenuView(): SmoothActionMenuView {
        return smoothActionBar.getMenuView()
    }

    override fun hideMenu() {
        smoothActionBar.hideMenu()
    }

    override fun showMenu() {
        smoothActionBar.showMenu()
    }

    override fun menuIsHidden(): Boolean {
        return smoothActionBar.menuIsHidden()
    }

    override fun toggleMenuHidden() {
        smoothActionBar.toggleMenuHidden()
    }

    override fun setOnRightMenuItemClickListener(onMenuItemClickListener: MenuItem.OnMenuItemClickListener) {
        smoothActionBar.setOnRightMenuItemClickListener(onMenuItemClickListener)
    }

    override fun setMenuIndicatorDrawable(drawable: Drawable) {
        smoothActionBar.setMenuIndicatorDrawable(drawable)
    }

    override fun setMenuIndicatorDrawable(id: Int) {
        smoothActionBar.setMenuIndicatorDrawable(id)
    }

    override fun setMenuIndicatorDrawable(stateListDrawable: StateListDrawable) {
        smoothActionBar.setMenuIndicatorDrawable(stateListDrawable)
    }

    override fun setMenuIndicatorColor(color: Int) {
        smoothActionBar.setMenuIndicatorColor(color)
    }

    override fun setMenuIndicatorColorRes(id: Int) {
        smoothActionBar.setMenuIndicatorColorRes(id)
    }

    override fun setMenuIndicatorColorStateList(colorStateList: ColorStateList) {
        smoothActionBar.setMenuIndicatorColorStateList(colorStateList)
    }

    override fun setMenuIndicatorColorStateList(id: Int) {
        smoothActionBar.setMenuIndicatorColorStateList(id)
    }

    override fun getInitActionBarHeight(): Float {
        return smoothActionBar.getInitActionBarHeight()
    }

    override fun getRealActionBarHeight(): Float {
        return smoothActionBar.getRealActionBarHeight()
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