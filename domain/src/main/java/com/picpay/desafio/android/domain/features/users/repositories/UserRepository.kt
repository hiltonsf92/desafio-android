package com.picpay.desafio.android.domain.features.users.repositories

import com.picpay.desafio.android.domain.common.Either
import com.picpay.desafio.android.domain.features.users.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun findAll(): Flow<Either<List<User>, String>>
}