package com.nesp.sdk.android.smooth.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import per.goweii.swipeback.SwipeBackDirection
import per.goweii.swipeback.SwipeBackHelper

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 8:38
 * Project: NespAndroidSdk
 **/
open class SmoothSwipeBackActivity : AppCompatActivity() {

    protected var mSwipeBackHelper: SwipeBackHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSwipeBackHelper = SwipeBackHelper.inject(this)
        mSwipeBackHelper!!.isSwipeBackEnable = swipeBackEnable()
        mSwipeBackHelper!!.isSwipeBackOnlyEdge = swipeBackOnlyEdge()
        mSwipeBackHelper!!.isSwipeBackForceEdge = swipeBackForceEdge()
        mSwipeBackHelper!!.swipeBackDirection = swipeBackDirection()
        mSwipeBackHelper!!.swipeBackLayout.setShadowStartColor(0)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mSwipeBackHelper!!.onPostCreate()
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        mSwipeBackHelper!!.onEnterAnimationComplete()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSwipeBackHelper!!.onDestroy()
    }

    override fun finish() {
        if (mSwipeBackHelper!!.finish()) {
            super.finish()
        }
    }

    protected open fun swipeBackEnable(): Boolean {
        return true
    }

    protected open fun swipeBackOnlyEdge(): Boolean {
        return false
    }

    protected open fun swipeBackForceEdge(): Boolean {
        return true
    }

    @SwipeBackDirection
    protected open fun swipeBackDirection(): Int {
        return SwipeBackDirection.FROM_LEFT
    }
}