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

package com.nesp.sdk.android.core.ktx.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.core.view.isVisible
import com.nesp.sdk.android.smooth.app.SmoothActionBar

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/10/11 8:07
 * Project: NespAndroidSdk
 **/

fun View.gone(animal: Boolean = false, onAnimationEnd: () -> Unit = {}) {
    if (!animal) {
        this.visibility = View.GONE
        onAnimationEnd()
    } else {
        if (!this.isVisible) return
        this.animate()
            .setDuration(200L)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    this@gone.visibility = View.GONE
                    onAnimationEnd()
                }
            })
            .alpha(0f)
            .start()
    }
}

fun View.visible(animal: Boolean = false, onAnimationEnd: () -> Unit = {}) {
    if (!animal) {
        this.visibility = View.VISIBLE
        onAnimationEnd()
    } else {
        if (this.isVisible) return
        this.alpha = 0f
        this.visibility = View.VISIBLE
        this.animate()
            .setDuration(200L)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    onAnimationEnd()
                }
            })
            .alpha(1F)
            .start()
    }
}

fun View.invisible(animal: Boolean = false, onAnimationEnd: () -> Unit = {}) {
    if (!animal) {
        this.visibility = View.INVISIBLE
        onAnimationEnd()
    } else {
        if (!this.isVisible) return
        this.animate()
            .setDuration(200L)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    this@invisible.visibility = View.INVISIBLE
                    onAnimationEnd()
                }
            })
            .alpha(0f)
            .start()
    }
}
