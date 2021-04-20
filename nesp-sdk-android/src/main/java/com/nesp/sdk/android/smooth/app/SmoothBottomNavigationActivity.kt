package com.nesp.sdk.android.smooth.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.core.view.children
import androidx.core.view.size
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nesp.sdk.android.R
import com.nesp.sdk.android.databinding.ActivitySmoothBottomNavigationBinding
import com.nesp.sdk.android.smooth.widget.SmoothViewPager
import com.nesp.sdk.android.util.AttrUtil
import java.lang.reflect.Field

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/10 20:41
 * Project: NespAndroidSdk
 **/
abstract class SmoothBottomNavigationActivity : SmoothSwipeBackActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    ViewPager.OnPageChangeListener {

    private lateinit var viewBinding: ActivitySmoothBottomNavigationBinding
    private lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySmoothBottomNavigationBinding.inflate(layoutInflater)

        viewBinding.viewPager.addOnPageChangeListener(this)
        viewBinding.viewPager.adapter = getFragmentPagerAdapter()
        viewBinding.viewPager.setScrollEnable(getIsScrollEnable())
        viewBinding.viewPager.overScrollMode = getOverScrollMode()

//        inflateBottomNavigationMenu(getBottomNavigationMenuResId())

        viewBinding.navigationView.selectedItemId = getDefaultSelectedItemId()
        viewBinding.navigationView.setOnNavigationItemSelectedListener(this)
        closeAnimation(viewBinding.navigationView)
    }

    override fun swipeBackEnable(): Boolean {
        return false
    }

    protected open fun getIsScrollEnable(): Boolean {
        return false
    }

    protected fun setScrollEnable(isScrollEnable: Boolean) {
        viewBinding.viewPager.setScrollEnable(isScrollEnable)
    }

    protected open fun getDefaultSelectedItemId(): Int {
        return if (viewBinding.navigationView.menu.size() > 0) {
            viewBinding.navigationView.menu.getItem(0).itemId
        } else {
            -1
        }
    }

    private fun inflateBottomNavigationMenu(@MenuRes menuRes: Int) {
        menuInflater.inflate(menuRes, viewBinding.navigationView.menu)
    }

    protected fun getBottomNavigationView(): BottomNavigationView {
        return viewBinding.navigationView
    }

    protected open fun getFragmentPagerAdapter(): FragmentPagerAdapter {
        return DefaultFragmentPagerAdapter(supportFragmentManager, getFragments())
    }

    protected abstract fun getFragments(): List<Fragment>

    protected fun getViewPager(): SmoothViewPager {
        return viewBinding.viewPager
    }

    protected fun setOverScrollMode(overScrollMode: Int) {
        viewBinding.viewPager.overScrollMode = overScrollMode
    }

    protected open fun getOverScrollMode(): Int {
        return View.OVER_SCROLL_NEVER
    }

//    @MenuRes
//    protected abstract fun getBottomNavigationMenuResId(): Int

    private class DefaultFragmentPagerAdapter(
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
        for (currentMenuItem in viewBinding.navigationView.menu.children) {
            if (currentMenuItem.itemId == item.itemId) {
                val index = viewBinding.navigationView.menu.children.indexOf(currentMenuItem)
                viewBinding.viewPager.setCurrentItem(index, false)
                return true
            }
        }
        return false
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (position >= viewBinding.navigationView.menu.size) return
        menuItem = viewBinding.navigationView.menu.getItem(position)
        menuItem.isChecked = true
    }

    override fun onPageScrollStateChanged(state: Int) {
    }


    @SuppressLint("RestrictedApi")
    private fun closeAnimation(view: BottomNavigationView) {
        val mMenuView = view.getChildAt(0) as BottomNavigationMenuView
        for (i in 0 until mMenuView.childCount) {
            val button = mMenuView.getChildAt(i) as BottomNavigationItemView
            val mLargeLabel: TextView? =
                getField(button.javaClass, button, "largeLabel") as TextView?
            val mSmallLabel: TextView? =
                getField(button.javaClass, button, "smallLabel") as TextView?
            val mSmallLabelSize = mSmallLabel!!.textSize
            setField(button.javaClass, button, "shiftAmount", 0f)
            setField(button.javaClass, button, "scaleUpFactor", 1f)
            setField(button.javaClass, button, "scaleDownFactor", 1f)
            mLargeLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize)
        }
        mMenuView.updateMenuView()
    }

    private fun getField(targetClass: Class<*>, instance: Any, fieldName: String): Any? {
        try {
            val field: Field = targetClass.getDeclaredField(fieldName)
            field.isAccessible = true
            return field.get(instance)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return null
    }

    private fun setField(targetClass: Class<*>, instance: Any, fieldName: String, value: Any) {
        try {
            val field: Field = targetClass.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(instance, value)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
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
            this, R.attr.smoothActivityVerticalPadding
        ).toInt()

        val realPaddingTop = paddingTop + dimensionAttrValue

        if (view is RecyclerView) {
            adaptRecyclerView(view, realPaddingTop)
        } else if (view is NestedScrollView) {
            adaptNestedScrollView(view, realPaddingTop)
        }
    }

    private fun adaptRecyclerView(recyclerView: RecyclerView, paddingBottom: Int) {
        recyclerView.clipToPadding = false
        recyclerView.setPadding(
            recyclerView.paddingLeft, recyclerView.paddingTop,
            recyclerView.paddingRight, getBottomNavigationViewHeight().toInt() + paddingBottom,
        )
    }

    private fun adaptNestedScrollView(nestedScrollView: NestedScrollView, paddingBottom: Int) {
        nestedScrollView.clipToPadding = false
        nestedScrollView.setPadding(
            nestedScrollView.paddingLeft, nestedScrollView.paddingTop,
            nestedScrollView.paddingRight, getBottomNavigationViewHeight().toInt() + paddingBottom,
        )
    }

    private fun getBottomNavigationViewHeight(): Float {
        return AttrUtil.getDimensionAttrValue(this, R.attr.smoothBottomNavigationHeight)
    }
}