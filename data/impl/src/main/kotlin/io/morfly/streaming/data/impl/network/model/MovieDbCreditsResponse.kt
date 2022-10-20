package io.morfly.streaming.data.impl.network.model

import com.squareup.moshi.Json


data class MovieDbCreditsResponse(
    val id: String,
    val cast: List<MovieDbCredit>
)

data class MovieDbCredit(
    val id: Int,
    val name: String,
    val character: String,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    @Json(name = "profile_path")
    val profilePath: String?,
    val order: Int
)
