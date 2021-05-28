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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import per.goweii.swipeback.SwipeBackDirection
import per.goweii.swipeback.SwipeBackHelper

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/10/11 8:38
 * Project: NespAndroidSdk
 **/
open class SmoothSwipeBackActivity : SmoothBaseActivity() {

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