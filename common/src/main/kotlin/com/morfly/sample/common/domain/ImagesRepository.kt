package com.morfly.sample.common.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow


interface ImagesRepository {

    fun getPagedImages(query: String): Flow<PagingData<Image>>

    fun getUserImages(userId: String): Flow<List<Image>>

    suspend fun getImage(id: String): Image?


    companion object {
        const val PAGE_SIZE = 10
    }
}