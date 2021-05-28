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

import java.lang.ref.SoftReference

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/10/23 19:55
 * Project: FishAccountingAndroid
 **/
open class BasePresenter<V : IView> : IPresenter<V> {

    private var mReferenceView: SoftReference<V>? = null

    override fun attach(view: V) {
        mReferenceView = SoftReference(view)
    }

    override fun getView(): V? {
        return mReferenceView?.get()
    }

    override fun detach() {
        mReferenceView?.clear()
        mReferenceView = null
    }

}