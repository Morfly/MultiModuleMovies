package com.morfly.sample.images.impl

import androidx.paging.PagingData
import com.morfly.sample.common.domain.Image
import kotlinx.coroutines.flow.Flow


sealed class ImagesViewState(
    val searchQuery: String?
) {

    class Empty(
        val searchSuggestion: String,
    ) : ImagesViewState(searchQuery = null)

    class LoadingOnEmpty(
        val searchSuggestion: String,
        searchQuery: String?
    ) : ImagesViewState(searchQuery)

    class Loading(
        val images: Flow<PagingData<Image>>,
        searchQuery: String?
    ) : ImagesViewState(searchQuery)

    class Loaded(
        val images: Flow<PagingData<Image>>,
        searchQuery: String?
    ) : ImagesViewState(searchQuery)
}