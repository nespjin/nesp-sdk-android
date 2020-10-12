package com.nesp.sdk.android.smooth.app

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.WindowManager
import android.widget.*
import androidx.annotation.MenuRes
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.cast
import com.nesp.sdk.android.core.ktx.content.getColorCompat
import com.nesp.sdk.android.core.ktx.content.getColorStateListCompat
import com.nesp.sdk.android.core.ktx.widget.visible
import com.nesp.sdk.android.smooth.widget.SmoothActionMenuView
import com.nesp.sdk.android.util.DisplayUtil
import com.nesp.sdk.android.util.WindowUtil

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 9:10
 * Project: NespAndroidSdk
 **/
class SmoothActionBar : RelativeLayout, ISmoothActionBar {

    private val rlSmoothActionBarContainer: RelativeLayout by lazy { findViewById(R.id.rlSmoothActionBarContainer) }
    private val ivBackIndicator: ImageView by lazy { findViewById(R.id.ivBackIndicator) }
    private val tvLeftAction: TextView by lazy { findViewById(R.id.tvLeftAction) }
    private val llLeftActionContainer: LinearLayout by lazy { findViewById(R.id.llLeftActionContainer) }
    private val tvRightAction: TextView by lazy { findViewById(R.id.tvRightAction) }
    private val smoothActionMenuView: SmoothActionMenuView by lazy { findViewById(R.id.smoothActionMenuView) }
    private val llRightActionContainer: LinearLayout by lazy { findViewById(R.id.llRightActionContainer) }
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

    override fun setBackIndicatorColorRes(id: Int) {
        setBackIndicatorColor(context.getColorCompat(id))
    }

    override fun setOnBackIndicatorClickListener(onBackIndicatorClickListener: OnClickListener) {
        ivBackIndicator.setOnClickListener(onBackIndicatorClickListener)
    }

    override fun setLeftAction(text: CharSequence, onLeftActionClickListener: OnClickListener) {
        tvLeftAction.text = text
        tvLeftAction.setOnClickListener(onLeftActionClickListener)
    }

    override fun setLeftAction(id: Int, onLeftActionClickListener: OnClickListener) {
        tvLeftAction.text = context.getString(id)
        tvLeftAction.setOnClickListener(onLeftActionClickListener)
    }

    override fun setLeftActionText(text: CharSequence) {
        tvLeftAction.text = text
    }

    override fun setLeftActionText(id: Int) {
        tvLeftAction.text = context.getString(id)
    }

    override fun setLeftActionTextColor(color: Int) {
        tvLeftAction.setTextColor(color)
    }

    override fun setLeftActionTextColorRes(id: Int) {
        tvLeftAction.setTextColor(context.getColorCompat(id))
    }

    override fun setLeftActionTextColorStateList(colorStateList: ColorStateList) {
        tvLeftAction.setTextColor(colorStateList)
    }

    override fun setLeftActionTextColorStateList(id: Int) {
        tvLeftAction.setTextColor(context.getColorStateListCompat(id))
    }

    override fun setOnLeftActionClickListener(onLeftActionClickListener: OnClickListener) {
        tvLeftAction.setOnClickListener(onLeftActionClickListener)
    }

    override fun setRightAction(text: CharSequence, onRightActionClickListener: OnClickListener) {
        tvRightAction.text = text
        tvRightAction.setOnClickListener(onRightActionClickListener)
    }

    override fun setRightAction(id: Int, onRightActionClickListener: OnClickListener) {
        tvRightAction.text = context.getString(id)
        tvRightAction.setOnClickListener(onRightActionClickListener)
    }

    override fun setRightActionText(text: CharSequence) {
        tvRightAction.text = text
    }

    override fun setRightActionText(id: Int) {
        tvRightAction.text = context.getString(id)
    }

    override fun setRightActionTextColor(color: Int) {
        tvRightAction.setTextColor(color)
    }

    override fun setRightActionTextColorRes(id: Int) {
        tvRightAction.setTextColor(context.getColorCompat(id))
    }

    override fun setRightActionTextColorStateList(colorStateList: ColorStateList) {
        tvRightAction.setTextColor(colorStateList)
    }

    override fun setRightActionTextColorStateList(id: Int) {
        tvRightAction.setTextColor(context.getColorStateListCompat(id))
    }

    override fun setOnRightActionClickListener(onRightActionClickListener: OnClickListener) {
        tvRightAction.setOnClickListener(onRightActionClickListener)
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
        return DisplayUtil.getDimensionAttrValue(context, R.attr.smoothActionBarHeight)
    }

    override fun getRealActionBarHeight(): Float {
        return getInitActionBarHeight() + WindowUtil.getStatusBarHeight(context)
    }

}
