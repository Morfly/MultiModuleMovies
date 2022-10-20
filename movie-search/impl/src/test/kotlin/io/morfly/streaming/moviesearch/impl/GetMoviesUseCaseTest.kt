@file:OptIn(ExperimentalCoroutinesApi::class)

package io.morfly.streaming.moviesearch.impl

import androidx.paging.PagingData
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.common.domain.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class GetMoviesUseCaseTest {

    private val moviesRepository = mockk<MoviesRepository>()
    private val useCase = spyk(GetMoviesUseCase(moviesRepository, UnconfinedTestDispatcher()))

    private val movie = mockk<Movie>()

    @Test
    fun `return trending movies if query is null`() = runTest {
        val pagingData = PagingData.from(listOf(movie))
        every { useCase.getTrendingMovies() } returns flowOf(pagingData)

        val movies = useCase.invoke(null)

        assertEquals(movies.first(), pagingData)
        verify(exactly = 1) { useCase.getTrendingMovies() }
        verify(exactly = 0) { useCase.searchMovies(any()) }
    }

    @Test
    fun `return trending movies if query is empty`() = runTest {
        val pagingData = PagingData.from(listOf(movie))
        every { useCase.getTrendingMovies() } returns flowOf(pagingData)

        val movies = useCase.invoke("")

        assertEquals(movies.first(), pagingData)
        verify(exactly = 1) { useCase.getTrendingMovies() }
        verify(exactly = 0) { useCase.searchMovies(any()) }
    }

    @Test
    fun `return trending movies if query is blank`() = runTest {
        val pagingData = PagingData.from(listOf(movie))
        every { useCase.getTrendingMovies() } returns flowOf(pagingData)

        val movies = useCase.invoke("  ")

        assertEquals(movies.first(), pagingData)
        verify(exactly = 1) { useCase.getTrendingMovies() }
        verify(exactly = 0) { useCase.searchMovies(any()) }
    }

    @Test
    fun `return movies for a valid query`() = runTest {
        val pagingData = PagingData.from(listOf(movie))
        every { useCase.searchMovies("query") } returns flowOf(pagingData)

        val movies = useCase.invoke("query")

        assertEquals(movies.first(), pagingData)
        verify(exactly = 0) { useCase.getTrendingMovies() }
        verify(exactly = 1) { useCase.searchMovies("query") }
    }

    @Test
    fun `searchMovies returns correct paging result`() = runTest {
        val pagingData = PagingData.from(listOf(movie))
        every { moviesRepository.getMovies("query") } returns flowOf(pagingData)

        val movies = useCase.searchMovies("query")

        assertEquals(movies.first(), pagingData)
    }
}