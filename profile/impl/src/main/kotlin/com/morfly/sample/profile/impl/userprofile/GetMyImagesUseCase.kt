package com.morfly.sample.profile.impl.userprofile

import com.morfly.sample.common.domain.Image
import com.morfly.sample.common.domain.ImagesRepository
import com.morfly.sample.common.domain.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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
        }
            .filterNotNull()
            .flowOn(Dispatchers.Default)
            .flatMapLatest { currentUser ->
                imagesRepository.getUserImages(currentUser.id)
            }
}