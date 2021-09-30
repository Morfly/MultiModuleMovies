package com.morfly.sample.images.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.morfly.sample.common.di.FeatureScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@FeatureScoped
class ImagesViewModel @Inject constructor(
    private val getImages: GetImages,
    getEmptyFeed: GetEmptyFeed
) : ViewModel() {

    private val searchQueryFlow = MutableStateFlow<String?>(value = null)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val images = searchQueryFlow
        .filterNotNull()
        .debounce(QUERY_INPUT_DELAY_MILLIS)
        .flatMapLatest { getImages(query = it) }
        .cachedIn(viewModelScope)

    val searchSuggestion: String =
        getEmptyFeed().searchSuggestion

    fun updateSearchQuery(query: String) {
        searchQueryFlow.value = query
    }


    companion object {

        private const val QUERY_INPUT_DELAY_MILLIS = 1000L
    }
}