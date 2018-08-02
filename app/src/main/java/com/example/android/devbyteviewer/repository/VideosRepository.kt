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

package com.example.android.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.asDatabaseModel
import com.example.android.devbyteviewer.util.launchBackground

/**
 * Repository for fetching devbyte videos from the network and storing them on disk
 */
class VideosRepository(private val database: VideosDatabase) {

    /**
     * Force a refresh of data from the network.
     *
     * Does not return the data fetched. Prefer to use [loadVideos] in application code. This method
     * is available for background sync tasks.
     */
    fun refreshFromNetwork() = launchBackground {
        val playlist = Network.devbytes.getPlaylist().await()
        database.videoDao.insertAll(*playlist.asDatabaseModel())
    }

    /**
     * Load videos from the repository.
     *
     * @return LiveData providing a list of videos
     */
    fun loadVideos(): LiveData<List<Video>> {
        refreshFromNetwork() // do not wait for async call

        return map(database.videoDao.getVideos()) { it.asDomainModel() }
    }
}