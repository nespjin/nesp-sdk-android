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

package com.nesp.sdk.android.mvp

import androidx.lifecycle.LifecycleOwner
import com.nesp.sdk.android.lifecycle.DefaultLifecycleObserver
import java.lang.ref.SoftReference

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/10/23 19:48
 * Project: NespAndroidSdk
 **/
open class BasePresenterLifecycle<V : IView> : IPresenterLifecycle<V>, DefaultLifecycleObserver {

    private var mReferenceView: SoftReference<V>? = null

    override fun attach(view: V, source: LifecycleOwner) {
        mReferenceView = SoftReference(view)
        source.lifecycle.addObserver(this)
    }

    override fun getView(): V? {
        return mReferenceView?.get()
    }

    override fun detach(source: LifecycleOwner) {
        source.lifecycle.removeObserver(this)

        mReferenceView?.clear()
        mReferenceView = null
    }

    override fun onCreate(source: LifecycleOwner) {

    }

    override fun onStart(source: LifecycleOwner) {

    }

    override fun onResume(source: LifecycleOwner) {

    }

    override fun onPause(source: LifecycleOwner) {

    }

    override fun onStop(source: LifecycleOwner) {

    }

    override fun onDestroy(source: LifecycleOwner) {

    }

    override fun onAny(source: LifecycleOwner) {

    }

}