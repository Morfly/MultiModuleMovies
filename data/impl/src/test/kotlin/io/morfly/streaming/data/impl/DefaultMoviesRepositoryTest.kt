@file:OptIn(ExperimentalCoroutinesApi::class)

package io.morfly.streaming.data.impl

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.data.impl.mapping.DataMapper
import io.morfly.streaming.data.impl.network.MovieDbApi
import io.morfly.streaming.data.impl.storage.AppDatabase
import io.morfly.streaming.data.impl.storage.dao.MoviesDao
import io.morfly.streaming.data.impl.storage.entity.StoredMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test


class DefaultMoviesRepositoryTest {
    private val storedMovie = mockk<StoredMovie>()
    private val domainMovie1 = mockk<Movie>()
    private val domainMovie2 = mockk<Movie>()
    private val networkService = mockk<MovieDbApi>()
    private val moviesDao = mockk<MoviesDao> {
        coEvery { getMovieById(1) } returns storedMovie
        coEvery { getMovieById(-1) } returns null
    }
    private val database = mockk<AppDatabase>() {
        every { moviesDao() } returns moviesDao
    }
    private val mapper = mockk<DataMapper>()

    private val moviesRepository = spyk(
        DefaultMoviesRepository(
            remoteMediator = mockk(relaxed = true),
            networkService, mapper, database
        )
    )

    @Test
    fun `get movie by id`() = runTest {
        every { mapper.storageToDomain(storedMovie) } returns domainMovie1
        val movie = moviesRepository.getMovieById(1)

        assertEquals(movie, domainMovie1)
    }

    @Test
    fun `get null if movie is not stored`() = runTest {
        val movie = moviesRepository.getMovieById(-1)

        assertNull(movie)
    }

    @Test
    fun `get trending movies from database and then from network`() = runTest {
        coEvery { moviesRepository.getTrendingMoviesFromStorage() } returns listOf(domainMovie1)
        coEvery { moviesRepository.getTrendingMoviesFromNetwork() } returns listOf(domainMovie2)

        val movies = moviesRepository.getTrendingMovies().take(2).toList()

        assertEquals(movies.size, 2)
        assertEquals(movies.first(), listOf(domainMovie1))
        assertEquals(movies.last(), listOf(domainMovie2))
    }

    @Test
    fun `get only network movies if database returns null`() = runTest {
        coEvery { moviesRepository.getTrendingMoviesFromStorage() } returns null
        coEvery { moviesRepository.getTrendingMoviesFromNetwork() } returns listOf(domainMovie2)

        val movies = moviesRepository.getTrendingMovies().take(1).toList()

        assertEquals(movies.size, 1)
        assertEquals(movies.first(), listOf(domainMovie2))
    }

    @Test
    fun `get only network movies if database returns empty list`() = runTest {
        coEvery { moviesRepository.getTrendingMoviesFromStorage() } returns emptyList()
        coEvery { moviesRepository.getTrendingMoviesFromNetwork() } returns listOf(domainMovie2)

        val movies = moviesRepository.getTrendingMovies().take(1).toList()

        assertEquals(movies.size, 1)
        assertEquals(movies.first(), listOf(domainMovie2))
    }
}