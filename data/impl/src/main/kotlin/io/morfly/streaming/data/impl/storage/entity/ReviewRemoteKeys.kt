package io.morfly.streaming.data.impl.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "review_remote_keys")
data class ReviewRemoteKeys(
    @PrimaryKey @ColumnInfo(name = "review_id") val reviewId: String,
    @ColumnInfo(name = "prev_key") val prevKey: Int?,
    @ColumnInfo(name = "next_key") val nextKey: Int?
)
