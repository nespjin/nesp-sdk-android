package com.nesp.sdk.android.core.ktx.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/12/3 22:30
 * Project: FishAccountingAndroid
 **/
fun coroutine(block: suspend () -> Unit) { CoroutineScope(Dispatchers.IO).launch { block() } }
fun coroutineMain(block: suspend () -> Unit) { CoroutineScope(Dispatchers.Main).launch { block() } }