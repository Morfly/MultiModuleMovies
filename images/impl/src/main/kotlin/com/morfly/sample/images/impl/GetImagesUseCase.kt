package com.morfly.sample.images.impl

import androidx.paging.PagingData
import com.morfly.sample.common.domain.Image
import com.morfly.sample.common.domain.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


fun interface GetImages {

    operator fun invoke(query: String): Flow<PagingData<Image>>
}


class GetImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) : GetImages {

    override fun invoke(query: String): Flow<PagingData<Image>> =
        imagesRepository.getPagedImages(query)
}