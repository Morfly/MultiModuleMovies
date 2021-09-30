package com.morfly.sample.common.domain


interface Image {
    val id: String
    val query: String
    val url: String
    val previewUrl: String
    val tags: String
    val views: Int
    val downloads: Int
    val favorites: Int
    val likes: Int
    val comments: Int
    val timestamp: Long
    val user: User
}