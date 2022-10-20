@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)

package io.morfly.streaming.data.impl

import androidx.paging.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.morfly.streaming.data.impl.mapping.DefaultDataMapper
import io.morfly.streaming.data.impl.network.MovieDbApi
import io.morfly.streaming.data.impl.network.model.MovieDbMovie
import io.morfly.streaming.data.impl.network.model.MovieDbMoviesResponse
import io.morfly.streaming.data.impl.storage.AppDatabase
import io.morfly.streaming.data.impl.storage.dao.MovieRemoteKeysDao
import io.morfly.streaming.data.impl.storage.entity.MovieRemoteKeys
import io.morfly.streaming.data.impl.storage.entity.StoredMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test


class DefaultMoviesRemoteMediatorTest {
    private val networkService = mockk<MovieDbApi>()
    private val movieRemoteKeysDao = mockk<MovieRemoteKeysDao>()
    private val database = mockk<AppDatabase> {
        every { moviesDao() } returns mockk()
        every { movieRemoteKeysDao() } returns movieRemoteKeysDao
    }
    private val mapper = DefaultDataMapper()

    private val remoteMediator = spyk(
        MoviesRemoteMediator(query = "query", networkService, database, mapper)
    ) {
        coEvery { writeToDatabase(any(), any()) } answers {}
    }

    @Test
    fun `end pagination with success on PREPEND`() = runTest {
        val pagingState = PagingState<Int, StoredMovie>(
            listOf(), null, PagingConfig(20), 10
        )
        val result = remoteMediator.load(LoadType.PREPEND, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        with((result as RemoteMediator.MediatorResult.Success)) {
            assertTrue(endOfPaginationReached)
        }
    }

    @Test
    fun `end pagination with empty data on APPEND`() = runTest {
        val pagingState = PagingState<Int, StoredMovie>(
            listOf(), null, PagingConfig(20), 10
        )
        val result = remoteMediator.load(LoadType.APPEND, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        with((result as RemoteMediator.MediatorResult.Success)) {
            assertTrue(endOfPaginationReached)
        }
    }

    @Test
    fun `return first page on REFRESH`() = runTest {
        // Given
        val response = MovieDbMovie(
            id = 1,
            title = "title",
            overview = "overview",
            backdropPath = "/path.jpg",
            posterPath = "/path.jpg",
            voteAverage = 5.3f,
            voteCount = 30
        )
        coEvery {
            networkService.searchMovies("query", 1)
        } returns MovieDbMoviesResponse(page = 1, listOf(response))
        val pagingState = PagingState<Int, StoredMovie>(
            listOf(), null, PagingConfig(20), 10
        )

        // When
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        with((result as RemoteMediator.MediatorResult.Success)) {
            assertFalse(endOfPaginationReached)
        }
    }

    @Test
    fun `return second page on APPEND`() = runTest {
        // Given
        val response = MovieDbMovie(
            id = 737,
            title = "title",
            overview = "overview",
            backdropPath = "/path.jpg",
            posterPath = "/path.jpg",
            voteAverage = 5.3f,
            voteCount = 30
        )
        coEvery {
            networkService.searchMovies("query", 2)
        } returns MovieDbMoviesResponse(page = 2, listOf(response))
        val pagingState = PagingState<Int, StoredMovie>(
            listOf(), null, PagingConfig(20), 10
        )
        every { remoteMediator.findLastItem(pagingState) } returns mockk {
            every { id } returns 737
        }
        coEvery {
            movieRemoteKeysDao.movieRemoteKeys(737)
        } returns MovieRemoteKeys(movieId = 737, prevKey = null, nextKey = 2)

        // When
        val result = remoteMediator.load(LoadType.APPEND, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        with((result as RemoteMediator.MediatorResult.Success)) {
            assertFalse(endOfPaginationReached)
        }
    }

    @Test
    fun `correctly calculate current offset`() {
        val offset = remoteMediator.calculateOffset(pageSize = 20, loadKey = 3)
        assertEquals(offset, 40)
    }
}