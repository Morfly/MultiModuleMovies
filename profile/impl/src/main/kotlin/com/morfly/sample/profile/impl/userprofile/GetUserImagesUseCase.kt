package com.morfly.sample.profile.impl.userprofile

import com.morfly.sample.common.domain.Image
import com.morfly.sample.common.domain.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


fun interface GetUserImages {

    operator fun invoke(userId: String): Flow<List<Image>>
}


class GetUserImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) : GetUserImages {

    override fun invoke(userId: String): Flow<List<Image>> =
        imagesRepository.getUserImages(userId)
}