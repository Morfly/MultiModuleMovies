package io.morfly.streaming.data.impl.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import io.morfly.streaming.data.impl.storage.entity.ReviewRemoteKeys


@Dao
interface ReviewRemoteKeysDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(remoteKeys: List<ReviewRemoteKeys>)

    @Query("SELECT * FROM review_remote_keys WHERE review_id = :reviewId")
    suspend fun reviewRemoteKeys(reviewId: String): ReviewRemoteKeys?

    @Query("DELETE FROM review_remote_keys")
    suspend fun deleteAll()
}