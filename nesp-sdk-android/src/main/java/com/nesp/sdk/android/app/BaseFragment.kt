package com.nesp.sdk.android.app

import android.app.Activity
import android.content.Context
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

    protected var isFirstStart = true
        private set

    override fun onStart() {
        super.onStart()
        if (isFirstStart) {
            onFirstStart()
        }
    }

    open fun onFirstStart() {

    }

    companion object {
        private const val TAG = "BaseFragment"
    }

}