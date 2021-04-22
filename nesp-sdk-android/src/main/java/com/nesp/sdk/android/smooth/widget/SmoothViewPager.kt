package com.nesp.sdk.android.smooth.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 *
 * @@author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/13 11:41 AM
 * Project: NespAndroidSdk
 * Description:
 **/
class SmoothViewPager : ViewPager {

    var isScrollEnable = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return isScrollEnable && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent?): Boolean {
        return isScrollEnable && super.onInterceptTouchEvent(arg0)
    }


}