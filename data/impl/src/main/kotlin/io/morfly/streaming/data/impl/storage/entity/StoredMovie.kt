package io.morfly.streaming.data.impl.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
data class StoredMovie(
    @PrimaryKey val localId: String,
    val id: Int,
    val ordinal: Int,
    val query: String,
    val title: String,
    val overview: String,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "vote_average") val voteAverage: Float,
    @ColumnInfo(name = "vote_count") val voteCount: Int,
)