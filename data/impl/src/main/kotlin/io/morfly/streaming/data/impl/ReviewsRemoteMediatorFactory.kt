package io.morfly.streaming.data.impl

import dagger.assisted.AssistedFactory


@AssistedFactory
interface ReviewsRemoteMediatorFactory {

    fun create(movieId: Int): ReviewsRemoteMediator
}