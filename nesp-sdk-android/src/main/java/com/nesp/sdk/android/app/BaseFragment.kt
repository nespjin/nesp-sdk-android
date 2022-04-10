package com.nesp.sdk.android.app

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/12 10:08
 **/
abstract class BaseFragment : Fragment(), IComponent {

    override val act: Activity
        get() = requireActivity()

    override val ctx: Context
        get() = requireContext()

    private var firstFlags = 0x00
    protected fun resetFirstFlags() {
        firstFlags = 0x00
    }

    private fun isFirstStart() = firstFlags and 0x01 == 0
    private fun setNotFirstStart() {
        firstFlags = firstFlags or 0x01
    }

    private fun isFirstResume() = (firstFlags ushr 1) and 0x01 == 0
    private fun setNotFirstResume() {
        firstFlags = firstFlags or (0x01 shl 1)
    }

    override fun onStart() {
        super.onStart()
        if (isFirstStart()) {
            onFirstStart()
            setNotFirstStart()
        }
    }

    open fun onFirstStart() {

    }

    override fun onResume() {
        super.onResume()
        if (isFirstResume()) {
            onFirstResume()
            setNotFirstResume()
        }
    }

    open fun onFirstResume() {

    }

    protected fun fitStatus() {
        fitStatus(requireView())
    }

    private fun fitStatus(view: View) {
        if (view is ViewGroup) {
            val children = view.children
            for (child in children) {
                fitStatus(child)
            }
        }

        if (view.fitsSystemWindows) {
            view.setPadding(
                view.paddingLeft,
                view.paddingTop + getStatusBarHeight(),
                view.paddingRight,
                view.paddingBottom
            )
        }
    }

    private fun getStatusBarHeight(): Int {
        if (statusBarHeight > 0) return statusBarHeight
        //获取状态栏高度的资源id
        val resourceId: Int =
            requireActivity().resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = requireActivity().resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    companion object {
        private const val TAG = "BaseFragment"
        private var statusBarHeight = 0
    }

}