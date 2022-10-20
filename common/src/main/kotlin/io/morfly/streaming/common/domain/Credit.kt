package io.morfly.streaming.common.domain

data class Credit(
    val id: Int,
    val name: String,
    val character: String,
    val department: String,
    val profilePath: String?,
    val order: Int
)