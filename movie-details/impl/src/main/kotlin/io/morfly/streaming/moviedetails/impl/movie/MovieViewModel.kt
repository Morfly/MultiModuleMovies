package io.morfly.streaming.moviedetails.impl.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import io.morfly.streaming.common.di.CommonModule
import io.morfly.streaming.common.di.CommonModule.IO
import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.common.domain.Review
import io.morfly.streaming.moviedetails.impl.common.reviews.GetReviews
import io.morfly.streaming.moviedetails.impl.common.reviews.ReviewItem
import io.morfly.streaming.moviedetails.impl.common.reviews.ReviewsViewModel
import io.morfly.streaming.moviedetails.impl.di.MovieId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


class MovieViewModel @Inject constructor(
    @MovieId private val movieId: Int,
    private val getMovie: GetMovie,
    private val getReviews: GetReviews,
    @Named(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), ReviewsViewModel {

    override val reviews: Flow<PagingData<ReviewItem>> by lazy {
        getReviews(movieId)
            .map { it.map { item -> ReviewItem(item) } }
            .cachedIn(viewModelScope)
    }

    private val mutableMovie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = mutableMovie

    fun load() {
        viewModelScope.launch(ioDispatcher) {
            mutableMovie.value = getMovie(movieId)
        }
    }
}