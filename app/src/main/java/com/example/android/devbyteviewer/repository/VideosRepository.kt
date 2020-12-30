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
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


/*Repository for fetching videos from network and writing them to disk*/

//pass database directly to the constructor instead of context
class VideosRepository(private val database: VideosDatabase) {


    //REFRESH OFFLINE CACHE

    //mark with 'suspend' to make fxn Coroutine Friendly
    suspend fun refreshVideos() {

        //switch to IO
        withContext(IO) {

            //read from network
            val playlist = Network.devbytes.getPlaylist()

            //insert video into database
            database.videoDAO.insertAll(*playlist.asDatabaseModel())
        }
    }


    //LOAD VIDEO DATA FROM OFFLINE CACHE

    //this returns a LiveData Object that callers can observe
    val videos: LiveData<List<Video>> = Transformations.

            //map from database video to domain video
    map(database.videoDAO.getVideos()) {

        it.asDomainModel()
    }


}