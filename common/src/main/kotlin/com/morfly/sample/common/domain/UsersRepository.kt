package com.morfly.sample.common.domain


interface UsersRepository {

    suspend fun getCurrentUser(): User?
}