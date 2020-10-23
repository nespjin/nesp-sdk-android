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

fun View.gone(animal: Boolean = false) {
    if (!animal) {
        this.visibility = View.GONE
    } else {
        if (!this.isVisible) return
        this.animate()
            .setDuration(200L)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    this@gone.visibility = View.GONE
                }
            })
            .alpha(0f)
            .start()
    }
}

fun View.visible(animal: Boolean = false) {
    if (!animal) {
        this.visibility = View.VISIBLE
    } else {
        if (this.isVisible) return
        this.alpha = 0f
        this.visibility = View.VISIBLE
        this.animate()
            .setDuration(200L)
            .setListener(null)
            .alpha(1F)
            .start()
    }
}

fun View.invisible(animal: Boolean = false) {
    if (!animal) {
        this.visibility = View.INVISIBLE
    } else {
        if (!this.isVisible) return
        this.animate()
            .setDuration(200L)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    this@invisible.visibility = View.INVISIBLE
                }
            })
            .alpha(0f)
            .start()
    }
}
