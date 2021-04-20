package com.nesp.sdk.android.core.ktx.widget

import android.annotation.SuppressLint
import android.util.TypedValue
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nesp.sdk.android.core.ktx.jvm.reflect.getFieldValue
import com.nesp.sdk.android.core.ktx.jvm.reflect.setFieldValue

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/4/21 1:00
 * Project: NespAndroidSdkSample
 **/
@SuppressLint("RestrictedApi")
private fun BottomNavigationView.closeAnimation() {
    val mMenuView = this.getChildAt(0) as BottomNavigationMenuView
    for (i in 0 until mMenuView.childCount) {
        val button = mMenuView.getChildAt(i) as BottomNavigationItemView
        val mLargeLabel: TextView? =
            button.javaClass.getFieldValue(button, "largeLabel") as TextView?
        val mSmallLabel: TextView? =
            button.javaClass.getFieldValue(button, "smallLabel") as TextView?
        val mSmallLabelSize = mSmallLabel!!.textSize
        button.javaClass.setFieldValue(button, "shiftAmount", 0f)
        button.javaClass.setFieldValue(button, "scaleUpFactor", 1f)
        button.javaClass.setFieldValue(button, "scaleDownFactor", 1f)
        mLargeLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize)
    }
    mMenuView.updateMenuView()
}
