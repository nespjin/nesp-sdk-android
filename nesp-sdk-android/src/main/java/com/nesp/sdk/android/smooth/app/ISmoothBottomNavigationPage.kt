package com.nesp.sdk.android.smooth.app

import android.content.Context
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import androidx.core.view.children
import androidx.core.view.size
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.widget.closeMenuItemAnimation
import com.nesp.sdk.android.smooth.widget.SmoothViewPager
import com.nesp.sdk.android.util.AttrUtil

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/4/21 1:11
 * Project: NespAndroidSdkSample
 **/
interface ISmoothBottomNavigationPage : BottomNavigationView.OnNavigationItemSelectedListener,
    ViewPager.OnPageChangeListener {

    var menuItem: MenuItem?

    fun getContext(): Context

    fun getViewPager(): SmoothViewPager

    fun getBottomNavigationView(): BottomNavigationView

    fun initializeViewPager() {
        val viewPager = getViewPager()
        viewPager.addOnPageChangeListener(this)
        viewPager.adapter = getFragmentPagerAdapter()
        viewPager.offscreenPageLimit = getFragments().size
        setScrollEnable(getIsScrollEnable())
        setOverScrollMode(getOverScrollMode())

//        inflateBottomNavigationMenu(getBottomNavigationMenuResId())

        val navigationView = getBottomNavigationView()
        navigationView.selectedItemId = getDefaultSelectedItemId()
        navigationView.setOnNavigationItemSelectedListener(this)
        navigationView.closeMenuItemAnimation()
    }

    fun inflateBottomNavigationMenu(@MenuRes menuRes: Int) {
        getMenuInflater().inflate(menuRes, getBottomNavigationView().menu)
    }

    fun getMenuInflater(): MenuInflater

    fun getOverScrollMode(): Int {
        return getViewPager().overScrollMode
    }

    fun setOverScrollMode(overScrollMode: Int) {
        getViewPager().overScrollMode = overScrollMode
    }

    fun setScrollEnable(isScrollEnable: Boolean) {
        getViewPager().isScrollEnable = isScrollEnable
    }

    fun getIsScrollEnable(): Boolean {
        return getViewPager().isScrollEnable
    }

    fun getDefaultSelectedItemId(): Int {
        val navigationView = getBottomNavigationView()
        return if (navigationView.menu.size() > 0) {
            navigationView.menu.getItem(0).itemId
        } else {
            -1
        }
    }

    fun getFragmentPagerAdapter(): FragmentPagerAdapter {
        return DefaultFragmentPagerAdapter(getSupportFragmentManager(), getFragments())
    }

    fun getSupportFragmentManager(): FragmentManager

    fun getFragments(): List<Fragment>

    class DefaultFragmentPagerAdapter(
        fm: FragmentManager,
        private val fragments: List<Fragment>
    ) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navigationView = getBottomNavigationView()
        val viewPager = getViewPager()
        for (currentMenuItem in navigationView.menu.children) {
            if (currentMenuItem.itemId == item.itemId) {
                val index = navigationView.menu.children.indexOf(currentMenuItem)
                viewPager.setCurrentItem(index, false)
                return true
            }
        }
        return false
    }

    override fun onPageSelected(position: Int) {
        val navigationView = getBottomNavigationView()
        if (position >= navigationView.menu.size) return
        menuItem = navigationView.menu.getItem(position)
        menuItem!!.isChecked = true
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    fun adaptScrollerView(view: View, paddingTop: Int = 0) {
        if (view is RecyclerView) {
            adaptRecyclerView(view, paddingTop)
        } else if (view is NestedScrollView) {
            adaptNestedScrollView(view, paddingTop)
        }
    }

    fun adaptScrollerViewFitActivity(view: View, paddingTop: Int = 0) {
        val dimensionAttrValue = AttrUtil.getDimensionAttrValue(
            view.context, R.attr.smoothActivityVerticalPadding
        ).toInt()

        val realPaddingTop = paddingTop + dimensionAttrValue

        if (view is RecyclerView) {
            adaptRecyclerView(view, realPaddingTop)
        } else if (view is NestedScrollView) {
            adaptNestedScrollView(view, realPaddingTop)
        }
    }

    fun adaptRecyclerView(recyclerView: RecyclerView, paddingBottom: Int) {
        recyclerView.clipToPadding = false
        recyclerView.setPadding(
            recyclerView.paddingLeft, recyclerView.paddingTop,
            recyclerView.paddingRight, getBottomNavigationViewHeight().toInt() + paddingBottom,
        )
    }

    fun adaptNestedScrollView(nestedScrollView: NestedScrollView, paddingBottom: Int) {
        nestedScrollView.clipToPadding = false
        nestedScrollView.setPadding(
            nestedScrollView.paddingLeft, nestedScrollView.paddingTop,
            nestedScrollView.paddingRight, getBottomNavigationViewHeight().toInt() + paddingBottom,
        )
    }

    fun getBottomNavigationViewHeight(): Float {
        return AttrUtil.getDimensionAttrValue(getContext(), R.attr.smoothBottomNavigationHeight)
    }

}