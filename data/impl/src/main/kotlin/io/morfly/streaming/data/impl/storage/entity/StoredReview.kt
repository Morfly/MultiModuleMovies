package io.morfly.streaming.data.impl.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "reviews")
data class StoredReview(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @Embedded val author: StoredAuthor,
    val content: String,
    @ColumnInfo(name = "created_at") val createdAt: Date,
    @ColumnInfo(name = "updated_at") val updatedAt: Date
)

data class StoredAuthor(
    val name: String,
    val username: String,
    @ColumnInfo(name = "avatar_path") val avatarPath: String?,
    val rating: Int?
)
