package com.morfly.sample.images.impl

import javax.inject.Inject


@JvmInline
value class EmptyFeedData(val searchSuggestion: String)


fun interface GetEmptyFeed {

    operator fun invoke(): EmptyFeedData
}


class GetEmptyFeedUseCase @Inject constructor() : GetEmptyFeed {

    override fun invoke(): EmptyFeedData =
        EmptyFeedData(searchSuggestion = SEARCH_SUGGESTIONS.random())


    private companion object {
        val SEARCH_SUGGESTIONS = listOf(
            "kitten",
            "puppies",
        )
    }
}