package io.morfly.streaming.common.domain


data class Author(
    val name: String,
    val username: String,
    val avatarPath: String?,
    val rating: Int?
)