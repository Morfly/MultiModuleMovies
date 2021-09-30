/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morfly.sample.data.impl.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.morfly.sample.data.impl.storage.entity.ImageRemoteKeys


@Dao
interface ImageRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<ImageRemoteKeys>)

    @Query("SELECT * FROM image_remote_keys WHERE image_id = :imageId")
    suspend fun imageRemoteKeys(imageId: String): ImageRemoteKeys?

    @Query("DELETE FROM image_remote_keys")
    suspend fun deleteAll()
}