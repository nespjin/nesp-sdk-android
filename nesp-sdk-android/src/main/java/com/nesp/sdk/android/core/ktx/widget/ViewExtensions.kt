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
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/11 8:07
 * Project: NespAndroidSdk
 **/

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

fun View.toggleVisible() {
    if (this.visibility == View.VISIBLE) this.gone() else this.visible()
}

@SuppressLint("ClickableViewAccessibility")
fun View.addClickScaleEffect(scale: Float = 0.8f, duration: Long = 50) {
    this.setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                this.animate().scaleX(scale).scaleY(scale).setDuration(duration).start()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                this.animate().scaleX(1f).scaleY(1f).setDuration(duration * 2).start()
            }
        }
        this.onTouchEvent(event)
    }
}


fun View.setOnScaleClickListener(
    onScaleClickListener: View.OnClickListener? = null,
    scale: Float = 0.8f,
    duration: Long = 100
) {

    val listener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            this@setOnScaleClickListener.animate().scaleX(1f).scaleY(1f).setDuration(duration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        onScaleClickListener?.onClick(this@setOnScaleClickListener)
                    }
                }).start()
        }
    }
    this.setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                this.animate()
                    .setListener(listener)
                    .scaleX(scale)
                    .scaleY(scale)
                    .setDuration(duration)
                    .start()
            }
//            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//                    listener.onAnimationEnd(null)
//            }
        }
        false
    }

}
