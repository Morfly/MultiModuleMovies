package io.morfly.streaming.common.domain

import java.util.*


data class Review(
    val id: String,
    val author: Author,
    val content: String,
    val createdAt: Date,
    val updatedAt: Date
)
