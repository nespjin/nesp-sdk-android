package com.nesp.sdk.android.binding

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/12 9:31
 **/
class ImageViewBindingAdapter2 {
    companion object {
        private const val TAG = "ImageViewBindingAdapter2"

        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageId(imageView: ImageView, @DrawableRes id: Int) {
            if (id == 0 || id == -1) return
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, id))
        }

    }
}