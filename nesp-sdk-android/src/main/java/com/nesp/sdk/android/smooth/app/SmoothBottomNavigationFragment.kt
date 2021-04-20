package com.nesp.sdk.android.smooth.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nesp.sdk.android.databinding.FragmentSmoothBottomNavigationBinding

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/4/20 23:05
 * Project: NespAndroidSdkSample
 **/
abstract class SmoothBottomNavigationFragment : SmoothBaseFragment() {

    private var viewBinding: FragmentSmoothBottomNavigationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSmoothBottomNavigationBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}