package io.morfly.streaming.data.impl.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.morfly.streaming.data.impl.storage.dao.MovieRemoteKeysDao
import io.morfly.streaming.data.impl.storage.dao.MoviesDao
import io.morfly.streaming.data.impl.storage.dao.ReviewRemoteKeysDao
import io.morfly.streaming.data.impl.storage.dao.ReviewsDao
import io.morfly.streaming.data.impl.storage.entity.MovieRemoteKeys
import io.morfly.streaming.data.impl.storage.entity.ReviewRemoteKeys
import io.morfly.streaming.data.impl.storage.entity.StoredMovie
import io.morfly.streaming.data.impl.storage.entity.StoredReview


@Database(
    entities = [StoredMovie::class, MovieRemoteKeys::class, StoredReview::class, ReviewRemoteKeys::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    abstract fun movieRemoteKeysDao(): MovieRemoteKeysDao

    abstract fun reviewsDao(): ReviewsDao

    abstract fun reviewRemoteKeysDao(): ReviewRemoteKeysDao

    companion object {
        private const val DATABASE_NAME = "app_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context)
                    .also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}