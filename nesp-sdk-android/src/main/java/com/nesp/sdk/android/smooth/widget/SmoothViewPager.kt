package com.nesp.sdk.android.smooth.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 *
 * Author: <a href="mailto:jinzhaolu@numob.com">Jack Email:jinzhaolu@numob.com</a>
 * Time: Created 2020/10/13 11:41 AM
 * Project: NespAndroidSdk
 * Description:
 **/
class SmoothViewPager : ViewPager {

    private var isScrollEnable = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setScrollEnable(isScrollEnable: Boolean) {
        this.isScrollEnable = isScrollEnable
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return isScrollEnable && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent?): Boolean {
        return isScrollEnable && super.onInterceptTouchEvent(arg0)
    }


}