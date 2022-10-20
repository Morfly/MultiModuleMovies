package io.morfly.streaming.data.impl.storage.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import io.morfly.streaming.data.impl.storage.entity.StoredMovie


@Dao
interface MoviesDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(movies: List<StoredMovie>)

    @Query("SELECT * FROM movies WHERE `query` = :query ORDER BY ordinal ASC")
    fun getMovies(query: String): PagingSource<Int, StoredMovie>

    @Query("SELECT * FROM movies WHERE `query` = '$QUERY_TRENDING' ORDER BY ordinal ASC")
    suspend fun getTrendingMovies(): List<StoredMovie>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Int): StoredMovie?

    @Query("DELETE FROM movies WHERE `query` = :query")
    suspend fun deleteByQuery(query: String)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()


    companion object {
        const val QUERY_TRENDING = "@trending"
    }
}
