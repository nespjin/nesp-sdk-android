package com.nesp.sdk.android.smooth.app

import android.content.Context
import android.os.Bundle
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nesp.sdk.android.core.ktx.widget.closeMenuItemAnimation
import com.nesp.sdk.android.databinding.ActivitySmoothBottomNavigationBinding
import com.nesp.sdk.android.smooth.widget.SmoothViewPager

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/10 20:41
 * Project: NespAndroidSdk
 **/
abstract class SmoothBottomNavigationActivity : SmoothSwipeBackActivity(),
    ISmoothBottomNavigationPage {

    private lateinit var viewBinding: ActivitySmoothBottomNavigationBinding

    override var menuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySmoothBottomNavigationBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initializeViewPager()
    }

    override fun swipeBackEnable(): Boolean {
        return false
    }

    override fun getBottomNavigationView(): BottomNavigationView {
        return viewBinding.navigationView
    }

    override fun getViewPager(): SmoothViewPager {
        return viewBinding.viewPager
    }

    override fun getContext(): Context {
        return this
    }

//    @MenuRes
//    protected abstract fun getBottomNavigationMenuResId(): Int


}