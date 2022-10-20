package io.morfly.streaming.data.impl

import dagger.assisted.AssistedFactory


@AssistedFactory
interface MoviesRemoteMediatorFactory {

    fun create(query: String): MoviesRemoteMediator
}