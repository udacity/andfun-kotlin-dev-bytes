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

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

//DAO
@Dao
interface VideoDAO {

    /*LiveData in ROOM lets you query from the UI thread and will watch for changes in the database. ROOM
    runs this query in the background thread. UI watches on the changes*/

    @Query("SELECT * FROM databasevideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>


    /*vararg means variable arguments and it is how a function can take an unknown number of arguments in
    Kotlin.It will actually pass an array under the hood.This way colors can pass a few videos without
    making a list*/

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg: DatabaseVideo)
}


//ROOM
/*ROOM requires we declare all entities held in this database; passed as an array*/
@Database(entities = [DatabaseVideo::class], version = 1, exportSchema = false)
abstract class VideosDatabase : RoomDatabase() {
    //abstract DAO val to get access to VideoDAO
    abstract val videoDAO: VideoDAO

    companion object {


        /* late init variable instance that holds our singleton, it's private to other
        classes to restrict direct access, Volatile to avoid caching*/
        @Volatile
        private lateinit var INSTANCE: VideosDatabase


        //public way to get Database method, returns INSTANCE after it has been initialized
        fun getInstance(context: Context): VideosDatabase {


            //make initialization thread-safe by wrapping it around a Synchronized block


            //figure out if INSTANCE is initialized we use Kotlin fxn isInitialized available on late init
            // variables to check if they have been assigned to something
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {

                    //if not initialized, then we initialize INSTANCE  inside the if-statement
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext, VideosDatabase::class.java, "videos")
                            .fallbackToDestructiveMigration()
                            .build()
                }

                //return INSTANCE after if-statement
                return INSTANCE

            }
        }


    }
}


/*@Database(entities = [DatabaseVideo::class], version = 1, exportSchema = false)
abstract class OfflineDatabase: RoomDatabase(){


    abstract val videoDAO:VideoDAO

    companion object{

        @Volatile
        private var INSTANCE:OfflineDatabase? = null

        fun getInstance(context: Context):OfflineDatabase{


            synchronized(this){

                var instance = INSTANCE

                if (instance == null) {

                    instance = Room.databaseBuilder(context.applicationContext, OfflineDatabase::class
                            .java,"database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }


        }

    }*/








