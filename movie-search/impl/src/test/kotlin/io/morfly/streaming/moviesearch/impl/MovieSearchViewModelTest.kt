@file:OptIn(ExperimentalCoroutinesApi::class)

package io.morfly.streaming.moviesearch.impl

import androidx.paging.PagingData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.morfly.streaming.common.domain.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test


class MovieSearchViewModelTest {

    private val getMovies = mockk<GetMovies>()
    private val viewModel = MovieSearchViewModel(getMovies)

    @Test
    fun `observing paging data`() = runTest {
        val pagingData = PagingData.from(listOf<Movie>())
        every { getMovies.invoke(null) } returns flowOf(pagingData)

        val result = viewModel._movies.first()

        assertEquals(result, pagingData)
    }

    @Test
    fun `observing paging after refresh`() = runTest {
        val pagingData = PagingData.from(listOf<Movie>())
        every { getMovies.invoke(null) } returns flowOf(pagingData)

        launch {
            delay(1)
            viewModel.refresh()
        }
        val result = viewModel._movies.take(2).toList()

        assertEquals(result.size, 2)
        assertEquals(result.first(), pagingData)
        assertEquals(result.last(), pagingData)
        verify(exactly = 2) { getMovies.invoke(null) }
    }

    @Test
    fun `observing paging after query update`() = runTest {
        val pagingData1 = PagingData.from(listOf<Movie>(mockk()))
        val pagingData2 = PagingData.from(listOf<Movie>(mockk()))
        every { getMovies.invoke(null) } returns flowOf(pagingData1)
        every { getMovies.invoke("query") } returns flowOf(pagingData2)

        launch {
            delay(1)
            viewModel.updateSearchQuery("query")
        }
        val result = viewModel._movies.take(2).toList()

        assertEquals(result.size, 2)
        assertEquals(result.first(), pagingData1)
        assertEquals(result.last(), pagingData2)
        verify(exactly = 1) { getMovies.invoke(null) }
        verify(exactly = 1) { getMovies.invoke("query") }
    }

    @Test
    fun `observing selected movie on init`() = runTest {
        val result = viewModel.selectedMovie.value

        assertNull(result)
    }

    @Test
    fun `observing selected movie on update`() = runTest {
        val movie = mockk<Movie>()
        viewModel.showMovieDetails(movie)

        val result = viewModel.selectedMovie.value

        assertEquals(result, movie)
    }

    @Test
    fun `observing selected movie after closing`() = runTest {
        viewModel.showMovieDetails(mockk())
        viewModel.closeMovieDetails()

        val result = viewModel.selectedMovie.value

        assertNull(result)
    }
}