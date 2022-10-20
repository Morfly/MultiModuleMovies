package io.morfly.streaming.moviesearch.impl.ui.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.common.ui.Dimens.bottomPadding


@Composable
fun MovieSearchGrid(movies: LazyPagingItems<Movie>, onMovieSelected: (Movie) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = bottomPadding)
    ) {
        items(movies.itemCount) { index ->
            movies[index]?.let { movie ->
                MovieItem(movie, onMovieSelected)
            }
        }
    }
}