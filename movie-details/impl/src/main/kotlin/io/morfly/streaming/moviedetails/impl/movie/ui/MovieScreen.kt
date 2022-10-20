package io.morfly.streaming.moviedetails.impl.movie.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import io.morfly.streaming.moviedetails.impl.common.reviews.ui.ReviewsFragment
import io.morfly.streaming.moviedetails.impl.movie.MovieViewModel


@Composable
fun MovieScreen(
    viewModel: MovieViewModel,
    onShowCredits: () -> Unit,
) {
    LaunchedEffect(Unit) { viewModel.load() }

    ReviewsFragment(
        viewModel,
        header = { Content(viewModel, onShowCredits) })

}

@Composable
private fun Content(
    viewModel: MovieViewModel,
    onShowCredits: () -> Unit,
) {
    viewModel.movie.collectAsState().value.let { movie ->
        if (movie != null) {
            Movie(movie, onShowCredits)
        }
    }
}