package com.morfly.sample.profile.impl.userprofile

import com.morfly.sample.common.domain.Image
import com.morfly.sample.common.domain.ImagesRepository
import com.morfly.sample.common.domain.UsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


fun interface GetMyImages {

    operator fun invoke(): Flow<List<Image>>
}


class GetMyImagesUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    private val imagesRepository: ImagesRepository
) : GetMyImages {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(): Flow<List<Image>> =
        flow {
            emit(usersRepository.getCurrentUser())
        }.filterNotNull().flatMapLatest { currentUser ->
            imagesRepository.getUserImages(currentUser.id)
        }
}