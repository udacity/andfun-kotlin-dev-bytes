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

package com.example.android.devbyteviewer.work

import androidx.work.Worker
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import kotlinx.coroutines.experimental.runBlocking
import retrofit2.HttpException

/**
 * RefreshDataWorker will fetch new results from the network and store them in the database
 */
class RefreshDataWorker : Worker() {

    override fun doWork() = runBlocking {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)
        val result = try {
            repository.refreshFromNetwork().join()
            Result.SUCCESS
        } catch (e: HttpException) {
            Result.RETRY
        }
        result
    }
}