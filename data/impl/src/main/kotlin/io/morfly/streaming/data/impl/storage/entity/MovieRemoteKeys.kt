package io.morfly.streaming.data.impl.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_remote_keys")
data class MovieRemoteKeys(
    @PrimaryKey @ColumnInfo(name = "movie_id") val movieId: String,
    @ColumnInfo(name = "prev_key") val prevKey: Int?,
    @ColumnInfo(name = "next_key") val nextKey: Int?
)