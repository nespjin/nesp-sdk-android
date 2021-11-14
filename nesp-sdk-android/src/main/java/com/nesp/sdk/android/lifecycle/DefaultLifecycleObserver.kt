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

package com.nesp.sdk.android.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/8/3 19:07
 * Project: NespAndroidSdk
 **/
interface DefaultLifecycleObserver : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> onCreate(source)
            Lifecycle.Event.ON_START -> onStart(source)
            Lifecycle.Event.ON_RESUME -> onResume(source)
            Lifecycle.Event.ON_PAUSE -> onPause(source)
            Lifecycle.Event.ON_STOP -> onStop(source)
            Lifecycle.Event.ON_DESTROY -> onDestroy(source)
            Lifecycle.Event.ON_ANY -> onAny(source)
        }
    }


    fun onCreate(source: LifecycleOwner)

    fun onStart(source: LifecycleOwner)

    fun onResume(source: LifecycleOwner)

    fun onPause(source: LifecycleOwner)

    fun onStop(source: LifecycleOwner)

    fun onDestroy(source: LifecycleOwner)

    fun onAny(source: LifecycleOwner)

}