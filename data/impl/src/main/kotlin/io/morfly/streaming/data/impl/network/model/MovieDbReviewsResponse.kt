package io.morfly.streaming.data.impl.network.model

import com.squareup.moshi.Json
import java.util.*


data class MovieDbReviewsResponse(
    val id: String,
    val page: Int,
    val results: List<MovieDbReview>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)

data class MovieDbReview(
    val id: String,
    val author: String,
    @Json(name = "author_details")
    val authorDetails: MovieDbAuthor,
    val content: String,
    @Json(name = "created_at")
    val createdAt: Date,
    @Json(name = "updated_at")
    val updatedAt: Date
)

data class MovieDbAuthor(
    val name: String,
    val username: String,
    @Json(name = "avatar_path")
    val avatarPath: String?,
    val rating: Int?
)
