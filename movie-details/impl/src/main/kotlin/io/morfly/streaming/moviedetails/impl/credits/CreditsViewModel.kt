package io.morfly.streaming.moviedetails.impl.credits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.morfly.streaming.common.di.CommonModule.IO
import io.morfly.streaming.common.domain.Credit
import io.morfly.streaming.moviedetails.impl.di.MovieId
import io.morfly.streaming.moviedetails.impl.movie.GetMovie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


class CreditsViewModel @Inject constructor(
    @MovieId private val movieId: Int,
    private val getMovie: GetMovie,
    private val getCredits: GetCredits,
    @Named(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val mutableCredits = MutableStateFlow<Map<Department, List<Credit>>>(emptyMap())
    val credits: StateFlow<Map<Department, List<Credit>>> = mutableCredits

    private val mutableMovieTitle = MutableStateFlow<String?>(null)
    val movieTitle: StateFlow<String?> = mutableMovieTitle

    fun load() {
        viewModelScope.launch(ioDispatcher) {
            val movie = async { getMovie(movieId) }
            val credits = async { getCredits(movieId) }

            mutableMovieTitle.value = movie.await()?.title
            mutableCredits.value = credits.await()
        }
    }
}