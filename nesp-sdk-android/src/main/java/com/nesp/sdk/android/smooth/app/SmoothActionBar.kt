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
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.cast
import com.nesp.sdk.android.core.ktx.content.getColorCompat
import com.nesp.sdk.android.core.ktx.content.getColorStateListCompat
import com.nesp.sdk.android.core.ktx.content.getDrawableCompat
import com.nesp.sdk.android.core.ktx.widget.gone
import com.nesp.sdk.android.core.ktx.widget.visible
import com.nesp.sdk.android.smooth.widget.SmoothActionMenuView
import com.nesp.sdk.android.util.AttrUtil
import com.nesp.sdk.android.util.WindowUtil

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 9:10
 * Project: NespAndroidSdk
 **/
class SmoothActionBar : RelativeLayout, ISmoothActionBar {

    private val rlSmoothActionBarContainer: RelativeLayout by lazy { findViewById(R.id.rlSmoothActionBarContainer) }
    private val rlContentRoot: RelativeLayout by lazy { findViewById(R.id.rlContentRoot) }
    private val ivBackIndicator: ImageView by lazy { findViewById(R.id.ivBackIndicator) }
    private val tvLeftAction: TextView by lazy { findViewById(R.id.tvLeftAction) }
    private val ivLeftAction: ImageView by lazy { findViewById(R.id.ivLeftAction) }
    private val llLeftActionContainer: LinearLayout by lazy { findViewById(R.id.llLeftActionContainer) }
    private val tvRightAction: TextView by lazy { findViewById(R.id.tvRightAction) }
    private val ivRightAction: ImageView by lazy { findViewById(R.id.ivRightAction) }
    private val smoothActionMenuView: SmoothActionMenuView by lazy { findViewById(R.id.smoothActionMenuView) }
    private val llRightActionContainer: LinearLayout by lazy { findViewById(R.id.llRightActionContainer) }
    private val flCustomViewContainer: FrameLayout by lazy { findViewById(R.id.flCustomViewContainer) }
    private val llCenterTitleContainer: LinearLayout by lazy { findViewById(R.id.llCenterTitleContainer) }
    private val tvTitle: TextView by lazy { findViewById(R.id.tvTitle) }
    private val tvSubtitle: TextView by lazy { findViewById(R.id.tvSubtitle) }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        inflate(context, R.layout.smooth_action_bar, this)
    }

    override fun setBackIndicatorDrawable(drawable: Drawable) {
        ivBackIndicator.setImageDrawable(drawable)
    }

    override fun setBackIndicatorDrawable(id: Int) {
        ivBackIndicator.setImageResource(id)
    }

    override fun setBackIndicatorDrawable(stateListDrawable: StateListDrawable) {
        ivBackIndicator.setImageState(stateListDrawable.state, false)
    }

    override fun setBackIndicatorColor(color: Int) {
        ivBackIndicator.imageTintList = ColorStateList.valueOf(color)
    }

    override fun setBackIndicatorColorStateList(colorStateList: ColorStateList) {
        ivBackIndicator.imageTintList = colorStateList
    }

    override fun setBackIndicatorColorStateList(id: Int) {
        ivBackIndicator.imageTintList = context.getColorStateListCompat(id)
    }

    override fun getBackIndicator(): ImageView {
        return ivBackIndicator
    }

    override fun backIndicatorIsHidden(): Boolean {
        return !ivBackIndicator.isVisible
    }

    override fun hideBackIndicator() {
        hideView(ivBackIndicator)
    }

    override fun showBackIndicator() {
        showView(ivBackIndicator)
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
        ivBackIndicator.setOnClickListener(onBackIndicatorClickListener)
    }

    override fun getLeftActionContainer(): LinearLayout {
        return llLeftActionContainer
    }

    override fun hideLeftActionContainer() {
        hideView(llLeftActionContainer)
    }

    override fun showLeftActionContainer() {
        showView(llLeftActionContainer)
    }

    override fun setLeftAction(text: CharSequence, onLeftActionClickListener: OnClickListener) {
        tvLeftAction.visible()
        ivLeftAction.gone()
        tvLeftAction.text = text
        tvLeftAction.setOnClickListener(onLeftActionClickListener)
    }

    override fun setLeftAction(id: Int, onLeftActionClickListener: OnClickListener) {
        try {
            val drawableCompat = context.getDrawableCompat(id)
            if (drawableCompat != null) {
                tvLeftAction.gone()
                ivLeftAction.visible()
                ivLeftAction.setImageDrawable(drawableCompat)
                ivLeftAction.setOnClickListener(onLeftActionClickListener)
            } else {
                tvLeftAction.visible()
                ivLeftAction.gone()
                tvLeftAction.text = context.getString(id)
                tvLeftAction.setOnClickListener(onLeftActionClickListener)
            }
        } catch (e: Exception) {
            tvLeftAction.visible()
            ivLeftAction.gone()
            tvLeftAction.text = context.getString(id)
            tvLeftAction.setOnClickListener(onLeftActionClickListener)
        }
    }

    override fun setLeftAction(drawable: Drawable, onLeftActionClickListener: OnClickListener) {
        tvLeftAction.gone()
        ivLeftAction.visible()
        ivLeftAction.setImageDrawable(drawable)
        ivLeftAction.setOnClickListener(onLeftActionClickListener)
    }

    override fun setLeftAction(
        stateListDrawable: StateListDrawable,
        onLeftActionClickListener: OnClickListener
    ) {
        tvLeftAction.gone()
        ivLeftAction.visible()
        ivLeftAction.setImageState(stateListDrawable.state, false)
        ivLeftAction.setOnClickListener(onLeftActionClickListener)
    }

    override fun setLeftActionText(text: CharSequence) {
        tvLeftAction.visible()
        ivLeftAction.gone()
        tvLeftAction.text = text
    }

    override fun setLeftActionText(id: Int) {
        tvLeftAction.visible()
        ivLeftAction.gone()
        tvLeftAction.text = context.getString(id)
    }

    override fun setLeftActionDrawable(drawable: Drawable) {
        tvLeftAction.gone()
        ivLeftAction.visible()
        ivLeftAction.setImageDrawable(drawable)
    }

    override fun setLeftActionDrawable(id: Int) {
        tvLeftAction.gone()
        ivLeftAction.visible()
        ivLeftAction.setImageDrawable(context.getDrawableCompat(id))
    }

    override fun setLeftActionDrawable(stateListDrawable: StateListDrawable) {
        tvLeftAction.gone()
        ivLeftAction.visible()
        ivLeftAction.setImageState(stateListDrawable.state, false)
    }

    override fun setLeftActionTextColor(color: Int) {
        tvLeftAction.visible()
        ivLeftAction.gone()
        tvLeftAction.setTextColor(color)
    }

    override fun setLeftActionTextColorRes(id: Int) {
        tvLeftAction.visible()
        ivLeftAction.gone()
        tvLeftAction.setTextColor(context.getColorCompat(id))
    }

    override fun setLeftActionTextColorStateList(colorStateList: ColorStateList) {
        tvLeftAction.visible()
        ivLeftAction.gone()
        tvLeftAction.setTextColor(colorStateList)
    }

    override fun setLeftActionTextColorStateList(id: Int) {
        tvLeftAction.visible()
        ivLeftAction.gone()
        tvLeftAction.setTextColor(context.getColorStateListCompat(id))
    }

    override fun setOnLeftActionClickListener(onLeftActionClickListener: OnClickListener) {
        tvLeftAction.setOnClickListener(onLeftActionClickListener)
        ivLeftAction.setOnClickListener(onLeftActionClickListener)
        if (tvLeftAction.text.isEmpty()) {
            tvLeftAction.gone()
            ivLeftAction.visible()
        } else {
            tvLeftAction.visible()
            ivLeftAction.gone()
        }
    }

    override fun getLeftActionTextView(): TextView {
        return tvLeftAction
    }

    override fun getLeftActionImageView(): ImageView {
        return ivLeftAction
    }

    override fun hideLeftAction() {
        hideView(tvLeftAction)
        hideView(ivLeftAction)
    }

    override fun showLeftAction() {
        if (tvLeftAction.text.isEmpty()) {
            tvLeftAction.gone()
            showView(ivLeftAction)
        } else {
            ivLeftAction.gone()
            showView(tvLeftAction)
        }
    }

    override fun leftActionIsHidden(): Boolean {
        return !tvLeftAction.isVisible && !ivLeftAction.isVisible
    }

    override fun toggleLeftActionHidden() {
        if (leftActionIsHidden()) {
            showLeftAction()
        } else {
            hideLeftAction()
        }
    }

    override fun getRightActionContainer(): LinearLayout {
        return llRightActionContainer
    }

    override fun hideRightActionContainer() {
        hideView(llRightActionContainer)
    }

    override fun showRightActionContainer() {
        showView(llRightActionContainer)
    }

    override fun setRightAction(text: CharSequence, onRightActionClickListener: OnClickListener) {
        tvRightAction.visible()
        ivRightAction.gone()
        tvRightAction.text = text
        tvRightAction.setOnClickListener(onRightActionClickListener)
    }

    override fun setRightAction(id: Int, onRightActionClickListener: OnClickListener) {
        try {
            val drawableCompat = context.getDrawableCompat(id)
            if (drawableCompat != null) {
                tvRightAction.gone()
                ivRightAction.visible()
                ivRightAction.setImageDrawable(drawableCompat)
                ivRightAction.setOnClickListener(onRightActionClickListener)
            } else {
                tvRightAction.visible()
                ivRightAction.gone()
                tvRightAction.text = context.getString(id)
                tvRightAction.setOnClickListener(onRightActionClickListener)
            }
        } catch (e: Exception) {
            tvRightAction.visible()
            ivRightAction.gone()
            tvRightAction.text = context.getString(id)
            tvRightAction.setOnClickListener(onRightActionClickListener)
        }
    }

    override fun setRightAction(drawable: Drawable, onRightActionClickListener: OnClickListener) {
        tvRightAction.gone()
        ivRightAction.visible()
        ivRightAction.setImageDrawable(drawable)
        ivRightAction.setOnClickListener(onRightActionClickListener)
    }

    override fun setRightAction(
        stateListDrawable: StateListDrawable,
        onRightActionClickListener: OnClickListener
    ) {
        tvRightAction.gone()
        ivRightAction.visible()
        ivRightAction.setImageState(stateListDrawable.state, false)
        ivRightAction.setOnClickListener(onRightActionClickListener)
    }

    override fun setRightActionText(text: CharSequence) {
        tvRightAction.visible()
        ivRightAction.gone()
        tvRightAction.text = text
    }

    override fun setRightActionText(id: Int) {
        tvRightAction.visible()
        ivRightAction.gone()
        tvRightAction.text = context.getString(id)
    }

    override fun setRightActionDrawable(drawable: Drawable) {
        tvRightAction.gone()
        ivRightAction.visible()
        ivRightAction.setImageDrawable(drawable)
    }

    override fun setRightActionDrawable(id: Int) {
        tvRightAction.gone()
        ivRightAction.visible()
        ivRightAction.setImageDrawable(context.getDrawableCompat(id))
    }

    override fun setRightActionDrawable(stateListDrawable: StateListDrawable) {
        tvRightAction.gone()
        ivRightAction.visible()
        ivRightAction.setImageState(stateListDrawable.state, false)
    }

    override fun setRightActionTextColor(color: Int) {
        tvRightAction.visible()
        ivRightAction.gone()
        tvRightAction.setTextColor(color)
    }

    override fun setRightActionTextColorRes(id: Int) {
        tvRightAction.visible()
        ivRightAction.gone()
        tvRightAction.setTextColor(context.getColorCompat(id))
    }

    override fun setRightActionTextColorStateList(colorStateList: ColorStateList) {
        tvRightAction.visible()
        ivRightAction.gone()
        tvRightAction.setTextColor(colorStateList)
    }

    override fun setRightActionTextColorStateList(id: Int) {
        tvRightAction.visible()
        ivRightAction.gone()
        tvRightAction.setTextColor(context.getColorStateListCompat(id))
    }

    override fun setOnRightActionClickListener(onRightActionClickListener: OnClickListener) {
        tvRightAction.setOnClickListener(onRightActionClickListener)
        ivRightAction.setOnClickListener(onRightActionClickListener)
        if (tvRightAction.text.isEmpty()) {
            tvRightAction.gone()
            ivRightAction.visible()
        } else {
            tvRightAction.visible()
            ivRightAction.gone()
        }
    }

    override fun getRightActionTextView(): TextView {
        return tvRightAction
    }

    override fun getRightActionImageView(): ImageView {
        return ivRightAction
    }

    override fun hideRightAction() {
        hideView(tvRightAction)
        hideView(ivRightAction)
    }

    override fun showRightAction() {
        if (tvRightAction.text.isEmpty()) {
            tvRightAction.gone()
            showView(ivRightAction)
        } else {
            ivRightAction.gone()
            showView(tvRightAction)
        }
    }

    override fun rightActionIsHidden(): Boolean {
        return !tvRightAction.isVisible && !ivRightAction.isVisible
    }

    override fun toggleRightActionHidden() {
        if (rightActionIsHidden()) {
            showRightAction()
        } else {
            hideRightAction()
        }
    }

    override fun setCustomActionBarView(view: View) {
        flCustomViewContainer.removeAllViews()
        flCustomViewContainer.visible()
        flCustomViewContainer.addView(view)
    }

    override fun setCustomActionBarView(id: Int) {
        flCustomViewContainer.removeAllViews()
        flCustomViewContainer.visible()
        inflate(context, id, flCustomViewContainer)
    }

    override fun getCustomViewContainer(): FrameLayout {
        return flCustomViewContainer
    }

    override fun hideCustomViewContainer() {
        hideView(flCustomViewContainer)
    }

    override fun showCustomViewContainer() {
        showView(flCustomViewContainer)
    }

    override fun getCenterTitleContainer(): LinearLayout {
        return llCenterTitleContainer
    }

    override fun hideCenterTitleContainer() {
        hideView(llCenterTitleContainer)
    }

    override fun showCenterTitleContainer() {
        showView(llCenterTitleContainer)
    }

    override fun setTitle(text: CharSequence) {
        tvTitle.text = text
    }

    override fun setTitle(id: Int) {
        tvTitle.text = context.getString(id)
    }

    override fun setTitleTextColor(color: Int) {
        tvTitle.setTextColor(color)
    }

    override fun setTitleTextColorStateList(colorStateList: ColorStateList) {
        tvTitle.setTextColor(colorStateList)
    }

    override fun setTitleTextColorStateList(id: Int) {
        tvTitle.setTextColor(context.getColorStateListCompat(id))
    }

    override fun getTitleView(): TextView {
        return tvTitle
    }

    override fun hideTitle() {
        hideView(tvTitle)
    }

    override fun showTitle() {
        showView(tvTitle)
    }

    override fun titleIsHidden(): Boolean {
        return !tvTitle.isVisible
    }

    override fun toggleTitleHidden() {
        if (titleIsHidden()) {
            showTitle()
        } else {
            hideTitle()
        }
    }

    override fun setTitleTextColorRes(id: Int) {
        tvTitle.setTextColor(context.getColorCompat(id))
    }

    override fun setSubtitle(text: CharSequence) {
        tvSubtitle.text = text
    }

    override fun setSubtitle(id: Int) {
        tvSubtitle.text = context.getString(id)
    }

    override fun setSubtitleTextColor(color: Int) {
        tvSubtitle.setTextColor(color)
    }

    override fun setSubtitleTextColorStateList(colorStateList: ColorStateList) {
        tvSubtitle.setTextColor(colorStateList)
    }

    override fun setSubtitleTextColorStateList(id: Int) {
        tvSubtitle.setTextColor(context.getColorStateListCompat(id))
    }

    override fun getSubtitleView(): TextView {
        return tvSubtitle
    }

    override fun hideSubtitle() {
        hideView(tvSubtitle)
    }

    override fun showSubtitle() {
        showView(tvSubtitle)
    }

    override fun subtitleViewIsHidden(): Boolean {
        return !tvSubtitle.isVisible
    }

    override fun toggleSubtitleHidden() {
        if (subtitleViewIsHidden()) {
            showSubtitle()
        } else {
            hideSubtitle()
        }
    }

    override fun hideAllTitle() {
        hideView(tvTitle)
        hideView(tvSubtitle)
    }

    override fun showAllTitle() {
        showView(tvTitle)
        showView(tvSubtitle)
    }

    override fun setSubtitleTextColorRes(id: Int) {
        tvSubtitle.setTextColor(context.getColorCompat(id))
    }

    override fun createRightMenu(@MenuRes id: Int, menuInflater: MenuInflater) {
        val popupMenu = PopupMenu(context, ivBackIndicator)
        menuInflater.inflate(id, popupMenu.menu)
        setMenu(popupMenu.menu)
    }

    override fun setMenu(menu: Menu) {
        smoothActionMenuView.visible()
        smoothActionMenuView.setMenu(menu)
    }

    override fun getMenuView(): SmoothActionMenuView {
        return smoothActionMenuView
    }

    override fun hideMenu() {
        hideView(smoothActionMenuView)
    }

    override fun showMenu() {
        showView(smoothActionMenuView)
    }

    override fun menuIsHidden(): Boolean {
        return !smoothActionMenuView.isVisible
    }

    override fun toggleMenuHidden() {
        if (menuIsHidden()) {
            showMenu()
        } else {
            hideMenu()
        }
    }

    override fun setOnRightMenuItemClickListener(onMenuItemClickListener: MenuItem.OnMenuItemClickListener) {
        smoothActionMenuView.setOnMenuItemClickListener(onMenuItemClickListener)
    }

    override fun setMenuIndicatorDrawable(drawable: Drawable) {
        smoothActionMenuView.setMenuIndicatorDrawable(drawable)
    }

    override fun setMenuIndicatorDrawable(id: Int) {
        smoothActionMenuView.setMenuIndicatorDrawable(id)
    }

    override fun setMenuIndicatorDrawable(stateListDrawable: StateListDrawable) {
        smoothActionMenuView.setMenuIndicatorDrawable(stateListDrawable)
    }

    override fun setMenuIndicatorColor(color: Int) {
        smoothActionMenuView.setMenuIndicatorColor(color)
    }

    override fun setMenuIndicatorColorRes(id: Int) {
        smoothActionMenuView.setMenuIndicatorColorRes(id)
    }

    override fun setMenuIndicatorColorStateList(colorStateList: ColorStateList) {
        smoothActionMenuView.setMenuIndicatorColorStateList(colorStateList)
    }

    override fun setMenuIndicatorColorStateList(id: Int) {
        smoothActionMenuView.setMenuIndicatorColorStateList(id)
    }

    fun resolveActionBarHeight() {
        rlSmoothActionBarContainer.layoutParams.apply {
            height = getRealActionBarHeight().toInt()
        }
    }

    override fun getInitActionBarHeight(): Float {
        return AttrUtil.getDimensionAttrValue(context, R.attr.smoothActionBarHeight)
    }

    override fun getRealActionBarHeight(): Float {
        return getInitActionBarHeight() + WindowUtil.getStatusBarHeight(context)
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
