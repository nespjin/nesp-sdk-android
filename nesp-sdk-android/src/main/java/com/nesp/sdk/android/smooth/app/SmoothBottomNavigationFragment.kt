/*
 * Copyright (C) 2021 The NESP Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nesp.sdk.android.smooth.app

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nesp.sdk.android.databinding.FragmentSmoothBottomNavigationBinding
import com.nesp.sdk.android.smooth.widget.SmoothViewPager

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2021/4/20 23:05
 * Project: NespAndroidSdkSample
 **/
abstract class SmoothBottomNavigationFragment : SmoothBaseFragment(), ISmoothBottomNavigationPage {

    private var viewBinding: FragmentSmoothBottomNavigationBinding? = null

    override var menuItemSelected: MenuItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSmoothBottomNavigationBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewPager()
    }
    override fun getSupportFragmentManager(): FragmentManager {
        return requireActivity().supportFragmentManager
    }

    override fun getBottomNavigationView(): BottomNavigationView {
        return viewBinding!!.navigationView
    }

    override fun getViewPager(): SmoothViewPager {
        return viewBinding!!.viewPager
    }

    override fun getContext(): Context {
        return requireContext()
    }

    override fun getMenuInflater(): MenuInflater {
        return requireActivity().menuInflater
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}