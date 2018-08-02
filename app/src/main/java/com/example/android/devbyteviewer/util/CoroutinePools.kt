/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.devbyteviewer.util

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import java.util.concurrent.ThreadPoolExecutor

/**
 * Make a new coroutine dispatcher backed by a [ThreadPoolExecutor] for processing IO based tasks.
 */
private val backgroundPool = newFixedThreadPoolContext(2, "background-coroutines")

/**
 * Launcher for executing a task on background threads. Does not return a result.
 *
 * ```
 * launchBackground {
 *    callSuspendFunction
 * }
 * ```
 */
fun launchBackground(block: suspend CoroutineScope.() -> Unit): Job {
    return launch(backgroundPool, block = block)
}