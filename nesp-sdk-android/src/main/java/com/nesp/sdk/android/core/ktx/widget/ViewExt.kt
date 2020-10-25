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
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
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
