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

package com.nesp.sdk.android.smooth.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mmin18.widget.RealtimeBlurView
import com.nesp.sdk.android.R
import com.nesp.sdk.kotlin.lang.cast
import com.nesp.sdk.android.core.ktx.content.getColorCompat
import com.nesp.sdk.android.core.ktx.content.getColorStateListCompat
import com.nesp.sdk.android.core.ktx.content.getDrawableCompat
import com.nesp.sdk.android.databinding.SmoothActionMenuViewBinding
import com.nesp.sdk.android.utils.DisplayUtil

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 13:57
 * Project: NespAndroidSdk
 **/
class SmoothActionMenuView : LinearLayout {

    private var popupWindow: PopupWindow? = null
    private var menuItemAdapter: MenuItemAdapter? = null

    private var popupWindowIsShown = false

    private val viewBinding: SmoothActionMenuViewBinding =
        SmoothActionMenuViewBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        popupWindow = createPopupWindow()
        viewBinding.ivMenuIndicator.setOnClickListener { togglePopupWindow() }
    }

    fun setMenuIndicatorDrawable(drawable: Drawable) {
        viewBinding.ivMenuIndicator.setImageDrawable(drawable)
    }

    fun setMenuIndicatorDrawable(@DrawableRes id: Int) {
        viewBinding.ivMenuIndicator.setImageDrawable(context.getDrawableCompat(id))
    }

    fun setMenuIndicatorDrawable(stateListDrawable: StateListDrawable) {
        viewBinding.ivMenuIndicator.setImageState(stateListDrawable.state, false)
    }

    fun setMenuIndicatorColor(@ColorInt color: Int) {
        viewBinding.ivMenuIndicator.imageTintList = ColorStateList.valueOf(color)
    }

    fun setMenuIndicatorColorRes(@ColorRes id: Int) {
        setMenuIndicatorColor(context.getColorCompat(id))
    }

    fun setMenuIndicatorColorStateList(colorStateList: ColorStateList) {
        viewBinding.ivMenuIndicator.imageTintList = colorStateList
    }

    fun setMenuIndicatorColorStateList(id: Int) {
        viewBinding.ivMenuIndicator.imageTintList = context.getColorStateListCompat(id)
    }

    fun setMenu(menu: Menu) {
        refreshPopupWindowRecyclerView(menu)
    }

    fun setOnMenuItemClickListener(onMenuItemClickListener: MenuItem.OnMenuItemClickListener?) {
        menuItemAdapter?.onMenuItemClickListener = onMenuItemClickListener
    }

    private fun createPopupWindow(): PopupWindow {
        val popupWindowViewContent = inflate(context, R.layout.smooth_action_menu_list, null)
        val popupWindow = PopupWindow(
            popupWindowViewContent, ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        popupWindow.isOutsideTouchable = true
        return popupWindow
    }

    private fun togglePopupWindow() {
        if (popupWindow == null) return
        if (popupWindowIsShown) {
            popupWindow!!.dismiss()
            popupWindowIsShown = false
        } else {
            popupWindow!!.showAsDropDown(
                viewBinding.ivMenuIndicator.cast(),
                DisplayUtil.dp2px(context, 0f),
                DisplayUtil.dp2px(context, 20f)
            )
            popupWindowIsShown = true
        }
    }

    private fun refreshPopupWindowRecyclerView(menu: Menu) {
        if (popupWindow == null) return
        val recyclerView =
            (popupWindow?.contentView?.findViewById(R.id.recyclerView) as RecyclerView?)
                ?: return
        val realtimeBlurView =
            popupWindow?.contentView?.findViewById(R.id.realtimeBlurView) as RealtimeBlurView?
                ?: return
        val cardView =
            popupWindow?.contentView?.findViewById(R.id.cvRoot) as CardView?
                ?: return
        menuItemAdapter = MenuItemAdapter(menu)

        recyclerView.adapter = menuItemAdapter
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)


        cardView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        realtimeBlurView.layoutParams.apply {
            height = cardView.measuredHeight
            width = cardView.measuredWidth
        }

    }


    private inner class MenuItemAdapter(
        private val menu: Menu
    ) : RecyclerView.Adapter<MenuItemViewHolder>() {

        var onMenuItemClickListener: MenuItem.OnMenuItemClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
            return MenuItemViewHolder(parent)
        }

        override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
            val menuItem = menu.getItem(position)
            holder.ivIcon.setImageDrawable(menuItem.icon)
            holder.tvTitle.text = menuItem.title
            holder.itemView.setOnClickListener {
                onMenuItemClickListener?.onMenuItemClick(menuItem)
                togglePopupWindow()
            }

            holder.itemView.isEnabled = menuItem.isEnabled
            holder.itemView.isVisible = menuItem.isVisible
        }

        override fun getItemCount(): Int {
            return menu.size()
        }

    }

    private class MenuItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.smooth_action_menu_item, parent, false)
    ) {
        val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon) as ImageView
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle) as TextView
    }

}