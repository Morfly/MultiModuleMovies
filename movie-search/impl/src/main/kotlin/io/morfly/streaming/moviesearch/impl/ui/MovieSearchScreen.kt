package io.morfly.streaming.moviesearch.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.moviesearch.impl.MovieSearchViewModel
import io.morfly.streaming.moviesearch.impl.R
import io.morfly.streaming.moviesearch.impl.ui.details.MovieDetailsPopup
import io.morfly.streaming.moviesearch.impl.ui.search.MovieSearchGrid
import io.morfly.streaming.moviesearch.impl.ui.search.NoMovies
import io.morfly.streaming.moviesearch.impl.ui.search.SearchField
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private sealed interface State {
    val movies: LazyPagingItems<Movie>
    val searchQuery: String

    class EmptyFeed(
        override val searchQuery: String,
        override val movies: LazyPagingItems<Movie>
    ) : State

    class FeedWithContent(
        override val searchQuery: String,
        override val movies: LazyPagingItems<Movie>
    ) : State

    class MovieDetailsPopup(
        override val searchQuery: String,
        override val movies: LazyPagingItems<Movie>,
        val selectedMovie: Movie
    ) : State
}

@Composable
fun MovieSearchScreen(viewModel: MovieSearchViewModel, onExpandMovieDetails: (Movie) -> Unit) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val selectedMovie by viewModel.selectedMovie.collectAsState()
    val query by viewModel.displayedSearchQuery.collectAsState(initial = "")

    LaunchedEffect(Unit) {
        if (movies.itemCount == 0) {
            viewModel.refresh()
        }
    }

    val state = remember(
        key1 = selectedMovie != null,
        key2 = movies.itemCount == 0,
        key3 = query
    ) {
        when {
            selectedMovie != null -> State.MovieDetailsPopup(query, movies, selectedMovie!!)
            movies.itemCount == 0 -> State.EmptyFeed(query, movies)
            else -> State.FeedWithContent(query, movies)
        }
    }

    RenderState(
        state,
        onUpdateSearchQuery = viewModel::updateSearchQuery,
        onMovieSelected = viewModel::showMovieDetails,
        onCloseMovieDetails = viewModel::closeMovieDetails,
        onRefresh = viewModel::refresh,
        onExpandMovieDetails
    )
}

@Composable
private fun RenderState(
    state: State,
    onUpdateSearchQuery: (String) -> Unit,
    onMovieSelected: (Movie) -> Unit,
    onCloseMovieDetails: () -> Unit,
    onRefresh: () -> Unit,
    onExpandMovieDetails: (Movie) -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }
    isRefreshing = when (state.movies.loadState.refresh) {
        LoadState.Loading -> true
        else -> false
    }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    val context = LocalContext.current
    Box {
        if (state is State.EmptyFeed) {
            NoMovies()
        }
        Column {
            SearchField(
                value = state.searchQuery,
                onValueChange = { newQuery ->
                    onUpdateSearchQuery(newQuery)
                },
                onClick = {
                    onUpdateSearchQuery("")
                },
                hint = context.getString(R.string.search_hint)
            )
            SwipeRefresh(
                swipeRefreshState,
                onRefresh = {
                    isRefreshing = true
                    onRefresh()
                },
                Modifier.fillMaxSize()
            ) {
                MovieSearchGrid(state.movies, onMovieSelected)
            }
        }

        val coroutineScope = rememberCoroutineScope()

        if (state is State.MovieDetailsPopup) {
            MovieDetailsPopup(
                movie = state.selectedMovie,
                onClosed = onCloseMovieDetails,
                onExpandMovieDetails = { movie ->
                    onExpandMovieDetails(movie)
                    coroutineScope.launch {
                        delay(300)
                        onCloseMovieDetails()
                    }
                }
            )
        }
    }
}