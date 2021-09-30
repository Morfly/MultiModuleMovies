package com.morfly.sample.data.impl

import com.morfly.sample.common.domain.User
import com.morfly.sample.common.domain.UsersRepository
import com.morfly.sample.data.impl.mapping.DataMapper
import com.morfly.sample.data.impl.network.PixabayApi
import com.morfly.sample.data.impl.network.PixabayApi.Companion.MIN_PER_PAGE_VALUE
import com.morfly.sample.data.impl.storage.AppDatabase
import javax.inject.Inject


class FakeUsersRepository @Inject constructor(
    private val pixabayApi: PixabayApi,
    private val appDatabase: AppDatabase,
    private val mapper: DataMapper
) : UsersRepository {

    private var currentUser: User? = null

    override suspend fun getCurrentUser(): User? {
        // Return currentUser if already initialized.
        currentUser?.let { return it }

        // If not get random item from DB and treat it as current user.
        currentUser = appDatabase.imagesDao()
            .getRandomImage()
            ?.user
        currentUser?.let { return it }

        prefetchData()
        return getCurrentUser()
    }

    private suspend fun prefetchData() {
        // If database is empty prefetch some random item from API.
        val images = pixabayApi.images(q = "", page = 1, perPage = MIN_PER_PAGE_VALUE).hits

        // Save retrieved data to the database.
        val storedImages = images.map { mapper.networkToStorage(it, query = "") }
        appDatabase.imagesDao().insertAll(storedImages)
    }
}