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

package com.example.android.devbyteviewer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao {
    @Query("select * from databasevideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseVideo)
}

// TODO (01) Create an abstract VideosDatabase class that extends RoomDatabase.

// TODO (02) Annotate VideosDatabase with @Database,including entities and version.

// TODO (03) Inside VideosDatabase, create abstract val videoDao.

// TODO (04) Create an INSTANCE variable to store the VideosDatabase singleton.

// TODO (05) Define a function getDatabase() that returns the VideosDatabase INSTANCE.

// TODO (06) Inside getDatabase(), before returning INSTANCE, use a synchronized{} block to
// check whether INSTANCE is initialized, and, if it isn’t, use DatabaseBuilder to create it.
