package io.morfly.streaming.moviesearch.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.morfly.streaming.common.domain.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class MovieSearchViewModel @Inject constructor(
    private val getMovies: GetMovies
) : ViewModel() {

    private var query: String? = null
    private var instantRefresh = false
    private val searchQueryFlow = MutableSharedFlow<String?>(replay = 1)
    val displayedSearchQuery: Flow<String> = searchQueryFlow.map { it.orEmpty() }
    private val selectedMovieFlow = MutableStateFlow<Movie?>(value = null)

    val selectedMovie: StateFlow<Movie?> = selectedMovieFlow

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    internal val _movies = searchQueryFlow
        .onEach { query = it }
        .debounce { if (instantRefresh) 0 else QUERY_INPUT_DELAY_MILLIS }
        .onEach { instantRefresh = false }
        .flatMapLatest { getMovies(query) }

    val movies: Flow<PagingData<Movie>> = _movies.cachedIn(viewModelScope)

    fun updateSearchQuery(newQuery: String) {
        val formattedQuery = newQuery.ifBlank { null }
        if (formattedQuery != query) {
            searchQueryFlow.tryEmit(formattedQuery)
        }
    }

    fun refresh() {
        instantRefresh = true
        searchQueryFlow.tryEmit(query)
    }

    fun showMovieDetails(movie: Movie) {
        selectedMovieFlow.value = movie
    }

    fun closeMovieDetails() {
        selectedMovieFlow.value = null
    }

    companion object {

        private const val QUERY_INPUT_DELAY_MILLIS = 1000L
    }
}